package com.federico.cicerone.contentproviderexample.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.federico.cicerone.contentproviderexample.R;
import com.federico.cicerone.contentproviderexample.Contract.StoreContract;

/**
 * Created by cicerone on 10/01/15.
 */
public class StoreAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public StoreAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        c.moveToFirst();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.item_title_detail, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // here we are setting our data
        // that means, take the data from the cursor and put it in views

        TextView textViewTitle = (TextView) view.findViewById(R.id.title);
        String storeName = cursor.getString(cursor.getColumnIndex(StoreContract.StoreEntry.COLUMN_NAME_NAME));
        textViewTitle.setText(storeName);
        TextView textViewDetail = (TextView) view.findViewById(R.id.detail);
        String detail = cursor.getString(cursor.getColumnIndex(StoreContract.StoreEntry.COLUMN_NAME_LAT)) + " " + cursor.getString(cursor.getColumnIndex(StoreContract.StoreEntry.COLUMN_NAME_LON));
        textViewDetail.setText(detail);
    }
}
