package exercise.android.reemh.todo_items;

import junit.framework.TestCase;

import org.junit.Test;

public class TodoItemsHolderImplTest extends TestCase {
    @Test
    public void test_when_addingTodoItem_then_callingListShouldHaveThisItem(){
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
        assertEquals(0, holderUnderTest.getCurrentItems().size());

        // test
        holderUnderTest.addNewInProgressItem("do shopping");

        // verify
        assertEquals(1, holderUnderTest.getCurrentItems().size());
    }

    // TODO: add at least 10 more tests to verify correct behavior of your implementation of `TodoItemsHolderImpl` class

    @Test
    public void test_when_addingMultipleTodoItems_then_callingListShouldHaveTheseItems(){
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
        assertEquals(0, holderUnderTest.getCurrentItems().size());

        // test
        holderUnderTest.addNewInProgressItem("do shopping");
        holderUnderTest.addNewInProgressItem("do shopping again");
        holderUnderTest.addNewInProgressItem("do shopping and again");
        holderUnderTest.addNewInProgressItem("do shopping one last time");


        // verify
        assertEquals(4, holderUnderTest.getCurrentItems().size());
    }

    @Test
    public void test_when_itemIsCreated_then_itIsInProgress(){
        // setup
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
        assertEquals(0, holderUnderTest.getCurrentItems().size());

        // test
        holderUnderTest.addNewInProgressItem("do shopping");

        TodoItem item = holderUnderTest.items.get(0);
        // verify
        assertFalse(item.isDone());
    }

    @Test
    public void test_when_itemIsChangedToDone_then_itIsDone(){
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
        holderUnderTest.addNewInProgressItem("do homework");
        TodoItem item = holderUnderTest.getItemPos(0);

        // test
        holderUnderTest.markItemDone(item);

        // verify
        assertTrue(holderUnderTest.items.get(0).isDone());
    }


    @Test
    public void test_when_itemIsChangedToDoneWithMultipleItems_then_itIsDone(){
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();

        holderUnderTest.addNewInProgressItem("dummy task 1");
        holderUnderTest.addNewInProgressItem("dummy task 2");
        holderUnderTest.addNewInProgressItem("do homework");
        TodoItem item = holderUnderTest.getItemPos(2);

        // test
        holderUnderTest.markItemDone(item);

        // verify
        assertTrue(holderUnderTest.items.get(2).isDone());
    }

    @Test
    public void test_when_itemIsChangedToInProgress_then_itIsInProgress(){
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();

        holderUnderTest.addNewInProgressItem("do homework");
        TodoItem item = holderUnderTest.getItemPos(0);

        // test
        holderUnderTest.markItemDone(item);
        holderUnderTest.markItemInProgress(item);

        // verify
        assertFalse(holderUnderTest.items.get(0).isDone());
    }


    @Test
    public void test_when_itemIsChangedToInProgressWithMultipleItems_then_itIsInProgress(){
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();

        holderUnderTest.addNewInProgressItem("dummy task 1");
        holderUnderTest.addNewInProgressItem("dummy task 2");
        holderUnderTest.addNewInProgressItem("do homework");
        TodoItem item = holderUnderTest.getItemPos(2);

        // test
        holderUnderTest.markItemDone(item);
        holderUnderTest.markItemInProgress(item);

        // verify
        assertFalse(holderUnderTest.items.get(2).isDone());
    }

    @Test
    public void test_when_itemIsRemoved_then_itIsRemoved(){
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
        holderUnderTest.addNewInProgressItem("eat lunch");
        TodoItem item = holderUnderTest.getItemPos(0);

        // test
        holderUnderTest.deleteItem(item);

        // verify
        assertEquals(0, holderUnderTest.getCurrentItems().size());
    }

    @Test
    public void test_when_itemIsRemovedFromMiddle_then_itIsRemovedAndOthersAreInPlace(){
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
        holderUnderTest.addNewInProgressItem("dummy task 1");
        holderUnderTest.addNewInProgressItem("dummy task 2");
        holderUnderTest.addNewInProgressItem("dummy task 3");
        holderUnderTest.addNewInProgressItem("eat lunch");
        holderUnderTest.addNewInProgressItem("dummy task 4");
        TodoItem item = holderUnderTest.getItemPos(3);
        TodoItem lastItem = holderUnderTest.getItemPos(4);

        // test
        holderUnderTest.deleteItem(item);

        // verify
        assertEquals(4, holderUnderTest.getCurrentItems().size());
        assertEquals(lastItem, holderUnderTest.getItemPos(3));
    }

    @Test
    public void test_when_itemIsMarkedDone_then_itMovesUnderInProgressItems()
    {
        TodoItemsHolderImpl holderUnderTest = new TodoItemsHolderImpl();
        holderUnderTest.addNewInProgressItem("task 1");
        holderUnderTest.addNewInProgressItem("task 2");
        holderUnderTest.addNewInProgressItem("task 3");

        TodoItem task1 = holderUnderTest.getItemPos(2);
        TodoItem task2 = holderUnderTest.getItemPos(1);
        TodoItem task3 = holderUnderTest.getItemPos(0);

        // test
        holderUnderTest.markItemDone(task3);

        // verify
        assertEquals(task3, holderUnderTest.getItemPos(2));
        assertEquals(task2, holderUnderTest.getItemPos(1));
        assertEquals(task1, holderUnderTest.getItemPos(0));

    }


}