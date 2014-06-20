package com.perlib.wmbg.asynctasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.perlib.wmbg.book.Book;
import com.perlib.wmbg.book.GoogleBooksBook;
import com.perlib.wmbg.interfaces.OnDownloadComplete;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// TODO: Auto-generated Javadoc
/**
 * AsyncTask to download a book from Google Books.
 */
public class DownloadBookInfoTask extends AsyncTask<String, Void, Book> {

	/** The listener. */
	private OnDownloadComplete listener;
	
	/**
	 * Instantiates a new download book info.
	 *
	 * @param listener the listener
	 */
	public DownloadBookInfoTask(OnDownloadComplete listener) {
		this.listener = listener;
	}

	/**
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Book doInBackground(String... params) {
		//Validate arguments
		if(params.length != 1) throw new IllegalArgumentException();
		String isbn = params[0];
		if(isbn == null || isbn == "") throw new IllegalArgumentException();
		
		try {
			//Read url
			URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn+"+isbn);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(connection.getInputStream());
			
			//Convert InputStream to String
		    String line = "";
		    StringBuilder total = new StringBuilder();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(in));
		    while ((line = rd.readLine()) != null) { 
		        total.append(line); 
		    }
		    
		    Gson gson = new Gson();
		    GoogleBooksBook book = gson.fromJson(total.toString(), GoogleBooksBook.class);
		    return book.toBook();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(Book result) {
		listener.onBookInfoDownloadComplete(result);
	}

}
