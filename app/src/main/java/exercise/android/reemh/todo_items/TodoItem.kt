package exercise.android.reemh.todo_items

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

class TodoItem : Serializable, Comparable<TodoItem> {
    val timeOfCreation: Long = System.currentTimeMillis()
    var isDone = false
    var taskText: String? = null
    val dateOfCreation: Date = Date()
    var timeLastModified: Date = dateOfCreation

    fun setText(text: String?) {
        taskText = text
    }


    override fun compareTo(other: TodoItem): Int {
        if (isDone == other.isDone)
        {
            return if (timeOfCreation < other.timeOfCreation) {
                1
            } else -1
        }
        else if (isDone && !other.isDone)
        {
            return 1
        }
        else if (!isDone && other.isDone)
        {
            return -1
        }
        return -1
    }

}