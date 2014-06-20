package com.perlib.wmbg.misc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.perlib.wmbg.R;
import com.perlib.wmbg.asynctasks.DownloadBookInfoTask;
import com.perlib.wmbg.book.Book;
import com.perlib.wmbg.interfaces.OnDownloadComplete;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommonLib {

	
	/**
	 * Save info.
	 *
	 * @param list the list
	 */
	public static void saveInfo(List<Book> list)
	{
		String eol = System.getProperty("line.separator");
		File externalStorage = Environment.getExternalStorageDirectory();
		if(externalStorage.canWrite())
		{
			File bookList = new File(externalStorage,"booklist.txt");
			try {
				FileWriter fw = new FileWriter(bookList);
				BufferedWriter bw = new BufferedWriter(fw);
				Book[] bookArray = new Book[]{};
				bookArray = list.toArray(bookArray);
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				String json = gson.toJson(bookArray);
				//Log.d("json", json);
				bw.write(json+eol);
				bw.close();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Read contact data.
	 *
	 * @param cr the cr
	 * @return the object[]
	 */
	@SuppressLint("UseSparseArrays")
	public static Object[] readContactData(ContentResolver cr) {
        Object[] results = new Object[3];
		try{
        	List<String> names = new ArrayList<String>();
        	Map<Integer, String> emails = new HashMap<Integer, String>();
        	Map <Integer, List<String>> contacts = new HashMap<Integer, List<String>>();
            /*********** Reading Contacts Name **********/

             
            //Query to get contact name
             
            Cursor cur = cr
                    .query(ContactsContract.Contacts.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
            // If data data found in contacts 
            if (cur.getCount() > 0) {
                
            	
                Log.i("AutocompleteContacts", "Reading   contacts........");
                 
                String name;
                String id;
                 
                while (cur.moveToNext()) 
                {
                    name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String email = "";
                    if(name != null)
                    {
						names.add(name);
                    }
                    Cursor cur1 = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    if(cur1.getCount()>0)
                    {
	                    while(cur1.moveToNext())
	                    {
	                    	email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
	                    	if(email != null)
	                    	{
								emails.put(Integer.parseInt(id), email);
	                    	}
	                    }
                    }
                    cur1.close();
                    List<String> line = new ArrayList<String>();
                    line.add(name);
                    line.add(email);
                    contacts.put(Integer.parseInt(id), line);
                }  // End while loop
 
            } // End Cursor value check
            else
            {
            	Log.i("contacts", "No contacts found");
            }
            cur.close();
            
            results[0] = names;
            results[1] = contacts;
            results[2] = emails;
	    } catch (NullPointerException e) {
	        Log.i("AutocompleteContacts","Exception : "+ e);
	    }
        return results;
   }

	
	/**
	 * Checks if is connected to internet.
	 *
	 * @param cx the cx
	 * @return true, if is connected to internet
	 */
	public static boolean isConnectedToInternet(Context cx){
	    ConnectivityManager connectivity = (ConnectivityManager)cx.getSystemService(Context.CONNECTIVITY_SERVICE);
	      if (connectivity != null) 
	      {
	          NetworkInfo[] info = connectivity.getAllNetworkInfo();
	          if (info != null)
                  for (NetworkInfo anInfo : info)
                      if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                          return true;
                      }

	      }
	      return false;
	}
	
	/**
	 * Handle isbn.
	 *
	 * @param isbn the isbn
	 * @param cx the cx
	 * @param listener the listener
	 */
	public static void handleISBN(String isbn, Context cx, OnDownloadComplete listener)
	{
		if(isbn.length() == 0){Toast.makeText(cx, cx.getString(R.string.InvalidISBN) , Toast.LENGTH_SHORT).show();return;}
		if(!CommonLib.isConnectedToInternet(cx)){Toast.makeText(cx, cx.getString(R.string.noConnection) , Toast.LENGTH_SHORT).show();return;}
		DownloadBookInfoTask downloader = new DownloadBookInfoTask(listener);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			downloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, isbn);
		}
		else
		{
			downloader.execute(isbn);
		}
	}
	
	/**
	 * Delete item.
	 *
	 * @param position the position
	 * @param prefs the settings
	 * @param cx the cx
	 * @param listener the listener
	 * @param items the items
	 */
	public static void deleteItem(final int position, SharedPreferences prefs, Context cx, DialogInterface.OnClickListener listener, List<Book> items)
	{

		if(PrefKeys.isConfirmDelete(prefs))
		{
			AlertDialog.Builder delete_builder = new AlertDialog.Builder(cx);
			delete_builder.setPositiveButton(cx.getString(R.string.deleteYes) , listener);
			delete_builder.setNegativeButton(cx.getString(R.string.deleteCancel), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
				}
			});
			delete_builder.setMessage(cx.getString(R.string.deleteConfirm) + ' ' + '"' + items.get(position).getName() + '"' + "?").setTitle(cx.getString(R.string.deleteConfirmTitle));
			AlertDialog dialog = delete_builder.create();
			dialog.show();
		}
		else
		{
			listener.onClick(null, 0);
		}
	}
	
	/**
	 * Return item.
	 *
	 * @param position the position
	 * @param items the items
	 * @return the list
	 */
	public static List<Book> returnItem(final int position, List<Book> items)
	{
		Book item = items.get(position);
        item.returnBook();
		items.set(position, item);
		CommonLib.saveInfo(items);
		return items;
	}

    /**
	 * Load data.
	 *
	 * @return the list
	 */
	public static List<Book> loadData()
	{
		String fs = System.getProperty("file.separator");
		File sd = Environment.getExternalStorageDirectory();
		File listfile = new File(sd+fs+"booklist.txt");
		List<Book> list = new ArrayList<Book>();
		
		if(listfile.exists())
		{
			try {
				BufferedReader br = new BufferedReader(new FileReader(listfile));
				
				String line;
				Gson gson = new Gson();
				Book[] bookArray = new Book[]{};
				while((line = br.readLine()) != null)
				{
					bookArray = gson.fromJson(line, Book[].class);
				}
				list = new ArrayList<Book>(Arrays.asList(bookArray));
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

    public static boolean isEmail(String email) {
        return Pattern.compile(".+@.+\\.[a-z]+").matcher(email).matches();
    }
}
