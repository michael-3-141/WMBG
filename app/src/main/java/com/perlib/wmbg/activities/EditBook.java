package com.perlib.wmbg.activities;

import android.app.DatePickerDialog;
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
import com.perlib.wmbg.fragments.DateLendedDialogFragment;
import com.perlib.wmbg.interfaces.BookContainerActivity;
import com.perlib.wmbg.misc.CommonLib;
import com.perlib.wmbg.misc.PrefKeys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * The EditBook activity.
 */
public class EditBook extends ActionBarActivity implements BookContainerActivity, DatePickerDialog.OnDateSetListener {

	private List<Book> items = new ArrayList<Book>();
	
	private int editPos;
	private Book editedItem;

	private Button btnSetLendedDate;
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
	    btnSetLendedDate = (Button) findViewById(R.id.btnSetLendedDate);
	    btnDelete = (Button) findViewById(R.id.btnDelete);
	    btnReturnBook = (Button) findViewById(R.id.btnReturnBook);
	    btnSendReminder = (Button) findViewById(R.id.btnSendReminder);
	    btnEdit = (Button)findViewById(R.id.btnEditBook);
	    
	    //Get currently edited item and fill in fields with its data
	    editedItem = items.get(editPos);
	    GregorianCalendar editedDate = new GregorianCalendar();
	    editedDate.setTimeInMillis(editedItem.getDateLended()*1000);
	    
	    //Remove unnecessary buttons. 
	    if(!editedItem.isLended())
	    {
	    	btnReturnBook.setVisibility(View.GONE);
	    	btnSendReminder.setVisibility(View.GONE);
	    }
	    
	    //Listeners
        btnSetLendedDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DateLendedDialogFragment fragment = new DateLendedDialogFragment(editedItem, EditBook.this);
                fragment.show(getSupportFragmentManager(), "dateLendedDialogFragmnt");
            }
        });

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
                    //Save
                    items.set(editPos, book);
					CommonLib.saveInfo(items);

                    //Start main activity
					/*Intent main = new Intent(getApplicationContext(), MainActivity.class);
					Bundle b = new Bundle();
					b.putParcelableArrayList("items", (ArrayList<? extends Parcelable>) items);
					main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					main.putExtras(b);
					startActivity(main);*/
                    onBackPressed();
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

    //Implement DateSetListener for date picker dialog
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        editedItem.setDateLended(c.getTimeInMillis() / 1000);
    }
}
