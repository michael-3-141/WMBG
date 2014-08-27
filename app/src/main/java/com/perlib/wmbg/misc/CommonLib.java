package com.perlib.wmbg.misc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.perlib.wmbg.R;
import com.perlib.wmbg.asynctasks.DownloadBookInfoTask;
import com.perlib.wmbg.book.Book;
import com.perlib.wmbg.interfaces.OnDownloadComplete;

import java.util.regex.Pattern;

public class CommonLib {


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
	 * @param cx the context
	 */
	public static void deleteItem(final int position, final Context cx)
	{
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Items(cx).remove(position);
            }
        };

		if(PrefKeys.isConfirmDelete(PreferenceManager.getDefaultSharedPreferences(cx)))
		{
			AlertDialog.Builder delete_builder = new AlertDialog.Builder(cx);
			delete_builder.setPositiveButton(cx.getString(R.string.deleteYes) , listener);
			delete_builder.setNegativeButton(cx.getString(R.string.deleteCancel), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			delete_builder.setMessage(cx.getString(R.string.deleteConfirm) + ' ' + '"' + new Items(cx).get(position).getName() + '"' + "?").setTitle(cx.getString(R.string.deleteConfirmTitle));
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
	 * @return the list
	 */
	public static void returnItem(final int position, Context cx)
	{
		Book item = new Items(cx).get(position);
        item.returnBook();
        new Items(cx).set(position, item);
	}

    public static boolean isEmail(String email) {
        return Pattern.compile(".+@.+\\.[a-z]+").matcher(email).matches();
    }
}
