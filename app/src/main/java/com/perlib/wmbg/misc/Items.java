package com.perlib.wmbg.misc;

import android.content.Context;
import android.preference.PreferenceManager;

import com.perlib.wmbg.book.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Items implements List<Book> {
    
    private SharedPreferencesHelper helper;
    
    public Items(Context cx)
    {
        this.helper = new SharedPreferencesHelper(PreferenceManager.getDefaultSharedPreferences(cx));
    }
    
    @Override
    public void add(int location, Book object) {
        List<Book> items = getList();
        items.add(location, object);
        helper.setBookList(items);
    }

    @Override
    public boolean add(Book object) {
        List<Book> items = getList();
        items.add(object);
        helper.setBookList(items);
        return true;
    }

    @Override
    public boolean addAll(int location, Collection<? extends Book> collection) {
        List<Book> items = getList();
        boolean toReturn = items.addAll(location, collection);
        helper.setBookList(items);
        return toReturn;
    }

    @Override
    public boolean addAll(Collection<? extends Book> collection) {
        List<Book> items = getList();
        boolean toReturn = items.addAll(collection);
        helper.setBookList(items);
        return toReturn;
    }

    @Override
    public void clear() {
        List<Book> items = getList();
        items.clear();
        helper.setBookList(items);
    }

    @Override
    public boolean contains(Object object) {
        return getList().contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return getList().containsAll(collection);
    }

    @Override
    public Book get(int location) {
        return getList().get(location);
    }

    @Override
    public int indexOf(Object object) {
        return getList().indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return getList().isEmpty();
    }

    @Override
    public Iterator<Book> iterator() {
        return getList().iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return getList().lastIndexOf(object);
    }

    @Override
    public ListIterator<Book> listIterator() {
        return getList().listIterator();
    }

    @Override
    public ListIterator<Book> listIterator(int location) {
        return getList().listIterator(location);
    }

    @Override
    public Book remove(int location) {
        List<Book> items = getList();
        Book toReturn = items.remove(location);
        helper.setBookList(items);
        return toReturn;
    }

    @Override
    public boolean remove(Object object) {
        List<Book> items = getList();
        boolean toReturn = items.remove(object);
        helper.setBookList(items);
        return toReturn;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        List<Book> items = getList();
        boolean toReturn = items.removeAll(collection);
        helper.setBookList(items);
        return toReturn;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        List<Book> items = getList();
        boolean toReturn = items.retainAll(collection);
        helper.setBookList(items);
        return toReturn;
    }

    @Override
    public Book set(int location, Book object) {
        List<Book> items = getList();
        Book toReturn = items.set(location, object);
        helper.setBookList(items);
        return toReturn;
    }

    @Override
    public int size() {
        return getList().size();
    }

    @Override
    public List<Book> subList(int start, int end) {
        return getList().subList(start, end);
    }

    @Override
    public Object[] toArray() {
        return getList().toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return getList().toArray(array);
    }
    
    private List<Book> getList(){
        List<Book> items = helper.getBookList();
        return items == null ? new ArrayList<Book>() : items;
    }
}
