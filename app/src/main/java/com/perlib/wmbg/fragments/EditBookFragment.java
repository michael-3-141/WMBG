package com.perlib.wmbg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.perlib.wmbg.R;
import com.perlib.wmbg.book.Book;

import java.util.GregorianCalendar;

public class EditBookFragment extends Fragment{

    private DatePicker dpDateLended;
    private BookFragment fmtBook;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_book, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dpDateLended = (DatePicker) view.findViewById(R.id.dpDateLended);
        fmtBook = new BookFragment();
        getChildFragmentManager().beginTransaction().add(R.id.bookFragment, fmtBook).commit();

        fmtBook.getView().setVisibility(View.GONE);
        dpDateLended.setVisibility(View.GONE);
    }

    public void setViewedBook(Book book)
    {
        if(book == null)
        {
            fmtBook.getView().setVisibility(View.GONE);
            dpDateLended.setVisibility(View.GONE);
            return;
        }
        fmtBook.setViewedBook(book);
        GregorianCalendar editedDate = new GregorianCalendar();
        editedDate.setTimeInMillis(book.getDateLended()*1000);
        dpDateLended.updateDate(editedDate.get(GregorianCalendar.YEAR), editedDate.get(GregorianCalendar.MONTH), editedDate.get(GregorianCalendar.DAY_OF_MONTH));
    }

    public Book getViewedBook()
    {
        Book book = fmtBook.getViewedBook();
        GregorianCalendar dateLendedGc = new GregorianCalendar(dpDateLended.getYear(), dpDateLended.getMonth(), dpDateLended.getDayOfMonth());
        book.setDateLended(dateLendedGc.getTimeInMillis()/1000);
        return book;
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
