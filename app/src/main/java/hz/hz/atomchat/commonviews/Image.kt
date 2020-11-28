package hz.hz.atomchat.commonviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import hz.hz.atomchat.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class Image @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr) {
    val image: ImageView

    init {
        View.inflate(context, R.layout.image_item, this)
        image = findViewById(R.id.image_item_image)
    }

    @ModelProp
    fun setImage(@DrawableRes res: Int) {
        image.setImageResource(res)
    }

}
