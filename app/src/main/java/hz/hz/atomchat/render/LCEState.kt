package hz.hz.atomchat.render

enum class Loading {
    Main,
    Secondary,
    None
}

class LCEState<out Content>(
    val loading: Loading = Loading.None,
    val error: Throwable? = null,
    val content: Content? = null,
)
