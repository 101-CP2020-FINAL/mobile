package hz.hz.atomchat.tasks

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import hz.hz.atomchat.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TaskView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr) {
    val title: TextView

    init {
        View.inflate(context, R.layout.task_item, this)
        title = findViewById(R.id.task_item_title)
    }

    @TextProp
    fun setTitle(text: CharSequence) {
        title.text = text
    }

}
