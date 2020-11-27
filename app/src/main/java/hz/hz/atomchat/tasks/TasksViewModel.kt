package hz.hz.atomchat.tasks

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TasksViewModel : ViewModel() {

    fun tasks(): Flow<String> = flow {
        for (i in 1..10) {
            emit(i.toString())
            delay(1000)
        }
    }
}
