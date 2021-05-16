package exercise.android.reemh.todo_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoItemAdapter extends RecyclerView.Adapter<TodoViewHolder> {

    private TodoItemsHolder itemsHolder;
//    public TodoItemListener todoItemListener;
//    private List<TodoItem> todoItemList = new ArrayList<>();
    //todo: do we need set item list method? or update?

//    public TodoItemAdapter(TodoItemsHolder logic)
//    {
//        super();
//        itemsHolder = logic;
//    }

    public void getHolder(TodoItemsHolder holder)
    {
        this.itemsHolder = holder;
    }

//    public void setTodoItemListener(TodoItemListener listener){todoItemListener = listener;}

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_todo_item, parent, false);

        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
//        TodoItem item = todoItemList.get(position);
        TodoItem item = itemsHolder.getItemPos(position);

        holder.text.setText(item.getTaskText());
        holder.checkBox.setChecked(item.isDone());
//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("button clicked");
//            }
//        });
//        holder.itemView.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                if(todoItemListener != null)
//                {
//                    todoItemListener.onTodoItemClicked(item);
//                }
//            }
//        });
    }

//    @Override
//    public void onBindViewHolder(TodoItemsHolderImpl itemsHolder, int position) {
//        TodoItem item = todoItemList.get(position);
//        itemsHolder.text.setText(item.getTaskText());
//        itemsHolder.checkBox.setChecked(item.isDone());
//    }

    @Override
    public int getItemCount() {
        // how many items we have in total
//        return todoItemList.size();
        return itemsHolder.getItemsSize();
    }
}
