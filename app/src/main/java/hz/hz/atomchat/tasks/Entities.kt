package hz.hz.atomchat.tasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import hz.hz.atomchat.R
import org.threeten.bp.LocalDateTime

enum class Priority(@DrawableRes val icon: Int) {
    High(R.drawable.priority_high),
    Medium(R.drawable.priority_medium),
    Low(R.drawable.priority_low)
}

enum class Action(@StringRes val actionText: Int) {
    ToDo(R.string.task_action_todo),
    ToBeDone(R.string.task_action_tobedone)
}

class Task(
    val id: Int,
    val title: String,
    val priority: Priority,
    val action: Action,
    val endTime: LocalDateTime,
    val description: String
)
