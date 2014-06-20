package com.perlib.wmbg.asynctasks;

import android.os.AsyncTask;

import com.perlib.wmbg.interfaces.OnDownloadComplete;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Async task to download a book from isbnDB.
 *
 * @deprecated Use {@link DownloadBookInfoTask} instead.
 */
@Deprecated
public class DownloadInfoTask extends AsyncTask<String, String, String> {

	/** The api key. */
	private final String API_KEY = "33BNPPTM";
	
	/** The listener. */
	@SuppressWarnings("unused")
	private OnDownloadComplete listener;
	
	/**
	 * Instantiates a new download info.
	 *
	 * @param listener the listener
	 */
	public DownloadInfoTask(OnDownloadComplete listener) {
		super();
		this.listener = listener;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected String doInBackground(String... isbn) {
		
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet("http://isbndb.com/api/v2/json/"+API_KEY+"/book/"+isbn[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
	}
	
	/**
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result)
	{
		//listener.onBookInfoDownloadComplete(result);
	}

}
