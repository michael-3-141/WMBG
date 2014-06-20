package com.perlib.wmbg.asynctasks;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.perlib.wmbg.interfaces.OnContactLoadingComplete;

import java.util.HashMap;
import java.util.Map;

public class GetContactNamesTask extends AsyncTask<Void, String, HashMap<Integer, String>> {

	
	private OnContactLoadingComplete listener;
	
	
	ContentResolver cr;
	
	
	public GetContactNamesTask(OnContactLoadingComplete listener, ContentResolver cr) {
		super();
		this.listener = listener;
		this.cr = cr;
	}

	@SuppressLint("UseSparseArrays")
	@Override
	protected HashMap<Integer, String> doInBackground(Void...nothing) {
        Cursor cur = null;
		Map<Integer, String> names = new HashMap<Integer, String>();
		try{
	        cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
	        if (cur.getCount() > 0) {
	            Log.i("AutocompleteContacts", "Reading   contacts...");
	             
	            String name = "";
	            int id;
	             
	            while (cur.moveToNext()) 
	            {
	                name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	                id = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    if(name != null) {
                        names.put(id, name);
                    }
	            }
	 
	        }
	        else
	        {
	        	Log.i("contacts", "No contacts found");
	        }
	        cur.close();
	        
	    } catch (NullPointerException e) {
	        Log.i("AutocompleteContacts","Exception : "+ e);
	    }
        if(cur != null)cur.close();
	    return (HashMap<Integer, String>) names;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(HashMap<Integer, String> result)
	{
		try{
			listener.OnNameLoadingFinished(result);
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	}

}
