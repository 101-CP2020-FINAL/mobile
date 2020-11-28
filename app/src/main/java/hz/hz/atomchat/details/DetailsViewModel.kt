package hz.hz.atomchat.details

import androidx.lifecycle.ViewModel
import hz.hz.atomchat.tasks.Action
import hz.hz.atomchat.tasks.Priority
import hz.hz.atomchat.tasks.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDateTime

class DetailsViewModel : ViewModel() {
    fun task(): Flow<Task> = flow {
        emit(
            Task(
                id = 1,
                title = "Title",
                Priority.High,
                Action.ToBeDone,
                startTime = LocalDateTime.now().plusMinutes(10),
                endTime = LocalDateTime.now().plusMinutes(10),
                description = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer eget justo mauris. Nam a nunc felis. Nullam sodales luctus nunc, vel malesuada massa efficitur ut. Vestibulum eget interdum metus. Quisque commodo posuere diam et vestibulum. Duis ac ipsum in ante maximus lacinia eu et ex. Proin ut porta est. Curabitur vitae scelerisque mauris. Maecenas a volutpat arcu. Phasellus nec justo nunc.

                Integer porta hendrerit nulla in tincidunt. Fusce cursus diam eget eros feugiat volutpat. Pellentesque non purus tincidunt, rutrum nisi id, vestibulum dolor. Duis a ligula elit. Sed et tellus lorem. Nam elementum, nibh aliquam euismod bibendum, felis justo aliquam tortor, in vulputate turpis mauris sit amet ipsum. Etiam rhoncus massa sagittis malesuada fringilla. Aliquam placerat ante quis nunc viverra, id interdum nunc dictum.
            """.trimIndent()
            )
        )
    }
}
