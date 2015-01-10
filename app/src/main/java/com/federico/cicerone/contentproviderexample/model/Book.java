package com.federico.cicerone.contentproviderexample.model;

import android.content.ContentValues;

import com.federico.cicerone.contentproviderexample.Contract.BookContract;
import com.federico.cicerone.contentproviderexample.Contract.StoreContract;

/**
 * Created by cicerone on 09/01/15.
 */
public class Book {

    private int _id;
    private String title;
    private int store_id;
    private String author;


    public Book( String title, String author, int store_id ) {
        setTitle(title);
        setAuthor(author);
        setStore_id(store_id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int get_id() {
        return _id;
    }

    public ContentValues getContentValue() {
        ContentValues book = new ContentValues();
        book.put( BookContract.BookEntry.COLUMN_NAME_AUTHOR, getAuthor() );
        book.put( BookContract.BookEntry.COLUMN_NAME_TITLE, getTitle() );
        book.put( BookContract.BookEntry.COLUMN_NAME_STORE_ID, getStore_id() );
        return book;
    }
}
