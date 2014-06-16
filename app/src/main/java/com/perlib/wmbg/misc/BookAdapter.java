package com.perlib.wmbg.misc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.perlib.wmbg.R;
import com.perlib.wmbg.activities.MainActivity;
import com.perlib.wmbg.book.Book;
import com.squareup.picasso.Picasso;

/**
 * A custom adapter to handle a list of books.
 * Can only be used in the MainActivity class.
 */
public class BookAdapter extends BaseAdapter implements Filterable {
	
	/** The activity. */
	private MainActivity activity;
	
	/** The colors. */
	private int[] colors = new int[] { 0x30ffffff, 0x30808080 };
	
	/** The cx. */
	private Context cx;
	
	/** The filtered. */
	boolean filtered;
	
	/** The Filtered list. */
	private ArrayList<Book> FilteredList = new ArrayList<Book>();
	
	/** The filter. */
	private BookFilter filter = new BookFilter(); 
	
	/**
	 * Instantiates a new book adapter.
	 *
	 * @param activity the activity
	 */
	public BookAdapter(MainActivity activity) {
		this.activity = activity;
		cx = activity.getApplicationContext();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) cx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.simple_list_item_3, parent, false);

		int colorPos = position % colors.length;
		v.setBackgroundColor(colors[colorPos]);

		Book item = getItem(position);

		TextView text1 = (TextView) v.findViewById(R.id.text1);
		TextView text2 = (TextView) v.findViewById(R.id.text2);
		TextView text3 = (TextView) v.findViewById(R.id.text3);
		TextView text4 = (TextView) v.findViewById(R.id.text4);
		ImageView iv = (ImageView) v.findViewById(R.id.image);

		String displayAuthor = cx.getString(R.string.by) + item.getAuthor();
		String displayLendedTo = cx.getString(R.string.lendedToDisplay);
		String displayDateLended = "";

		if(item.isLended())
		{
			displayLendedTo += item.getLendedTo();
		}
		else
		{
			displayLendedTo += cx.getString(R.string.none);
		}

		if(item.isLended())
		{
			GregorianCalendar gcDateLended = new GregorianCalendar();
			gcDateLended.setTimeInMillis(item.getDateLended()*1000);
			SimpleDateFormat format = new SimpleDateFormat("d/M/y", Locale.US);
			displayDateLended = cx.getString(R.string.dateLendedDisplay) + format.format(gcDateLended.getTime());
		}

		text1.setText(item.getName());
		text2.setText(displayAuthor);
		text3.setText(displayLendedTo);
		text4.setText(displayDateLended);

		File file = null;
		try{
			file = new File(item.getThumbnailUrl());
		}catch(Exception e){
			
		}
		if(file != null)
		{
			if(file.exists()){
				Picasso.with(cx).load(file).into(iv);
				return v;
			}
		}
		
		Picasso.with(cx).load(item.getThumbnailUrl()).into(iv);
		
		return v;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return !filtered ? activity.items.size() : FilteredList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Book getItem(int position) {
		return !filtered ? activity.items.get(position) : FilteredList.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * The Class BookFilter.
	 */
	public class BookFilter extends Filter{
		
        /* (non-Javadoc)
         * @see android.widget.Filter#performFiltering(java.lang.CharSequence)
         */
        @SuppressLint("DefaultLocale")
		@Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<Book> FilteredList = new ArrayList<Book>();
            String filterString = constraint.toString().toLowerCase();
            Book item;
        	
            FilterResults Result = new FilterResults();
            if(constraint.length() == 0 ){
            	return null;
            }

            for(int i = 0; i<activity.items.size(); i++){
                item = activity.items.get(i);
                if(item.getName().toLowerCase().contains(filterString)){
                    FilteredList.add(activity.items.get(i));
                }
            }
            Result.values = FilteredList;
            Result.count = FilteredList.size();

            return Result;
        }

        /* (non-Javadoc)
         * @see android.widget.Filter#publishResults(java.lang.CharSequence, android.widget.Filter.FilterResults)
         */
        @SuppressWarnings("unchecked")
		@Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
        	if(results != null)
        	{
        		filtered = true;
	        	FilteredList = (ArrayList<Book>) results.values;
	            notifyDataSetChanged();
        	}
        	else
        	{
        		filtered = false;
        		notifyDataSetChanged();
        	}
        }
	};
	
	/* (non-Javadoc)
	 * @see android.widget.Filterable#getFilter()
	 */
	public Filter getFilter() {
		return filter;
	}
	
	
	
}
