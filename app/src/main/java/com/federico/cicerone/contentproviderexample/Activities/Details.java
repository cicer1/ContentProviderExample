package com.federico.cicerone.contentproviderexample.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.federico.cicerone.contentproviderexample.R;
import com.federico.cicerone.contentproviderexample.Utils.Constants;
import com.federico.cicerone.contentproviderexample.model.Book;

public class Details extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String bookJSON = getIntent().getExtras().getString(Constants.TOKEN);
        Book book = Book.fromJSON( bookJSON );
        TextView title = (TextView) findViewById( R.id.title );
        TextView author = (TextView) findViewById( R.id.author );
        TextView description = (TextView) findViewById( R.id.description );
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        description.setText(book.getTitle() + " is a great book by " + book.getAuthor());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_book) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
