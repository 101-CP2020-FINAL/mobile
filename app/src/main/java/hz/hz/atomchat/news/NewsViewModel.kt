package hz.hz.atomchat.news

import androidx.lifecycle.ViewModel
import hz.hz.atomchat.render.LCEState
import hz.hz.atomchat.tasks.Action
import hz.hz.atomchat.tasks.Priority
import hz.hz.atomchat.tasks.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDateTime

class NewsViewModel : ViewModel() {

    fun tasks(): Flow<LCEState<State>> = flow {
        for (i in 1..10) {

            val taskList = List(10) {
                Task(
                    id = it,
                    title = "Title $it",
                    Priority.values()[Math.floorMod(it, Priority.values().size)],
                    Action.values()[Math.floorMod(it, Action.values().size)],
                    startTime = LocalDateTime.now().plusMinutes(i + it.toLong()),
                    endTime = LocalDateTime.now().plusMinutes(i + it.toLong()),
                    description = ""
                )
            }

            emit(
                LCEState(
                    content = State(
                        news = taskList
                    )
                )
            )
            delay(1000)
        }
    }
}
