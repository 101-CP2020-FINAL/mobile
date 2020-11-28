package hz.hz.atomchat.render

enum class Loading {
    Main,
    Secondary,
    None
}

data class LCEState<out Content>(
    val loading: Loading = Loading.None,
    val error: Throwable? = null,
    val content: Content? = null,
) {
    fun <T> mapContent(block: (Content) -> T): LCEState<T> {
        val content = if (content != null) {
            block(content)
        } else {
            null
        }

        return LCEState<T>(
            loading = loading,
            error = error,
            content = content,
        )
    }
}
