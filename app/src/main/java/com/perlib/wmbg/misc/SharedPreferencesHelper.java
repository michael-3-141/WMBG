package com.perlib.wmbg.misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perlib.wmbg.book.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class used to save books in SharedPreferences by saving them as JSON strings using Gson.
 */
public class SharedPreferencesHelper {

    private SharedPreferences prefs;
    private Gson gson = new Gson();

    public SharedPreferencesHelper(SharedPreferences preferences)
    {
        prefs = preferences;
    }

    public  SharedPreferencesHelper(Context cx)
    {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(cx);
    }

    public Book getBook(String key)
    {
        return gson.fromJson(prefs.getString("key", ""), Book.class);
    }

    public void setBook(String key, Book book)
    {
        prefs.edit().putString(key, gson.toJson(book)).commit();
    }

    public ArrayList<Book> getBookList(String key)
    {
        return gson.fromJson(prefs.getString(key, ""), new TypeToken<ArrayList<Book>>(){}.getType());
    }

    public void setBookList(String key, List<Book> books)
    {
        prefs.edit().putString(key, gson.toJson(books, new TypeToken<ArrayList<Book>>(){}.getType())).commit();
    }

    public ArrayList<Book> getBookList()
    {
        return getBookList("items");
    }

    public void setBookList(List<Book> books)
    {
        setBookList("items", books);
    }

}
