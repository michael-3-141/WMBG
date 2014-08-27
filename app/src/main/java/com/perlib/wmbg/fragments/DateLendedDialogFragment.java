package com.perlib.wmbg.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.perlib.wmbg.book.Book;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DateLendedDialogFragment extends DialogFragment {

    Book book;
    DatePickerDialog.OnDateSetListener dateSetListener;

    public DateLendedDialogFragment(Book book, DatePickerDialog.OnDateSetListener dateSetListener) {
        this.book = book;
        this.dateSetListener = dateSetListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(book.getDateLended() * 1000);
        return new DatePickerDialog(getActivity(), dateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }
}
