package com.federico.cicerone.contentproviderexample.Activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.federico.cicerone.contentproviderexample.Contract.BookContract;
import com.federico.cicerone.contentproviderexample.R;
import com.federico.cicerone.contentproviderexample.model.Book;
import com.federico.cicerone.contentproviderexample.model.Store;
import com.federico.cicerone.contentproviderexample.sqlite.ContentProvider;
import com.federico.cicerone.contentproviderexample.Contract.StoreContract;


public class Main extends ActionBarActivity {

    private TextView texttest;
    private ContentResolver cr;
    private String logtag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        texttest = (TextView) findViewById(R.id.texttest);
        cr = getContentResolver();

        Store storeA = new Store( "store A", 12, 40 );
        Store storeB = new Store( "store B", 13, 41 );

        // delete all previously saved stores and books
        cr.delete( ContentProvider.CONTENT_URI_STORE, StoreContract.StoreEntry.COLUMN_NAME_ID + " >= 0", null);
        cr.delete( ContentProvider.CONTENT_URI_BOOK, BookContract.BookEntry.COLUMN_NAME_ID + " >= 0", null);

        cr.insert( ContentProvider.CONTENT_URI_STORE, storeA.getContentValue() );
        cr.insert( ContentProvider.CONTENT_URI_STORE, storeB.getContentValue() );

        Cursor store_crs =  ContentProvider.getAllStores( cr );
        while ( store_crs.moveToNext()) {
            Book book = new Book("The Prince", "Niccoló Machiavelli", store_crs.getInt( store_crs.getColumnIndex( StoreContract.StoreEntry.COLUMN_NAME_ID ) ) );
            cr.insert( ContentProvider.CONTENT_URI_BOOK, book.getContentValue() );
        }
        texttest.setText(getInfo());
        Cursor c =  ContentProvider.getAllBooks(cr);
    }

    private String getInfo() {
        String info = "";
        Cursor store_crs =  ContentProvider.getAllStores( cr );
        while ( store_crs.moveToNext()) {
            info += " Store: " + store_crs.getString( store_crs.getColumnIndex( StoreContract.StoreEntry.COLUMN_NAME_NAME ) );
            Cursor book_crs =  ContentProvider.getAllBooksInStore(cr, store_crs.getInt(store_crs.getColumnIndex(StoreContract.StoreEntry.COLUMN_NAME_ID)));
            while ( book_crs.moveToNext() ){
                info += " Title: " + book_crs.getString( book_crs.getColumnIndex(BookContract.BookEntry.COLUMN_NAME_TITLE) );
                info += " Author: " + book_crs.getString( book_crs.getColumnIndex(BookContract.BookEntry.COLUMN_NAME_AUTHOR) );
            }
        }
        return info;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
