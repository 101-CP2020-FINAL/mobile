package hz.hz.atomchat.commonviews

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
import hz.hz.atomchat.tasks.Action
import hz.hz.atomchat.tasks.Priority
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class Header2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr) {
    val title: TextView

    init {
        View.inflate(context, R.layout.header_2_item, this)
        title = findViewById(R.id.header_item_title)
    }

    @TextProp
    fun setTitle(text: CharSequence) {
        title.text = text
    }

}
