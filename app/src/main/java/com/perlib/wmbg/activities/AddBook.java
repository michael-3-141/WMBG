package com.perlib.wmbg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.perlib.wmbg.R;
import com.perlib.wmbg.asynctasks.DownloadBookInfoTask;
import com.perlib.wmbg.book.Book;
import com.perlib.wmbg.fragments.BookFragment;
import com.perlib.wmbg.interfaces.BookContainerActivity;
import com.perlib.wmbg.interfaces.OnDownloadComplete;
import com.perlib.wmbg.misc.CommonLib;
import com.perlib.wmbg.misc.Items;

import java.util.GregorianCalendar;

/**
 * Add book activity. Used from scan book and for manual mode.
 */
public class AddBook extends ActionBarActivity implements OnDownloadComplete, BookContainerActivity {

	private Items items;
	private DownloadBookInfoTask downloader;
    
    private EditText etISBN;
    private Button btnDownloadInfo;
    private BookFragment fmtBook;
	private Button btnAddBook;
	
    //Modes for the activity. Auto doesn't show the manual isbn option while manual does.
	private int mode;
	
	public static final int MODE_AUTO = 0;
	public static final int MODE_MANUAL = 1;
	
	
	
	/**
	 *  Called when the activity is first created.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_addbook);

        //Setup action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	    
		//Create view references
	    btnAddBook = (Button)findViewById(R.id.btnAddBook);
	    etISBN = (EditText)findViewById(R.id.etISBN);
	    fmtBook = (BookFragment) getSupportFragmentManager().findFragmentById(R.id.bookFragment);
	    btnDownloadInfo = (Button)findViewById(R.id.btnDownloadInfo);

        //Get items
        items = new Items(this);
	    
	    //Get mode and apply changes
	    Bundle b = getIntent().getExtras();
	    mode = b.getInt("mode");
	    Book book = b.getParcelable("book");
	    if(mode == MODE_AUTO)
	    {
	    	fmtBook.setViewedBook(book);
	    	etISBN.setVisibility(View.GONE);
	    	btnDownloadInfo.setVisibility(View.GONE);
	    }
	    
	    //Listeners
	    btnAddBook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Get book from book fragment
				Book book = fmtBook.getViewedBook();
                //Book name validation
				if(!(book.getName().length() == 0))
				{
                    //Set lended date to now for book.
					GregorianCalendar dateLendedGc = new GregorianCalendar();
					book.setDateLended(dateLendedGc.getTimeInMillis()/1000);

                    //Add item and save
                    items.add(book);

                    //Go back to main activity and remove add book from activity stack to avoid confusion
					Intent main = new Intent(getApplicationContext(), MainActivity.class);
					main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(main);
					finish();
				}
				else
				{
                    //Notify user of field requirement
					Toast.makeText(getApplicationContext(), getString(R.string.requiredBookNameError), Toast.LENGTH_SHORT).show();
				}
			}
		});
	    
	    //Download info listener
	    btnDownloadInfo.setOnClickListener(new OnClickListener() {
			
	    	
			@Override
			public void onClick(View v) {
			//Start book info download with this as listener
		    CommonLib.handleISBN(etISBN.getText().toString(), AddBook.this, AddBook.this);
			}
		});
	    
	}

	//On book download finished.
	@Override
	public void onBookInfoDownloadComplete(Book result) {
		//Null check
		if(result == null)
		{
			Toast.makeText(getApplicationContext(), getString(R.string.InvalidISBN) , Toast.LENGTH_SHORT).show();
			return;
		}
        //Pass book to book fragment
		fmtBook.setViewedBook(result);
		
	}

    //Cancel AsyncTasks onDestroy to avoid crashes when they finish
	@Override
	public void onDestroy()
	{
		if(downloader != null)
		{
			downloader.cancel(true);
		}
		super.onDestroy();
	}

    //Implement book container activity for book fragment to use
    @Override
    public Book getBook() {
        return null;
    }
}
