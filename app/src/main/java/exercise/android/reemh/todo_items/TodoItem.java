package exercise.android.reemh.todo_items;

import java.io.Serializable;

public class TodoItem implements Serializable, Comparable<TodoItem> {
    private final long timeOfCreation;
    private boolean done = false;
    private String taskText;

    public TodoItem()
    {
        timeOfCreation = System.currentTimeMillis();
    }

    public void setText(String text)
    {
        taskText = text;
    }

    public String getTaskText()
    {
        return taskText;
    }

    public void setDone(boolean status)
    {
        done = status;
    }

    public void flipInProgress()
    {
        done = !done;
    }

    public boolean isDone()
    {
        return done;
    }

    public long getTimeOfCreation()
    {
        return timeOfCreation;
    }

    @Override
    public int compareTo(TodoItem other) {
        if (this.done && other.done)
        {
            if (this.timeOfCreation < other.timeOfCreation)
            {
                return -1;
            }
            return 1;
        }
        else if (this.done)
        {
            return 1;
        }

        return -1;
    }

    // TODO: edit this class as you want
}
