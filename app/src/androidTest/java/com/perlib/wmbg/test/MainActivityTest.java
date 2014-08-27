package com.perlib.wmbg.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.perlib.wmbg.activities.MainActivity;
import com.perlib.wmbg.book.Book;
import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private Solo solo;
    public static final Book testBook = new Book("My Test", "My Author", "Mr. test", "t@t.t", System.currentTimeMillis()/1000, "http://bks4.books.google.co.il/books?id=wrOQLV6xB-wC&printsec=frontcover&img=1&zoom=1&imgtk=AFLRE72cMx0YEsa4NRYoFUrC8clNYF8eDX2KKSwIJbNyRZqXGgsR4akwbQeIzaC1SMV5BkQKEMXl9BvY5csYQGitrfSOhiqy757P24unQvZU_F5RiIvpHbESP25omkT6ilQEUgiQlCOT");

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo  = new Solo(getInstrumentation(), getActivity());
    }

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testListLongClick()
    {
        setupItems(testBook);

        solo.clickLongInList(0);

        assertTrue(solo.waitForDialogToOpen());
    }

    public void testItemsAreDisplayedInMainActivity()
    {
        solo.assertCurrentActivity("Wrong activiy", MainActivity.class);
        setupItems(testBook);
        ArrayList<TextView> item = solo.clickInList(0);
        assertEquals(testBook.getName(), item.get(0).getText());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    private void setupItems(Book... items)
    {
        List<Book> itemsList = new ArrayList<Book>(Arrays.asList(items));
        ((MainActivity)solo.getCurrentActivity()).items = itemsList;
    }
}
