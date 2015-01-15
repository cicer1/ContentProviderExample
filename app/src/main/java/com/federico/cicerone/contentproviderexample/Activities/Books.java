package com.federico.cicerone.contentproviderexample.Activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.federico.cicerone.contentproviderexample.R;
import com.federico.cicerone.contentproviderexample.Utils.Constants;
import com.federico.cicerone.contentproviderexample.Utils.Router;
import com.federico.cicerone.contentproviderexample.adapter.BookAdapter;
import com.federico.cicerone.contentproviderexample.adapter.StoreAdapter;
import com.federico.cicerone.contentproviderexample.model.Book;
import com.federico.cicerone.contentproviderexample.model.Store;
import com.federico.cicerone.contentproviderexample.sqlite.ContentProvider;

public class Books extends ActionBarActivity {

    private ListView bookList;
    private BookAdapter bookAdapter;
    private ContentResolver cr;
    private Store store;
    private String logtag = "BooksActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String storeJSON = getIntent().getExtras().getString( Constants.TOKEN );
        store = Store.fromJSON( storeJSON );
        setContentView(R.layout.activity_books);
        cr = getContentResolver();
        bookList = (ListView) findViewById( R.id.book_list );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = ContentProvider.getAllBooksInStore( cr, store.get_id() );
        bookAdapter = new BookAdapter( this, cursor, 0);
        bookList.setAdapter( bookAdapter );
        BookClickListener bookClickListener = new BookClickListener(this,cursor);
        bookList.setOnItemClickListener(bookClickListener);
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

        if (id == R.id.action_create_book) {
            Router.getInstance().Navigate( this, Router.ENTRIES.TAP_ON_MENU_CREATEBOOK, store.toJSON() );
        }

        return super.onOptionsItemSelected(item);
    }
    private class BookClickListener implements AdapterView.OnItemClickListener{
        Context context;
        Cursor book_crs;

        private BookClickListener(Context ctx, Cursor c) {
            context = ctx;
            book_crs = c;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Book b = new Book( book_crs );
            Router.getInstance().Navigate( context , Router.ENTRIES.TAP_ON_BOOKITEM, b.toJSON() );
        }
    }

}
