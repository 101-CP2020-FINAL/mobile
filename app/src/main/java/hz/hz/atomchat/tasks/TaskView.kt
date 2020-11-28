package hz.hz.atomchat.tasks

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import hz.hz.atomchat.R
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TaskView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr) {
    val format = DateTimeFormatter.ISO_TIME

    val title: TextView
    val date: TextView
    val action: TextView
    val priority: ImageView

    init {
        View.inflate(context, R.layout.task_item, this)
        title = findViewById(R.id.task_item_title)
        date = findViewById(R.id.task_item_date)
        action = findViewById(R.id.task_item_action)
        priority = findViewById(R.id.task_item_priority)
        setBackgroundResource(R.color.task_bg)
    }

    @TextProp
    fun setTitle(text: CharSequence) {
        title.text = text
    }

    @ModelProp
    fun setEndTime(new: LocalDateTime) {
        date.text = new.format(format)
    }

    @ModelProp
    fun setAction(new: Action) {
        action.setText(new.actionText)
    }

    @ModelProp
    fun setPriority(new: Priority) {
        priority.setImageResource(new.icon)
    }

}
