package com.federico.cicerone.contentproviderexample.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.federico.cicerone.contentproviderexample.R;
import com.federico.cicerone.contentproviderexample.model.Store;
import com.federico.cicerone.contentproviderexample.sqlite.ContentProvider;

public class CreateStore extends ActionBarActivity {

    private EditText store_name_et;
    private EditText store_lat_et;
    private EditText store_lon_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        findViewElements();
    }

    private void findViewElements() {
        store_name_et = (EditText) findViewById(R.id.store_name_et);
        store_lat_et = (EditText) findViewById(R.id.store_lat_et);
        store_lon_et = (EditText) findViewById(R.id.store_lon_et);
    }

    public void saveStore(View view){
        String name = store_name_et.getText().toString();
        String lat = store_lat_et.getText().toString();
        String lon = store_lon_et.getText().toString();
        Store store = new Store(name, Integer.valueOf(lat), Integer.valueOf(lon));
        getContentResolver().insert(ContentProvider.CONTENT_URI_STORE,store.asContentValue());
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_store, menu);
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
