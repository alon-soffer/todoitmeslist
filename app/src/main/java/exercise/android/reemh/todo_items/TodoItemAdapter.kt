package exercise.android.reemh.todo_items

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoItemAdapter : RecyclerView.Adapter<TodoViewHolder>() {
    private var itemsHolder: TodoItemsHolder? = null
    var onItemClickCallback: ((TodoItem)->Unit)? = null
    var onDeleteClickCallback: ((TodoItem)->Unit)? = null

    fun setHolder(holder: TodoItemsHolder?) {
        itemsHolder = holder
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.row_todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        val item = itemsHolder!!.getItemPos(position)
        holder.text.text = item.taskText
        holder.checkBox.isChecked = item.isDone

        if (!item.isDone) {
            holder.text.apply {
                paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        } else {
            holder.text.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

        holder.checkBox.setOnClickListener {
            val callback = onItemClickCallback ?:return@setOnClickListener
            callback(item)
        }

        holder.deleteButton.setOnClickListener {
            val callback = onDeleteClickCallback ?:return@setOnClickListener
            callback(item)
        }
    }


    override fun getItemCount(): Int {
        // how many items we have in total
        return itemsHolder!!.itemsSize
    }
}

