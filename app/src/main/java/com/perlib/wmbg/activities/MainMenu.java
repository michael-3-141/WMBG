package com.perlib.wmbg.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.perlib.wmbg.R;
import com.perlib.wmbg.book.Book;
import com.perlib.wmbg.interfaces.OnDownloadComplete;
import com.perlib.wmbg.misc.CommonLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenu extends Activity implements OnDownloadComplete {

	Button btnSearchBook;
	Button btnScanBook;
	Button btnManualAddBook;
	
	IntentIntegrator scanIntegrator = new IntentIntegrator(this);
	List<Book> items = new ArrayList<Book>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.activity_menu);

        //View references
	    btnSearchBook = (Button) findViewById(R.id.btnSeachBooks);
	    btnScanBook = (Button) findViewById(R.id.btnScanBook);
	    btnManualAddBook = (Button) findViewById(R.id.btnManualAddBook);

        //Load items from file.
	    loadData();

        //Search book listener
	    btnSearchBook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Go to main list activity
				Intent searchBook = new Intent(getApplicationContext(), MainActivity.class);
				searchBook.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
				startActivity(searchBook);
			}
		});

        //Scan book listener
	    btnScanBook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    scanIntegrator.initiateScan();
				
			}
		});

        //Manual add book
	    btnManualAddBook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                //Go to addbook in manual mode
				Intent addBook = new Intent(getApplicationContext(), AddBook.class);
				addBook.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
				addBook.putExtra("mode", AddBook.MODE_MANUAL);
				startActivity(addBook);
			}
		});
	}

	private void loadData()
	{
        //Get the file
		String fs = System.getProperty("file.separator");
		File sd = Environment.getExternalStorageDirectory();
		File listfile = new File(sd+fs+"booklist.txt");

        //Check if file exists
		if(listfile.exists())
		{
			try {
				BufferedReader br = new BufferedReader(new FileReader(listfile));

                //Read the item file using BufferedReader and convert to Book list using Gson
				String line;
				List<Book> list = new ArrayList<Book>();
				Gson gson = new Gson();
				Book[] bookArray = new Book[]{};
				while((line = br.readLine()) != null)
				{
					bookArray = gson.fromJson(line, Book[].class);
				}
				list = new ArrayList<Book>(Arrays.asList(bookArray));
                //Assign the read info to the items list and close reader
				items = list;
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    //Handles response from barcode scanner
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
        //Use zxing integration to read data from intent
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
			String contents = scanResult.getContents();
			if(contents != null)
			{
                //Handle isbn code and use this as listener for download completion
				CommonLib.handleISBN(contents, getApplicationContext(), this);
                //Notify user of download in progress
                Toast.makeText(getApplicationContext(), R.string.bookDownloadStarted, Toast.LENGTH_LONG).show();
			}
		}
	}

    //Called when the book info download for the scanned isbn code is completed
	@Override
	public void onBookInfoDownloadComplete(Book result) {
		//Null check
		if(result == null)
		{
			Toast.makeText(getApplicationContext(), getString(R.string.InvalidISBN) , Toast.LENGTH_SHORT).show();
			return;
		}

        //Go to scan book activity to handle scan results.
		Intent scanBook = new Intent(getApplicationContext(), ScanBook.class);
		scanBook.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) items);
		scanBook.putExtra("result", (Parcelable) result);
		startActivity(scanBook);
	}

}
