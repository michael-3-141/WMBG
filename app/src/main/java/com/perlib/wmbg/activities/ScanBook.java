package com.perlib.wmbg.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.perlib.wmbg.R;
import com.perlib.wmbg.book.Book;
import com.perlib.wmbg.misc.CommonLib;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * The scan book activity. Gets the info from main menu and displays several options for the user to do with the book.
 */
public class ScanBook extends ActionBarActivity {

	
	TextView tvBookName;
	TextView tvBookAuthor;
    ImageView ivBookImage;
	Button btnAddBook;
	Button btnReturnBook;
	Button btnEditBook;
	
	
	List<Book> items = new ArrayList<Book>();
	List<Book> matchedItems = new ArrayList<Book>();
	List<Integer> matchedItemsPos = new ArrayList<Integer>();
	List<Book> matchedLendedItems = new ArrayList<Book>();
	List<Integer> matchedLendedItemsPos = new ArrayList<Integer>();
	
	Book result = new Book();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_scanbook);

        //Setup action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

        //Create view references
	    tvBookName = (TextView) findViewById(R.id.tvBookName);
	    tvBookAuthor = (TextView) findViewById(R.id.tvBookAuthor);
        ivBookImage = (ImageView) findViewById(R.id.ivBookImage);
	    btnAddBook = (Button) findViewById(R.id.btnAddBook);
	    btnReturnBook = (Button) findViewById(R.id.btnReturnBook);
	    btnEditBook = (Button) findViewById(R.id.btnEditBook);

        //Get data from intent
		Bundle b = getIntent().getExtras();
		items = b.getParcelableArrayList("items");
		result = b.getParcelable("result");

        //Set book preview to result
		tvBookName.setText(result.getName());
		tvBookAuthor.setText(getString(R.string.by) + result.getAuthor());
        Picasso.with(this).load(result.getThumbnailUrl()).into(ivBookImage);

        //Check for items for this book
		int j = 0;
        for (Book item : items) {
            if (item.getName().equals(result.getName())) {
                //Adds item to matched items
                matchedItems.add(item);
                matchedItemsPos.add(j);
                if (item.getLendedTo().length() > 0) {
                    //Adds item to matched lended items
                    matchedLendedItems.add(item);
                    matchedLendedItemsPos.add(j);
                }
            }
            j++;
        }

        //
		if(matchedItems.size() == 0)
		{
			btnReturnBook.setVisibility(View.GONE);
			btnEditBook.setVisibility(View.GONE);
		}
		else
		{
			boolean lended = false;
			boolean isFirst = true;
			String display = getString(R.string.returnScannedBook);
            for (Book item : matchedItems) {
                if (item.getLendedTo().length() > 0) {
                    if (!lended) lended = true;
                    if (isFirst) {
                        display += " " + item.getLendedTo();
                    } else {
                        display += " or " + item.getLendedTo();
                    }
                    isFirst = false;
                }
            }
			if(!lended)
			{
				btnReturnBook.setVisibility(View.GONE);
			}
			else
			{
				btnReturnBook.setText(display);
			}
		}
	    
		
	    btnAddBook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent addBook = new Intent(getApplicationContext(), AddBook.class);
				addBook.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
				addBook.putExtra("mode", AddBook.MODE_AUTO);
				addBook.putExtra("book", result);
				startActivity(addBook);
			}
		});
	    
	    btnReturnBook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(matchedLendedItems.size() == 1)
				{
					Book item = matchedLendedItems.get(0);
                    item.returnBook();
					items.set(matchedLendedItemsPos.get(0), item);
					CommonLib.saveInfo(items);
					Intent main = new Intent(getApplicationContext(), MainActivity.class);
					main.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
					startActivity(main);
				}
				else
				{
					List<String> display = new ArrayList<String>();
                    for (Book item : matchedLendedItems) {
                        display.add(getString(R.string.lendedToDisplay) + item.getLendedTo());
                    }
					String[] displayArray = new String[]{};
					displayArray = display.toArray(displayArray);
					AlertDialog.Builder duplicateDialog = new AlertDialog.Builder(ScanBook.this);
					duplicateDialog.setTitle(getString(R.string.duplicateBooks)).setItems(displayArray, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							Book item = matchedLendedItems.get(which);
							item.setLendedTo("");
							item.setDateLended(-1);
							item.setEmail("");
							items.set(matchedLendedItemsPos.get(which), item);
							CommonLib.saveInfo(items);
							Intent main = new Intent(getApplicationContext(), MainActivity.class);
							main.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
							startActivity(main);
						}
					});
					duplicateDialog.show();
				}
			}
		});
	    
	    btnEditBook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(matchedItems.size() == 1)
				{
					Intent editBook = new Intent(getApplicationContext(), EditBook.class);
					editBook.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
					editBook.putExtra("position", matchedItemsPos.get(0));
					startActivity(editBook);
				}
				else
				{
					List<String> display = new ArrayList<String>();
                    for (Book item : matchedItems) {
                        if (item.getLendedTo().length() < 1) {
                            display.add(getString(R.string.lendedToDisplay) + getString(R.string.none));
                        } else {
                            display.add(getString(R.string.lendedToDisplay) + item.getLendedTo());
                        }
                    }
					String[] displayArray = new String[]{};
					displayArray = display.toArray(displayArray);
					AlertDialog.Builder duplicateDialog = new AlertDialog.Builder(ScanBook.this);
					duplicateDialog.setTitle(getString(R.string.duplicateBooks)).setItems(displayArray, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							Intent editBook = new Intent(getApplicationContext(), EditBook.class);
							editBook.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
							editBook.putExtra("position", matchedItemsPos.get(which));
							startActivity(editBook);
						}
					});
					duplicateDialog.show();
				}
			}
			
		});
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		items = CommonLib.loadData();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
		CommonLib.saveInfo(items);
	}
}
