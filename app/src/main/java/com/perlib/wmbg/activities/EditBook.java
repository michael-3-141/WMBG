package com.perlib.wmbg.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.perlib.wmbg.R;
import com.perlib.wmbg.book.Book;
import com.perlib.wmbg.fragments.EditBookFragment;
import com.perlib.wmbg.misc.CommonLib;
import com.perlib.wmbg.misc.Items;
import com.perlib.wmbg.misc.PrefKeys;

/**
 * The Book editing activity. Not used on tablets, as tablets have two pane layout for the list.
 */
public class EditBook extends ActionBarActivity {

	private Items items;
	
	private int editPos;
	private Book editedItem;

	private Button btnReturnBook;
	private Button btnSendReminder;
	private Button btnDelete;
	private Button btnEdit;
	private EditBookFragment fmtBook;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_editbook);

        //Setup action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

        //Get data from intent
	    Bundle b = getIntent().getExtras();
	    editPos = b.getInt("position");

        //Get items
        items = new Items(this);

	    //Create view references
	    fmtBook = (EditBookFragment) getSupportFragmentManager().findFragmentById(R.id.bookFragment);
	    btnDelete = (Button) findViewById(R.id.btnDelete);
	    btnReturnBook = (Button) findViewById(R.id.btnReturnBook);
	    btnSendReminder = (Button) findViewById(R.id.btnSendReminder);
	    btnEdit = (Button)findViewById(R.id.btnEditBook);
	    
	    //Get currently edited item and fill in fields with its data
	    editedItem = items.get(editPos);
        Bundle args = new Bundle();
        args.putParcelable("book", editedItem);
        fmtBook.setArguments(args);
	    
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
				CommonLib.deleteItem(editPos, EditBook.this);
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
			}
		});
	    
	    btnReturnBook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Return book and go to main activity
				CommonLib.returnItem(editPos, EditBook.this);
				Intent main = new Intent(getApplicationContext(), MainActivity.class);
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

                    //Start main activity
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

}
