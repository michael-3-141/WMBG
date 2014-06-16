package com.perlib.wmbg.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.perlib.wmbg.R;
import com.perlib.wmbg.book.Book;
import com.perlib.wmbg.fragments.BookFragment;
import com.perlib.wmbg.interfaces.BookContainerActivity;
import com.perlib.wmbg.misc.CommonLib;
import com.perlib.wmbg.misc.PrefKeys;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * The EditBook activity.
 */
public class EditBook extends ActionBarActivity implements BookContainerActivity {

	private List<Book> items = new ArrayList<Book>();
	
	private int editPos;
	private Book editedItem;

	private DatePicker dpDateLended;
	private Button btnReturnBook;
	private Button btnSendReminder;
	private Button btnDelete;
	private Button btnEdit;
	private BookFragment fmtBook;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_editbook);

        //Setup action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

        //Get data from intent
	    Bundle b = getIntent().getExtras();
	    items = b.getParcelableArrayList("items");
	    editPos = b.getInt("position");

	    //Create view references
	    fmtBook = (BookFragment) getSupportFragmentManager().findFragmentById(R.id.bookFragment);
	    dpDateLended = (DatePicker)findViewById(R.id.dpDateLended);
	    btnDelete = (Button) findViewById(R.id.btnDelete);
	    btnReturnBook = (Button) findViewById(R.id.btnReturnBook);
	    btnSendReminder = (Button) findViewById(R.id.btnSendReminder);
	    btnEdit = (Button)findViewById(R.id.btnEditBook);
	    
	    //Get currently edited item and fill in fields with its data
	    editedItem = items.get(editPos);
	    GregorianCalendar editedDate = new GregorianCalendar();
	    editedDate.setTimeInMillis(editedItem.getDateLended()*1000);
	    dpDateLended.updateDate(editedDate.get(GregorianCalendar.YEAR), editedDate.get(GregorianCalendar.MONTH), editedDate.get(GregorianCalendar.DAY_OF_MONTH));
	    
	    //Remove unnecessary buttons. 
	    if(!editedItem.isLended())
	    {
	    	btnReturnBook.setVisibility(View.GONE);
	    	btnSendReminder.setVisibility(View.GONE);
	    }
	    
	    //Listeners
	    btnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				CommonLib.deleteItem(editPos, PreferenceManager.getDefaultSharedPreferences(EditBook.this), EditBook.this, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete current book, save and go to main activity.
                        items.remove(editPos);
                        CommonLib.saveInfo(items);
                        Intent main = new Intent(getApplicationContext(), MainActivity.class);
                        main.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
                        startActivity(main);
                    }
                }, items);
				
			}
		});
	    
	    btnReturnBook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Return book and go to main activity
				items = CommonLib.returnItem(editPos, items);
				Intent main = new Intent(getApplicationContext(), MainActivity.class);
				main.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
				startActivity(main);
			}
		});
	    
	    btnSendReminder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                //Start email app to send reminder
				String uriText = null;
				uriText =
				"mailto:" + editedItem.getEmail() + 
				"?subject=" + Uri.encode(getString(R.string.emailSubject), "UTF-8") + 
				"&body=" + Uri.encode(PrefKeys.getEmailMessage(PreferenceManager.getDefaultSharedPreferences(EditBook.this)).replaceAll("@book@", editedItem.getName()));
				Uri uri = Uri.parse(uriText);
		        Intent sendEmail = new Intent(Intent.ACTION_SENDTO);
		        sendEmail.setData(uri);
		        startActivity(Intent.createChooser(sendEmail, getString(R.string.sendEmail)));
			}
		});
	    
	    btnEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Get book from book fragment
				Book book = fmtBook.getViewedBook();
                //Validate book name
				if(!(book.getName().length() == 0))
				{
                    //Set date lended to date lended DatePicker
					GregorianCalendar dateLendedGc = new GregorianCalendar(dpDateLended.getYear(), dpDateLended.getMonth(), dpDateLended.getDayOfMonth());
					book.setDateLended(dateLendedGc.getTimeInMillis()/1000);

                    //Save
                    items.set(editPos, book);
					CommonLib.saveInfo(items);

                    //Start main activity
					Intent main = new Intent(getApplicationContext(), MainActivity.class);
					Bundle b = new Bundle();
					b.putParcelableArrayList("items", (ArrayList<? extends Parcelable>) items);
					main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					main.putExtras(b);
					startActivity(main);
					finish();
				}
				else
				{
                    //Notify user about required fields
					Toast.makeText(getApplicationContext(), getString(R.string.requiredBookNameError), Toast.LENGTH_SHORT).show();
				}
			}
				
		});
	}

    //Implement BookContainerActivity for book fragment
	@Override
	public Book getBook() {
		return editedItem;
	}

}
