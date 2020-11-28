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
        val taskList = listOf(
            Task(
                id = 1,
                title = "Проверить ремни ГРМ",
                Priority.Low,
                Action.ToBeChecked,
                startTime = LocalDateTime.now(),
                endTime = LocalDateTime.now(),
                description = ""
            ),
            Task(
                id = 2,
                title = "Ознакомиться с изменениями в трудовом распорядке",
                Priority.Medium,
                Action.Done,
                startTime = LocalDateTime.now(),
                endTime = LocalDateTime.now(),
                description = ""
            )
        )

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
