package exercise.android.reemh.todo_items;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class TodoApplication extends Application {

    private TodoItemsHolder todoItemsDb;
    private TodoItemAdapter todoItemAdapter;
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sp = this.getSharedPreferences("todo_item_db", MODE_PRIVATE);
        this.todoItemsDb = new TodoItemsHolderImpl(sp);
        this.todoItemAdapter = new TodoItemAdapter();
        this.todoItemAdapter.setHolder(todoItemsDb);
    }


    public TodoItemsHolder getTodoItemsDb() {
        return todoItemsDb;
    }
    public TodoItemAdapter getTodoItemAdapter() { return todoItemAdapter;}
}
