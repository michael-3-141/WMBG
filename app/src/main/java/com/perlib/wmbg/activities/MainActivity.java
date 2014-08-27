package com.perlib.wmbg.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.perlib.wmbg.R;
import com.perlib.wmbg.book.Book;
import com.perlib.wmbg.fragments.EditBookFragment;
import com.perlib.wmbg.interfaces.EditBookFragmentEditListener;
import com.perlib.wmbg.misc.BookAdapter;
import com.perlib.wmbg.misc.CommonLib;
import com.perlib.wmbg.misc.Items;
import com.perlib.wmbg.misc.PrefKeys;

/**
 * The list activity. This activity has a list of all the books the user has.
 */
public class MainActivity extends ActionBarActivity implements EditBookFragmentEditListener {

	public Items items;

    private SharedPreferences prefs;
	private ListView bookList;
	private BookAdapter adapter;
	private SwipeDismissAdapter swipeAdapter;
	private EditText etSearch;
    private EditBookFragment fmtEditBook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        //Setup action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

        //View references
		bookList = (ListView)findViewById(R.id.bookList);
		etSearch = (EditText) findViewById(R.id.etSearch);
        fmtEditBook = (EditBookFragment) getSupportFragmentManager().findFragmentById(R.id.fmtEditBook);

        //Load the settings.
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Load items from SharedPreferences
        items = new Items(this);

        //Instantiate the book adapter
		adapter = new BookAdapter(this);

        //Instantiate experimental swipe feature.
		swipeAdapter = new SwipeDismissAdapter(adapter ,new OnDismissCallback() {
			
			@Override
			public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
			    for (int position : reverseSortedPositions) {
			    	if(PrefKeys.getSwipeMode(prefs) == PrefKeys.MODE_DELETE_ITEM)
			    	{
			    		CommonLib.deleteItem(position, MainActivity.this);

			    	}
			    	else if(PrefKeys.getSwipeMode(prefs) == PrefKeys.MODE_RETURN_ITEM)
			    	{
			    		CommonLib.returnItem(position, MainActivity.this);
			    	}
			    }
				
			}
		});

        //Apply swipe feature if setting is on.
		if(PrefKeys.getSwipeMode(prefs) == PrefKeys.MODE_NOTHING)
		{
			bookList.setAdapter(adapter);
		}
		else
		{
			bookList.setAdapter(swipeAdapter);
			swipeAdapter.setAbsListView(bookList);
		}

        //Add textwatcher for search.
		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				MainActivity.this.adapter.getFilter().filter(s);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});

        //Item long click handler
		bookList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, final View view,
					final int position, long id) {

                //Create dialog and set items depending on the state of the book.
				AlertDialog.Builder options_builder = new AlertDialog.Builder(MainActivity.this);
				String[] display;
				if(items.get(position).isLended())
				{
					display = new String[]{getString(R.string.sendReminder), getString(R.string.markAsReturned), getString(R.string.delete)};
				}
				else
				{
					display = new String[]{getString(R.string.delete)};
				}
                //Set title, items and OnClick listener.
				options_builder.setTitle(getString(R.string.options)).setItems(display, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//Since options are different depending on state, the id's are different too and this if/else is needed.
						if(items.get(position).isLended())
						{
							switch(which){
							case 0:
								//Send Reminder button
								String uriText = null;
								uriText =
								"mailto:"+items.get(position).getEmail() + 
								"?subject=" + Uri.encode(getString(R.string.emailSubject), "UTF-8") + 
								"&body=" + Uri.encode(PrefKeys.getEmailMessage(prefs).replaceAll("@book@", items.get(position).getName()));
								Uri uri = Uri.parse(uriText);
						        Intent sendEmail = new Intent(Intent.ACTION_SENDTO);
						        sendEmail.setData(uri);
						        startActivity(Intent.createChooser(sendEmail, getString(R.string.sendEmail)));
								break;
							case 1:
                                //Return button
								CommonLib.returnItem(position, MainActivity.this);
								break;
							case 2:
                                //Delete button
								CommonLib.deleteItem(position, MainActivity.this);
								break;
							default:
								break;
							}
						}
						else
						{
                            //Only option is delete
							CommonLib.deleteItem(position, MainActivity.this);
						}
					}
				});

                //Show the dialog
				AlertDialog dialog = options_builder.create();
				dialog.show();
				return true;
			}
		});

        //Item click listener. Opens EditBook activity.
		bookList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                //When on tablet mode, just update fragment
                if(!getResources().getBoolean(R.bool.isTablet)) {
                    goto_editbook(position);
                }
                else{
                    view.setSelected(true);
                    fmtEditBook.setViewedBook(items.get(position));
                }
			}

		});
		
		
	}

    //Opens editbook activity at position.
	private void goto_editbook(int position) {
        //Create intent
		Intent editbook = new Intent(getApplicationContext(), EditBook.class);
        //Add items and position
		Bundle b = new Bundle();
		b.putInt("position", position);
		editbook.putExtras(b);
        //Start the activity
		startActivity(editbook);
	}

    //Activity lifecycle handlers.
	@Override
	public void onResume()
	{
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onStart()
	{
		super.onStart();
		//EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop()
	{
		super.onStop();
		//EasyTracker.getInstance(this).activityStop(this);
	}

    @Override
    public void updateEditedBook(Book editedBook, int position) {

    }
}
