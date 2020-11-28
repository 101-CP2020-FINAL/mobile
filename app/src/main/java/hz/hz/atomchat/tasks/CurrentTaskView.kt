package hz.hz.atomchat.tasks

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import hz.hz.atomchat.R
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CurrentTaskView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr) {
    val format = DateTimeFormatter.ISO_TIME
    var timer: CountDownTimer? = null

    val title: TextView
    val date: TextView
    val action: TextView
    val priority: ImageView

    init {
        View.inflate(context, R.layout.current_task_item, this)
        title = findViewById(R.id.task_item_title)
        date = findViewById(R.id.task_item_date)
        action = findViewById(R.id.task_item_action)
        priority = findViewById(R.id.task_item_priority)
    }

    @TextProp
    fun setTitle(text: CharSequence) {
        title.text = text
    }

    @ModelProp
    fun setEndTime(new: LocalDateTime) {
        timer?.cancel()

        val distance = Duration.between(LocalDateTime.now(), new)

        timer = object : CountDownTimer(distance.toMillis(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val time = LocalTime.ofSecondOfDay(millisUntilFinished / 1000)
                date.text = "Осталось ${time.format(format)}"
            }

            override fun onFinish() {
                date.text = "Просрочено"
            }

        }.start()
    }

    @ModelProp
    fun setAction(new: Action) {
        action.setText(new.callForActionText)
    }

    @ModelProp
    fun setPriority(new: Priority) {
        priority.setImageResource(new.icon)
    }

}
