package hz.hz.atomchat.tasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import hz.hz.atomchat.R
import org.threeten.bp.LocalDateTime

enum class Priority(@DrawableRes val icon: Int) {
    Low(R.drawable.priority_low),
    Medium(R.drawable.priority_medium),
    High(R.drawable.priority_high),
    Alarm(R.drawable.priority_high),
}

enum class Action(@StringRes val callForActionText: Int, @StringRes val buttonText: Int) {
    ToDo(R.string.task_action_todo, R.string.task_button_todo),
    ToBeDone(R.string.task_action_tobedone, R.string.task_button_tobedone),
    ToBeChecked(R.string.task_action_tobechecked, R.string.task_button_tobechecked),
    Done(R.string.task_action_done, R.string.task_button_done),
}

class Task(
    val id: Int,
    val title: String,
    val priority: Priority,
    val action: Action,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val description: String
)
