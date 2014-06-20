package com.perlib.wmbg.asynctasks;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.perlib.wmbg.interfaces.OnEmailLoadingListener;

// TODO: Auto-generated Javadoc
/**
 * The Class GetContactEmail.
 */
public class GetContactEmailTask extends AsyncTask<Integer, String, String> {

	/** The cr. */
	ContentResolver cr;
	
	/** The listener. */
	OnEmailLoadingListener listener;
	

	/**
	 * Instantiates a new gets the contact email.
	 *
	 * @param cr the cr
	 * @param listener the listener
	 */
	public GetContactEmailTask(ContentResolver cr, OnEmailLoadingListener listener) {
		super();
		this.cr = cr;
		this.listener = listener;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected String doInBackground(Integer... id) {
		String email = "";
		try{
	        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{Integer.toString(id[0])}, null);
	        if(cur.getCount()>0)
	        {
	            while(cur.moveToNext())
	            {
	            	email = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
	            }
	        }
	        cur.close();
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		return email;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result)
	{
		try{
			listener.OnEmailLoadingCompleted(result);
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	}

}
