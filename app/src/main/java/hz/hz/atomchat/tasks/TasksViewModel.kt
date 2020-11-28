package hz.hz.atomchat.tasks

import androidx.lifecycle.ViewModel
import hz.hz.atomchat.app_logic.Tasks
import hz.hz.atomchat.render.LCEState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class TasksViewModel : ViewModel() {

    fun tasks(): Flow<LCEState<State>> = Tasks.tasks().mapLatest {
        it.mapContent { state ->
            State(
                tasksInProgress = state.tasks.filter { it.action == Action.ToBeDone },
                tasksToDo = state.tasks.filter { it.action == Action.ToDo }
            )
        }
    }
}
