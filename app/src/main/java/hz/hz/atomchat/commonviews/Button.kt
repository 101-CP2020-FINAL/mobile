package hz.hz.atomchat.commonviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import hz.hz.atomchat.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class Button @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr) {
    val text: TextView

    init {
        View.inflate(context, R.layout.button_item, this)
        text = findViewById(R.id.button_item_button)
    }

    @TextProp
    fun setText(text: CharSequence) {
        this.text.text = text
    }

    @CallbackProp
    fun setClickListener(listener: OnClickListener?) {
        text.setOnClickListener(listener)
    }

}
