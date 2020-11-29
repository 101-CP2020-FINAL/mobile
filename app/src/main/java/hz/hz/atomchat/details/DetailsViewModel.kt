package hz.hz.atomchat.details

import androidx.lifecycle.ViewModel
import hz.hz.atomchat.app_logic.Tasks
import hz.hz.atomchat.render.LCEState
import hz.hz.atomchat.tasks.Action
import hz.hz.atomchat.tasks.Task
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

data class State(
    val task: Task,
    val action: LCEState<Unit>
)

class DetailsViewModel : ViewModel() {
    private val channel = Channel<LCEState<Unit>>()
    private lateinit var state: State

    fun task(id: Int): Flow<State> = Tasks.taskAction()
        .map {
            state = State(Tasks.task(id), it)

            state
        }

    fun changeStatus() {
        if (state.task.action == Action.ToDo) {
            Tasks.changeStatus(state.task.id, 2)
        } else {
            Tasks.changeStatus(state.task.id, 4)
        }
    }
}
