package hz.hz.atomchat.details

import androidx.lifecycle.ViewModel
import hz.hz.atomchat.app_logic.Tasks
import hz.hz.atomchat.tasks.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailsViewModel : ViewModel() {
    fun task(id: Int): Flow<Task> = flow {
        emit(Tasks.task(id))
    }
}
