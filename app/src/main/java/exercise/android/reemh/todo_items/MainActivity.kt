package exercise.android.reemh.todo_items

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    @JvmField
    var holder: TodoItemsHolder? = null
    private var todoAdapter: TodoItemAdapter? = null
    private var broadcastReceiverForItemEdited : BroadcastReceiver? = null
    private val BROADCAST_NAME = "update_item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val app = applicationContext as TodoApplication
        if (holder == null) {
            holder = app.todoItemsDb
        }

        /* find UI elements */
        val createNewTodoButton = findViewById<FloatingActionButton>(R.id.buttonCreateTodoItem)
        val editText = findViewById<EditText>(R.id.editTextInsertTask)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerTodoItemsList)

        /* init adapter */
        todoAdapter = app.todoItemAdapter;

        /* init recyclerView */
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = todoAdapter

        /* set on click listeners for todoRow elements (checkbox and delete button) */
        todoAdapter!!.onItemClickCallback = {todoItem ->
            if (todoItem.isDone) {
                holder!!.markItemInProgress(todoItem)
                todoAdapter!!.notifyDataSetChanged()
            } else {
                holder!!.markItemDone(todoItem)
                todoAdapter!!.notifyDataSetChanged()
            }
        }

        todoAdapter!!.onDeleteClickCallback = {todoItem ->
            holder!!.deleteItem(todoItem)
            todoAdapter!!.notifyDataSetChanged()
        }

        todoAdapter!!.onTextClickCallback = {todoItem ->
            val editIntent = Intent(this, EditScreenActivity::class.java)
            editIntent.putExtra("item", todoItem)
            startActivity(editIntent)
        }

        /* set on click listener for create task FAB*/
        createNewTodoButton.setOnClickListener { v: View? ->
            val taskDesc = editText.text.toString()
            if (taskDesc.isEmpty()) {
                return@setOnClickListener
            }
            holder!!.addNewInProgressItem(taskDesc)
            todoAdapter!!.notifyDataSetChanged()
            editText.setText("")
        }

        broadcastReceiverForItemEdited = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, incomingIntent: Intent?) {
                if (incomingIntent == null || incomingIntent.action != BROADCAST_NAME) return

                val todoItem = incomingIntent.getSerializableExtra("updated_item") as TodoItem
                holder!!.updateItem(todoItem)
                todoAdapter!!.notifyDataSetChanged()
            }
        }
        registerReceiver(broadcastReceiverForItemEdited, IntentFilter(BROADCAST_NAME))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiverForItemEdited)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val editText = findViewById<EditText>(R.id.editTextInsertTask)
        outState.putString("current_text", editText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val app = applicationContext as TodoApplication
        holder = app.todoItemsDb
        todoAdapter = app.todoItemAdapter

        val currentText = savedInstanceState.getString("current_text")

        val createNewTodoButton = findViewById<FloatingActionButton>(R.id.buttonCreateTodoItem)
        val editText = findViewById<EditText>(R.id.editTextInsertTask)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerTodoItemsList)

        editText.setText(currentText)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = todoAdapter

        /* set on click listeners for todoRow elements (checkbox and delete button) */
        todoAdapter!!.onItemClickCallback = {todoItem ->
            if (todoItem.isDone) {
                holder!!.markItemInProgress(todoItem)
                todoAdapter!!.notifyDataSetChanged()
            } else {
                holder!!.markItemDone(todoItem)
                todoAdapter!!.notifyDataSetChanged()
            }
        }

        todoAdapter!!.onDeleteClickCallback = {todoItem ->
            holder!!.deleteItem(todoItem)
            todoAdapter!!.notifyDataSetChanged()
        }

        todoAdapter!!.onTextClickCallback = {todoItem ->
            val editIntent = Intent(this, EditScreenActivity::class.java)
            editIntent.putExtra("item", todoItem)
            startActivity(editIntent)
        }

        /* set on click listener for create task FAB*/
        createNewTodoButton.setOnClickListener { v: View? ->
            val taskDesc = editText.text.toString()
            if (taskDesc.isEmpty()) {
                return@setOnClickListener
            }
            holder!!.addNewInProgressItem(taskDesc)
            todoAdapter!!.notifyDataSetChanged()
            editText.setText("")
        }
    }
}

/*
SPECS:
- the screen starts out empty (no items shown, edit-text input should be empty)
- every time the user taps the "add TODO item" button:
    * if the edit-text is empty (no input), nothing happens
    * if there is input:
        - a new TodoItem (checkbox not checked) will be created and added to the items list
        - the new TodoItem will be shown as the first item in the Recycler view
        - the edit-text input will be erased
- the "TodoItems" list is shown in the screen
  * every operation that creates/edits/deletes a TodoItem should immediately be shown in the UI
  * the order of the TodoItems in the UI is:
    - all IN-PROGRESS items are shown first. items are sorted by creation time,
      where the last-created item is the first item in the list
    - all DONE items are shown afterwards, no particular sort is needed (but try to think about what's the best UX for the user)
  * every item shows a checkbox and a description. you can decide to show other data as well (creation time, etc)
  * DONE items should show the checkbox as checked, and the description with a strike-through text
  * IN-PROGRESS items should show the checkbox as not checked, and the description text normal
  * upon click on the checkbox, flip the TodoItem's state (if was DONE will be IN-PROGRESS, and vice versa)
  * add a functionality to remove a TodoItem. either by a button, long-click or any other UX as you want
- when a screen rotation happens (user flips the screen):
  * the UI should still show the same list of TodoItems
  * the edit-text should store the same user-input (don't erase input upon screen change)
Remarks:
- you should use the `holder` field of the activity
- you will need to create a class extending from RecyclerView.Adapter and use it in this activity
- notice that you have the "row_todo_item.xml" file and you can use it in the adapter
- you should add tests to make sure your activity works as expected. take a look at file `MainActivityTest.java`
(optional, for advanced students:
- save the TodoItems list to file, so the list will still be in the same state even when app is killed and re-launched
)
*/