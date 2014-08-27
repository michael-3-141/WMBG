package com.perlib.wmbg.misc;

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
import com.perlib.wmbg.book.Book;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * A custom adapter to handle a list of books.
 */
public class BookAdapter extends BaseAdapter implements Filterable {

	private Items items;
	private int[] colors = new int[] { 0x30ffffff, 0x3099FF66 };
    private Context cx;
	boolean filtered;
	private ArrayList<Book> FilteredList = new ArrayList<Book>();
	private BookFilter filter = new BookFilter();

	
	public BookAdapter(Context cx) {
        this.cx = cx;
		items = new Items(cx);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) cx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.book_list_item, parent, false);

        if(!v.isSelected()) {
            int colorPos = position % colors.length;
            v.setBackgroundColor(colors[colorPos]);
        }
        else{
            v.setBackgroundColor(0xFF5C993D);
        }

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
				Picasso.with(cx).load(file).fit().centerCrop().into(iv);
                return v;
			}
		}

        if(item.getThumbnailUrl() == "")return v;
		Picasso.with(cx).load(item.getThumbnailUrl()).fit().centerCrop().into(iv);

		return v;
	}


	@Override
	public int getCount() {
		return !filtered ? items.size() : FilteredList.size();
	}


	@Override
	public Book getItem(int position) {
		return !filtered ? items.get(position) : FilteredList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	
	public class BookFilter extends Filter{

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

            for(int i = 0; i<items.size(); i++){
                item = items.get(i);
                if(item.getName().toLowerCase().contains(filterString)){
                    FilteredList.add(items.get(i));
                }
            }
            Result.values = FilteredList;
            Result.count = FilteredList.size();

            return Result;
        }

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

	public Filter getFilter() {
		return filter;
	}
}
