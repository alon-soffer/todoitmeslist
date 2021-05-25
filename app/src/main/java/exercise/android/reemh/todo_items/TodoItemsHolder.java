package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.util.List;


// TODO: feel free to add/change/remove methods as you want
public interface TodoItemsHolder extends Serializable {

  /** Get a copy of the current items list */
  List<TodoItem> getCurrentItems();

  void setCurrentItems(List<TodoItem> itemsToSet);
  /**
   * Creates a new TodoItem and adds it to the list, with the @param description and status=IN-PROGRESS
   * Subsequent calls to [getCurrentItems()] should have this new TodoItem in the list
   */
  void addNewInProgressItem(String description);

  /** mark the @param item as DONE */
  void markItemDone(TodoItem item);

  /** mark the @param item as IN-PROGRESS */
  void markItemInProgress(TodoItem item);

  /** delete the @param item */
  void deleteItem(TodoItem item);

  /** get amount of items */
  int getItemsSize();

  /** get the item from the given postion*/
  TodoItem getItemPos(int position);

  /** update the given item in the DB*/
  void updateItem(TodoItem item);
}
