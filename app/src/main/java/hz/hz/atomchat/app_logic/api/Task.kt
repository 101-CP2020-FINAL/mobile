package hz.hz.atomchat.app_logic.api

class Task(
    val id: Int,
    val type: Dictionary,
    val priority: Dictionary,
    val status: Status,
    val author: Dictionary,
    val title: String,
    val description: String,
    val date_start: String?,
    val deadline: String?,
)

class Dictionary(
    val id: Int,
    val title: String,
)

class Status(
    val id: Int,
    val alias: String,
    val title: String,
)
