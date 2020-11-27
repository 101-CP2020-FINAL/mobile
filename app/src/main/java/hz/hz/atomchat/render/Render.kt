package hz.hz.atomchat.render

import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import timber.log.Timber

@ExperimentalCoroutinesApi
fun <S : Any> Flow<S>.observe(view: View, init: StateDiff<S>.() -> Unit) {
    val diff = StateDiff<S>().also(init)
    view.launch {
        this@observe.distinctUntilChanged().collect {
            if (isActive && view.isAttachedToWindow) diff.update(it)
        }
    }
}

fun View.launch(name: String? = null, block: suspend CoroutineScope.() -> Unit) {
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        var scope: CoroutineScope? = null
        override fun onViewAttachedToWindow(v: View?) {
            Timber.i("onViewAttachedToWindow name=${name ?: this@launch}")
            scope = MainScope().also { scope ->
                scope.launch(Dispatchers.Main.immediate) {
                    block()
                }
            }
        }

        override fun onViewDetachedFromWindow(v: View?) {
            Timber.i("onViewDetachedFromWindow view=${this@launch}")
            scope?.cancel()
            scope = null
        }
    })
}
