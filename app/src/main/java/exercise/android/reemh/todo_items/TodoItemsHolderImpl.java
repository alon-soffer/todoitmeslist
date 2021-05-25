package exercise.android.reemh.todo_items;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class TodoItemsHolderImpl implements TodoItemsHolder {
    List<TodoItem> items = new ArrayList<>();
    private transient final SharedPreferences sp;

    public TodoItemsHolderImpl(SharedPreferences sp)
    {
        this.sp = sp;
        initiateFromSp();
    }

    private void initiateFromSp()
    {
        Gson gson = new Gson();
        Set<String> spKeys = sp.getAll().keySet();
        for (String spKey : spKeys) {
            String storedJson = sp.getString(spKey, null);
            if (storedJson != null){
                TodoItem item = gson.fromJson(storedJson, TodoItem.class);
                items.add(item);
            }
        }
        Collections.sort(items);
    }


    @Override
    public List<TodoItem> getCurrentItems() { return items; }

    @Override
    public void setCurrentItems(List<TodoItem> itemsToSet) {
        items = itemsToSet;
    }

    @Override
    public void addNewInProgressItem(String description) {
        TodoItem newItem = new TodoItem();
        newItem.setText(description);
        items.add(0, newItem);

        updateSp(newItem);
    }


    @Override
    public void markItemDone(TodoItem item) {
        for (TodoItem todoItem : items)
        {
            if (item == todoItem)
            {
                todoItem.setDone(true);
                todoItem.setTimeLastModified(new Date());
                updateSp(item);
            }
        }
        Collections.sort(items);
    }

    private void updateSp(TodoItem item) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sp.edit();
        String itemAsJson = gson.toJson(item);
        String key = Long.toString(item.getTimeOfCreation());
        editor.putString(key, itemAsJson);
        editor.apply();
    }

    @Override
    public void markItemInProgress(TodoItem item) {
        for (TodoItem todoItem : items)
        {
            if (item == todoItem)
            {
                todoItem.setDone(false);
                todoItem.setTimeLastModified(new Date());
                updateSp(item);
            }
        }
        Collections.sort(items);
    }

    @Override
    public void deleteItem(TodoItem item) {
        items.remove(item);

        SharedPreferences.Editor editor = sp.edit();
        String key = Long.toString(item.getTimeOfCreation());
        editor.remove(key);
        editor.apply();
    }

    @Override
    public void updateItem(TodoItem item) {
        deleteItem(item);

        for (TodoItem todoItem : items)
        {
            if (item.getTimeOfCreation() == todoItem.getTimeOfCreation())
            {
                todoItem.setDone(item.isDone());
                todoItem.setText(item.getTaskText());
                todoItem.setTimeLastModified(item.getTimeLastModified());
                updateSp(item);
            }
        }
        Collections.sort(items);
    }

    @Override
    public int getItemsSize() {
        return items.size();
    }

    @Override
    public TodoItem getItemPos(int position) {
        return items.get(position);
    }
}
