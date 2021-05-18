package exercise.android.reemh.todo_items

import java.io.Serializable

class TodoItem : Serializable, Comparable<TodoItem> {
    private val timeOfCreation: Long = System.currentTimeMillis()
    var isDone = false
    var taskText: String? = null

    fun setText(text: String?) {
        taskText = text
    }

    fun flipInProgress() {
        isDone = !isDone
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