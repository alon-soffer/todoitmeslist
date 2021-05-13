package exercise.android.reemh.todo_items;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    TextView text;
    CheckBox checkBox;

    public TodoViewHolder(View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.todoItemRowText);
        checkBox = itemView.findViewById(R.id.todoItemRowCheckBox);
    }
}
