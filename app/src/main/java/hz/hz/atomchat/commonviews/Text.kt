package hz.hz.atomchat.commonviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import hz.hz.atomchat.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class Text @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr) {
    val text: TextView

    init {
        View.inflate(context, R.layout.text_item, this)
        text = findViewById(R.id.text_item_text)
    }

    @TextProp
    fun setText(text: CharSequence) {
        this.text.text = text
    }

}
