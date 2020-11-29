package hz.hz.atomchat.app_logic

import com.google.gson.Gson
import hz.hz.atomchat.app_logic.api.createApi
import hz.hz.atomchat.render.LCEState
import hz.hz.atomchat.render.Loading
import hz.hz.atomchat.tasks.Action
import hz.hz.atomchat.tasks.Priority
import hz.hz.atomchat.tasks.Task
import io.github.centrifugal.centrifuge.*
import io.github.centrifugal.centrifuge.EventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import okhttp3.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.await
import timber.log.Timber
import hz.hz.atomchat.app_logic.api.Task as TaskApi

object Tasks {
    private val api by lazy { createApi() }

    private var appState: LCEState<AppState> = LCEState(loading = Loading.Main)

    private var actionChannel = Channel<Pair<Int, Int>>()

    fun tasks(): Flow<LCEState<AppState>> = flow {

        appState = appState.copy(
            loading = if (appState.content == null) Loading.Main else Loading.Secondary,
            error = null
        )

        emit(appState)

        appState = try {
            val tasks = api.getTasks().await()
            appState.copy(
                loading = Loading.None,
                error = null,
                content = AppState(tasks = convert(tasks).sortedByDescending { it.priority.ordinal }),
            )
        } catch (e: Throwable) {
            Timber.e(e)
            appState.copy(
                loading = Loading.None,
                error = e,
            )
        }

        emit(appState)

        socketFlow().collect { task ->

            if (appState.content == null) {
                return@collect
            }

            val tasks = appState.content!!.tasks
                .filter { it.id != task.id }
                .plus(task)
                .sortedByDescending { it.priority.ordinal }

            appState = appState.copy(content = AppState(tasks))

            emit(appState)
        }
    }

    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    private fun convert(tasks: List<TaskApi>): List<Task> {
        return tasks.map { it.toTask() }
    }

    fun task(id: Int): Task {
        return appState.content!!.tasks.first { it.id == id }
    }

    fun taskAction(): Flow<LCEState<Unit>> {
        actionChannel = Channel<Pair<Int, Int>>()

        return actionChannel
            .consumeAsFlow()
            .flatMapLatest {
                flow<LCEState<Unit>> {
                    emit(LCEState(loading = Loading.Main))

                    try {
                        api.changeTasks(it.first, it.second).await()
                        emit(LCEState(content = Unit))
                    } catch (e: Throwable) {
                        emit(LCEState(error = e))
                    }
                }
            }
            .onStart {
                emit(LCEState())
            }
    }

    fun changeStatus(taskId: Int, statusId: Int) {
        actionChannel.offer(Pair(taskId, statusId))
    }
}


private fun TaskApi.toTask(): Task = Task(
    id,
    title = title,
    description = description.orEmpty(),
    priority = Priority.values()[priority.id - 1],
    action = Action.values()[status.id - 1],
    startTime = if (date_start != null) {
        LocalDateTime.parse(
            date_start,
            Tasks.format
        )
    } else LocalDateTime.now(),
    endTime = if (deadline != null) {
        LocalDateTime.parse(
            deadline,
            Tasks.format
        )
    } else LocalDateTime.now(),
)

fun socketFlow(): Flow<Task> = callbackFlow {
    val client = Client(
        "ws://92.63.103.157:9002/connection/websocket?format=protobuf",
        Options(),
        object : EventListener() {
            override fun onError(client: Client?, event: ErrorEvent?) {
                Timber.tag("socket").d("error")
            }
        }
    )
    client.connect()

    val sub = client.newSubscription("public", object : SubscriptionEventListener() {
        override fun onPublish(sub: Subscription?, event: PublishEvent?) {
            Timber.tag("socket").d(String())
            val task = Gson().fromJson(String(event!!.data), TaskApi::class.java)
            offer(task.toTask())
        }
    })
    sub.subscribe()

    awaitClose { cancel() }
}
