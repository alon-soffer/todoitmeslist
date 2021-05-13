package exercise.android.reemh.todo_items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: implement!
//public class TodoItemsHolderImpl extends RecyclerView.ViewHolder implements TodoItemsHolder {
public class TodoItemsHolderImpl implements TodoItemsHolder {
//    TextView text;
//    CheckBox checkBox;
    List<TodoItem> items = new ArrayList<>();
    TodoItemAdapter adapter;

//    public TodoItemsHolderImpl(View itemView) {
//        super(itemView);
//        text = itemView.findViewById(R.id.todoItemRowText);
//        checkBox = itemView.findViewById(R.id.todoItemRowCheckBox);
//    }


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
                todoItem.setDone(false);
                Collections.sort(items);
                notifyAdapterAboutChange();
//                todoItem.flipInProgress();
            }
        }
    }

    @Override
    public void markItemInProgress(TodoItem item) {
        for (TodoItem todoItem : items)
        {
            if (item == todoItem)
            {
                todoItem.setDone(true);
                Collections.sort(items);
                notifyAdapterAboutChange();
//                todoItem.flipInProgress();
            }
        }
    }

    @Override
    public void deleteItem(TodoItem item) {
        items.remove(item);
//        for (TodoItem todoItem : items)
//        {
//            if (item == todoItem)
//            {
//                items.remove(item);
//            }
//        }
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
