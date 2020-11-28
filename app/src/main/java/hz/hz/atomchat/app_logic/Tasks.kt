package hz.hz.atomchat.app_logic

import hz.hz.atomchat.app_logic.api.createApi
import hz.hz.atomchat.render.LCEState
import hz.hz.atomchat.render.Loading
import hz.hz.atomchat.tasks.Action
import hz.hz.atomchat.tasks.Priority
import hz.hz.atomchat.tasks.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.await
import hz.hz.atomchat.app_logic.api.Task as ApiTask

object Tasks {
    private val api by lazy { createApi() }

    private var appState: LCEState<AppState> = LCEState(loading = Loading.Main)

    fun tasks(): Flow<LCEState<AppState>> = flow {

        appState = appState.copy(loading = Loading.Main, error = null)

        emit(appState)

        appState = try {
            val tasks = api.getTasks().await()
            appState.copy(
                loading = Loading.None,
                error = null,
                content = AppState(tasks = convert(tasks)),
            )
        } catch (e: Throwable) {
            appState.copy(
                loading = Loading.None,
                error = e,
            )
        }

        emit(appState)
    }

    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    private fun convert(tasks: List<ApiTask>): List<Task> {
        return tasks.map {
            Task(
                it.id,
                title = it.title,
                description = it.description,
                priority = Priority.High,
                action = Action.ToDo,
                startTime = LocalDateTime.parse(
                    it.date_start,
                    format
                ),
                endTime = LocalDateTime.parse(
                    it.deadline,
                    format
                ),
            )
        }
    }
}
