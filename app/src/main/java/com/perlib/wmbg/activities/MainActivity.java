package com.perlib.wmbg.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.Toast;

import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.perlib.wmbg.R;
import com.perlib.wmbg.book.Book;
import com.perlib.wmbg.interfaces.OnDownloadComplete;
import com.perlib.wmbg.misc.BookAdapter;
import com.perlib.wmbg.misc.CommonLib;
import com.perlib.wmbg.misc.PrefKeys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The list activity. This activity has a list of all the books the user has.
 */
public class MainActivity extends ActionBarActivity implements OnDownloadComplete{

	
	public List<Book> items = new ArrayList<Book>();

    private SharedPreferences prefs;
	private ListView bookList;
	private BookAdapter adapter;
	private SwipeDismissAdapter swipeAdapter;
	private EditText etSearch;

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

        //Load the settings. TODO: Replace with SharedPreferences
		//settings = CommonLib.loadSettings(getApplicationContext());
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Load items from intent if exists
		if(getIntent().getExtras() != null)
		{
			Bundle b = getIntent().getExtras();
			items = b.getParcelableArrayList("items");
		}

        //Instantiate the book adapter
		adapter = new BookAdapter(this);

        //Instantiate experimental swipe feature.
		swipeAdapter = new SwipeDismissAdapter(adapter ,new OnDismissCallback() {
			
			@Override
			public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
			    for (int position : reverseSortedPositions) {
			    	if(PrefKeys.getSwipeMode(prefs) == PrefKeys.MODE_DELETE_ITEM)
			    	{
			    		deleteItem(position);
			    	}
			    	else if(PrefKeys.getSwipeMode(prefs) == PrefKeys.MODE_RETURN_ITEM)
			    	{
			    		returnItem(position);
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
								returnItem(position);
								break;
							case 2:
                                //Delete button
								deleteItem(position);
								break;
							default:
								break;
							}
						}
						else
						{
                            //Only option is delete
							deleteItem(position);
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
				goto_editbook(position);
			}

		});
		
		
	}

    //Opens editbook activity at position.
	private void goto_editbook(int position) {
        //Create intent
		Intent editbook = new Intent(getApplicationContext(), EditBook.class);
        //Add items and position
		Bundle b = new Bundle();
		b.putParcelableArrayList("items", (ArrayList<? extends Parcelable>) items);
		b.putInt("position", position);
		editbook.putExtras(b);
        //Start the activity
		startActivity(editbook);
	}

    //Delete item (Includes all confirmation dialog handling)
	private void deleteItem(final int position)
	{
        //Check if delete confirmation is on
		if(PrefKeys.isConfirmDelete(prefs))
		{
            //Create dialog, and set button
			AlertDialog.Builder delete_builder = new AlertDialog.Builder(MainActivity.this);
			delete_builder.setPositiveButton(getString(R.string.deleteYes) , new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//Remove item, refresh list and save data
					items.remove(position);
					swipeAdapter.notifyDataSetChanged();
					CommonLib.saveInfo(items);
				}
			});
			delete_builder.setNegativeButton(getString(R.string.deleteCancel), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
            //Set the dialog message and show the dialog
			delete_builder.setMessage(getString(R.string.deleteConfirm) + ' ' + '"' + items.get(position).getName() + '"' + "?").setTitle(getString(R.string.deleteConfirmTitle));
			AlertDialog dialog = delete_builder.create();
			dialog.show();
		}
		else
		{
            //Remove item, refresh list and save data
			items.remove(position);
			swipeAdapter.notifyDataSetChanged();
			CommonLib.saveInfo(items);
		}
	}
	
	//Return an item
	private void returnItem(final int position)
	{
		Book item = items.get(position);
        item.returnBook();
		items.set(position, item);
		CommonLib.saveInfo(items);
	    adapter.notifyDataSetChanged();
	}
	
	
	private void returnOrDeleteItem(final int position)
	{
		AlertDialog.Builder delete_or_return_builder = new AlertDialog.Builder(MainActivity.this);
		delete_or_return_builder.setPositiveButton(getString(R.string.chooseReturn) , new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				returnItem(position);
			}
		});
		delete_or_return_builder.setNegativeButton(getString(R.string.chooseDelete), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				deleteItem(position);
			}
		});
		delete_or_return_builder.setMessage(getString(R.string.returnOrDelete));
		AlertDialog dialog = delete_or_return_builder.create();
		dialog.show();
	}

	@Override
	public void onBookInfoDownloadComplete(Book result) {
		
		if(result == null)
		{
			Toast.makeText(getApplicationContext(), getString(R.string.InvalidISBN) , Toast.LENGTH_SHORT).show();
			return;
		}
		final List<Integer> matcheIds = new ArrayList<Integer>();
		List<Book> matches = new ArrayList<Book>();
		List<String> matchDisplay = new ArrayList<String>();
		int it = 0;
		for(Iterator<Book> i = items.iterator(); i.hasNext(); )
		{
			Book item = i.next();
			if(item.getName().equals(result.getName()))
			{
				matcheIds.add(it);
				matches.add(item);
				matchDisplay.add("Lended To: " + item.getLendedTo());
			}
			it++;
		}
		if(matches.size() == 1)
		{
			returnOrDeleteItem(matcheIds.get(0));
		}
		else if(matches.size() == 0)
		{
			return;
		}
		else
		{
			String[] displayArray = new String[]{};
			displayArray = matchDisplay.toArray(displayArray);
			AlertDialog.Builder options_builder = new AlertDialog.Builder(MainActivity.this);
			options_builder.setTitle(getString(R.string.duplicateBooks)).setItems(displayArray, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					returnOrDeleteItem(matcheIds.get(which));
				}
			});
			
			AlertDialog dialog = options_builder.create();
			dialog.show();
		}
	}


    //Activity lifecycle handlers.
	@Override
	public void onResume()
	{
		super.onResume();
		items = CommonLib.loadData();
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
		CommonLib.saveInfo(items);
	}
}
