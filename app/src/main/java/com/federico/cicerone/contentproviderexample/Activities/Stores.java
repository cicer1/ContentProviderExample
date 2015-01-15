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

import com.federico.cicerone.contentproviderexample.Utils.Router;
import com.federico.cicerone.contentproviderexample.adapter.StoreAdapter;
import com.federico.cicerone.contentproviderexample.Contract.BookContract;
import com.federico.cicerone.contentproviderexample.R;
import com.federico.cicerone.contentproviderexample.model.Book;
import com.federico.cicerone.contentproviderexample.model.Store;
import com.federico.cicerone.contentproviderexample.sqlite.ContentProvider;
import com.federico.cicerone.contentproviderexample.Contract.StoreContract;
import com.google.gson.Gson;


public class Stores extends ActionBarActivity {

    private ListView storeList;
    private StoreAdapter storeAdapter;
    private ContentResolver cr;
    private String logtag = "StoresActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        cr = getContentResolver();
        //insertData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        storeList = (ListView) findViewById( R.id.store_list );
        Cursor cursor = ContentProvider.getAllStores( cr );
        storeAdapter = new StoreAdapter( this, cursor, 0);
        storeList.setAdapter( storeAdapter );
        StoreClickListener storeClickListener = new StoreClickListener(this,cursor);
        storeList.setOnItemClickListener(storeClickListener);
    }

    private void insertData() {
        Store storeA = new Store( "store A", 12, 40 );
        Store storeB = new Store( "store B", 13, 41 );

        // delete all previously saved stores and books
        cr.delete( ContentProvider.CONTENT_URI_STORE, StoreContract.StoreEntry.COLUMN_NAME_ID + " >= 0", null);
        cr.delete( ContentProvider.CONTENT_URI_BOOK, BookContract.BookEntry.COLUMN_NAME_ID + " >= 0", null);

        cr.insert( ContentProvider.CONTENT_URI_STORE, storeA.asContentValue() );
        cr.insert( ContentProvider.CONTENT_URI_STORE, storeB.asContentValue() );

        Cursor store_crs =  ContentProvider.getAllStores( cr );
        while ( store_crs.moveToNext()) {
            Book book = new Book("The Prince", "Niccoló Machiavelli", store_crs.getInt( store_crs.getColumnIndex( StoreContract.StoreEntry.COLUMN_NAME_ID ) ) );
            cr.insert( ContentProvider.CONTENT_URI_BOOK, book.asContentValue() );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_create_store) {
            Router.getInstance().Navigate(this,Router.ENTRIES.TAP_ON_MENU_CREATESTORE,null);
        }

        return super.onOptionsItemSelected(item);
    }

    private class StoreClickListener implements AdapterView.OnItemClickListener{
        Context context;
        Cursor store_crs;

        private StoreClickListener(Context ctx, Cursor c) {
            context = ctx;
            store_crs = c;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Store s = new Store( store_crs );
            Router.getInstance().Navigate( context , Router.ENTRIES.TAP_ON_STOREITEM, s.toJSON() );
        }
    }

}
