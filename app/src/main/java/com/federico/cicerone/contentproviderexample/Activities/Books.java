package com.federico.cicerone.contentproviderexample.Activities;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.federico.cicerone.contentproviderexample.R;
import com.federico.cicerone.contentproviderexample.adapter.BookAdapter;
import com.federico.cicerone.contentproviderexample.adapter.StoreAdapter;
import com.federico.cicerone.contentproviderexample.sqlite.ContentProvider;

public class Books extends ActionBarActivity {

    private ListView bookList;
    private BookAdapter bookAdapter;
    private ContentResolver cr;
    private String storeId;
    private String logtag = "BooksActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeId = getIntent().getExtras().getString( Stores.EXTRA.STORE_ID );
        setContentView(R.layout.activity_books);
        cr = getContentResolver();
        bookList = (ListView) findViewById( R.id.book_list );
        Cursor cursor = ContentProvider.getAllBooksInStore( cr, storeId );
        bookAdapter = new BookAdapter( this, cursor, 0);
        bookList.setAdapter( bookAdapter );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_books, menu);
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
