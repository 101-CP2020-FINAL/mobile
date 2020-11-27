package hz.hz.atomchat.render

import java.util.ArrayDeque

class StateDiff<S : Any> {
    private val diffs = ArrayDeque<(ps: S?, ns: S) -> Unit>()
    private var previousState: S? = null

    companion object {
        fun <S : Any> createInitial(initialState: S, init: StateDiff<S>.() -> Unit): StateDiff<S> {
            val stateDiff = StateDiff<S>().also(init)
            stateDiff.update(initialState)
            return stateDiff
        }
    }

    fun onChange(block: (ps: S?, ns: S) -> Unit) {
        diffs.add { ps, ns -> block(ps, ns) }
    }

    fun onChange(
        shouldUpdate: (previousState: S?, newState: S) -> Boolean,
        block: (S) -> Unit
    ) {
        diffs.add { ps, ns ->
            if (shouldUpdate(ps, ns)) block(ns)
        }
    }

    fun update(newState: S) {
        if (previousState !== newState) diffs.forEach { diff -> diff(previousState, newState) }
        previousState = newState
    }
}

fun <S : Any, T> StateDiff<S>.diff(vararg mappers: (S) -> T, block: (S) -> Unit) {
    onChange({ p, n -> p == null || mappers.any { mapper -> mapper(p) != mapper(n) } }, block)
}

fun <S : Any> StateDiff<S>.onInit(block: (S) -> Unit) {
    var isFirst = true
    onChange({ _, _ -> isFirst }, { isFirst = false; block(it) })
}

fun <S : Any> StateDiff<S>.onNotInit(block: (S) -> Unit) {
    var isFirst = false
    onChange({ _, _ -> isFirst.also { isFirst = true } }, { block(it) })
}

fun <S : Any> StateDiff<S>.diffOnce(
    filter: (S?, S) -> Boolean = { _, _ -> true },
    block: (S) -> Unit
) {
    var lock = false
    onChange(
        { p, n -> !lock && filter(p, n) },
        { lock = true; block(it) }
    )
}
