package hz.hz.atomchat.tasks

import androidx.lifecycle.ViewModel
import hz.hz.atomchat.render.LCEState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDateTime

class TasksViewModel : ViewModel() {

    fun tasks(): Flow<LCEState<State>> = flow {
        val inProgress = List(2) {
            Task(
                id = it + 100,
                title = "Title $it",
                Priority.values()[Math.floorMod(it, Priority.values().size)],
                Action.values()[Math.floorMod(it, Action.values().size)],
                endTime = LocalDateTime.now().plusMinutes(1 + 7 * it.toLong())
            )
        }

        for (i in 1..10) {

            val taskList = List(10) {
                Task(
                    id = it,
                    title = "Title $it",
                    Priority.values()[Math.floorMod(it, Priority.values().size)],
                    Action.values()[Math.floorMod(it, Action.values().size)],
                    endTime = LocalDateTime.now().plusMinutes(i + it.toLong())
                )
            }

            emit(
                LCEState(
                    content = State(
                        tasksInProgress = inProgress,
                        tasksToDo = taskList
                    )
                )
            )
            delay(1000)
        }
    }
}
