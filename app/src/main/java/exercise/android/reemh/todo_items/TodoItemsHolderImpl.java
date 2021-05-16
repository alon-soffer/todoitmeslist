package exercise.android.reemh.todo_items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoItemsHolderImpl implements TodoItemsHolder {
    List<TodoItem> items = new ArrayList<>();
    TodoItemAdapter adapter;

    @Override
    public List<TodoItem> getCurrentItems() { return items; }

    @Override
    public void addNewInProgressItem(String description) {
        TodoItem newItem = new TodoItem();
        newItem.setText(description);
        items.add(0, newItem);
        notifyAdapterAboutChange();
    }

    @Override
    public void markItemDone(TodoItem item) {
        for (TodoItem todoItem : items)
        {
            if (item == todoItem)
            {
                todoItem.setDone(true);
            }
        }
        Collections.sort(items);
    }

    @Override
    public void markItemInProgress(TodoItem item) {
        for (TodoItem todoItem : items)
        {
            if (item == todoItem)
            {
                todoItem.setDone(false);
            }
        }
        Collections.sort(items);
    }

    @Override
    public void deleteItem(TodoItem item) {
        items.remove(item);
    }

    @Override
    public int getItemsSize() {
        return items.size();
    }

    @Override
    public TodoItem getItemPos(int position) {
        return items.get(position);
    }

    @Override
    public void setAdapter(TodoItemAdapter adapter) {
        this.adapter = adapter;
    }

//    @Override
    public void notifyAdapterAboutChange() {
        adapter.notifyDataSetChanged();
    }


}
