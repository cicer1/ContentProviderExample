package com.federico.cicerone.contentproviderexample.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.federico.cicerone.contentproviderexample.R;
import com.federico.cicerone.contentproviderexample.Utils.Constants;
import com.federico.cicerone.contentproviderexample.model.Book;
import com.federico.cicerone.contentproviderexample.model.Store;
import com.federico.cicerone.contentproviderexample.sqlite.ContentProvider;

public class CreateBook extends ActionBarActivity {

    private EditText book_title_et;
    private EditText book_author_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        findViewElements();
    }

    public void saveBook(View view){
        String storeJSON = getIntent().getExtras().getString( Constants.TOKEN );
        Store store = Store.fromJSON( storeJSON );
        String title = book_title_et.getText().toString();
        String author = book_author_et.getText().toString();
        Book book = new Book( title, author, store.get_id());
        getContentResolver().insert(ContentProvider.CONTENT_URI_BOOK,book.asContentValue());
        finish();
    }

    private void findViewElements() {
        book_title_et = (EditText) findViewById(R.id.book_title_et);
        book_author_et = (EditText) findViewById(R.id.book_author_et);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_book, menu);
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
