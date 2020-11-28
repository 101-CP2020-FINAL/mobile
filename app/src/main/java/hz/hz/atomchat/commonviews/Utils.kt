package hz.hz.atomchat.commonviews

import android.R
import android.util.TypedValue
import android.view.View


fun View.setSelectableItemBackground() {
    val outValue = TypedValue()
    context.theme.resolveAttribute(R.attr.selectableItemBackground, outValue, true)
    this.setBackgroundResource(outValue.resourceId)
}
