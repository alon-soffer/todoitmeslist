package exercise.android.reemh.todo_items;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EditScreenActivity extends AppCompatActivity {

    private static final String LAST_MODIFIED_PREFIX = "Last Modified: ";
    private static final String TIME_OF_CREATION_PREFIX = "Time Of Creation: ";


    private String getLastModified(Date lastModified)
    {
        Calendar c = Calendar.getInstance();
        long diff = c.getTimeInMillis() - lastModified.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long hours = TimeUnit.MILLISECONDS.toHours(diff);

        if (minutes < 60)
        {
            return minutes + "minutes ago";
        }
        if (hours < 24)
        {
            return "Today at " + hours;
        }
        // TODO: text should be "<date> at <hour>
        return lastModified + "at " + (hours % 24);
    }


    private void setLastModifiedView(TextView lastModifiedView, TodoItem item)
    {
        String lm = LAST_MODIFIED_PREFIX + getLastModified(item.getTimeLastModified());
        lastModifiedView.setText(lm);
    }

    private void uponUpdate(TodoItem item, TextView lastModifiedView)
    {
        item.setTimeLastModified(new Date());
        setLastModifiedView(lastModifiedView, item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_screen);

        TextView timeOfCreationView = findViewById(R.id.timeOfCreationView);
        TextView lastModifiedView = findViewById(R.id.lastModifiedView);
        Switch progressStatus = findViewById(R.id.progressStatus);
        EditText taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);

        Intent itemIntent = getIntent();
        TodoItem item = (TodoItem) itemIntent.getSerializableExtra("item");

        String toc = TIME_OF_CREATION_PREFIX + item.getDateOfCreation();
        timeOfCreationView.setText(toc);

        setLastModifiedView(lastModifiedView, item);

        progressStatus.setChecked(item.isDone());

        String td = taskDescriptionEditText.getText().toString() + item.getTaskText();
        taskDescriptionEditText.setText(td);

        progressStatus.setOnClickListener(v -> {
            item.setDone(progressStatus.isChecked());
            uponUpdate(item, lastModifiedView);
            sendUpdateBroadcast(item);
        });

        taskDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                String newTaskDescription = taskDescriptionEditText.getText().toString();
                item.setText(newTaskDescription);
                uponUpdate(item, lastModifiedView);

                sendUpdateBroadcast(item);
            }
        });
    }

    private void sendUpdateBroadcast(TodoItem item) {
        Intent updateIntent = new Intent("update_item");
        updateIntent.setAction("update_item");
        updateIntent.putExtra("updated_item", item);
        sendBroadcast(updateIntent);
    }
}
