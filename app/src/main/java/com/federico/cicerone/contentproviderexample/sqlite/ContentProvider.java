package com.federico.cicerone.contentproviderexample.sqlite;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.federico.cicerone.contentproviderexample.contract.BookContract;
import com.federico.cicerone.contentproviderexample.contract.StoreContract;

/**
 * Created by cicerone on 09/01/15.
 */
public class ContentProvider extends android.content.ContentProvider {

    // authority
    public static final Uri CONTENT_URI_BOOK = Uri.parse("content://com.federico.cicerone.contentprovider/book");
    public static final Uri CONTENT_URI_STORE = Uri.parse("content://com.federico.cicerone.contentprovider/store");
    private static DB_Helper myOpenHelper;


    @Override
    public boolean onCreate() {
        // Construct the underlying database.
        // Defer opening the database until you need to perform
        // a query or transaction.

        myOpenHelper = new DB_Helper(getContext() , "mydatabase.db");
        return true;
    }

    private static final int ALLROWS_BOOK = 1;
    private static final int ALLROWS_STORE = 2;

    private static final UriMatcher uriMatcher;

    //Populate the UriMatcher object, where a URI ending in 'todoitems' will
    //correspond to a request for all items, and 'todoitems/[rowID]'
    //represents a single row.
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.federico.cicerone.contentprovider", "book" , ALLROWS_BOOK);
        uriMatcher.addURI("com.federico.cicerone.contentprovider", "store", ALLROWS_STORE);
    }

    @Override
    public String getType(Uri uri) {
        // Return a string that identifies the MIME type
        // for a Content Provider URI
        switch (uriMatcher.match(uri)) {
            case ALLROWS_BOOK: return "vnd.android.cursor.dir/vnd.com.federico.cicerone.book";
            case ALLROWS_STORE: return "vnd.android.cursor.dir/vnd.com.federico.cicerone.store";
            default: throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {


        String groupBy = null;
        String having = null;
        SQLiteQueryBuilder queryBuilder;
        Cursor cursor = null;
        // Open a read / write database to support the transaction.
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case ALLROWS_BOOK:
                queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(DB_Helper.getBookTable());
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
                break;
            case ALLROWS_STORE:
                queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(DB_Helper.getStoreTable());
                cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
                break;
            default: throw new SQLException("Failed to query " + uri);
        }
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int ndel = 0;
        // Open a read / write database to support the transaction.
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case ALLROWS_BOOK:
                ndel = db.delete(DB_Helper.getBookTable(), selection, selectionArgs);
                //---if added successfully---
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            case ALLROWS_STORE:
                ndel = db.delete(DB_Helper.getStoreTable(), selection, selectionArgs);
                //---if added successfully---
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default: throw new SQLException("Failed to delete " + uri);
        }
        return ndel;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) throws RuntimeException{
        // Open a read / write database to support the transaction.
        Uri _uri = null;
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        long _ID1;
        switch (uriMatcher.match(uri)){
            case ALLROWS_BOOK:
                try {
                    _ID1 = db.insertOrThrow(DB_Helper.getBookTable(), "", values);
                    //---if added successfully---
                    if (_ID1 > 0) {
                        _uri = ContentUris.withAppendedId(CONTENT_URI_BOOK, _ID1);
                        getContext().getContentResolver().notifyChange(_uri, null);
                    }
                } catch (SQLException e) {

                }
                break;
            case ALLROWS_STORE:
                try {
                    _ID1 = db.insertOrThrow(DB_Helper.getStoreTable(), "", values);
                    //---if added successfully---
                    if (_ID1 > 0) {
                        _uri = ContentUris.withAppendedId(CONTENT_URI_STORE, _ID1);
                        getContext().getContentResolver().notifyChange(_uri, null);
                    }
                } catch (SQLException e) {

                }
                break;
            default: throw new SQLException("Failed to insert row into " + uri);
        }
        return _uri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int nup = 0;
        // Open a read / write database to support the transaction.
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case ALLROWS_BOOK:
                nup = db.update(DB_Helper.getBookTable(),
                        values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                break;

            case ALLROWS_STORE:
                nup = db.update(DB_Helper.getStoreTable(),
                        values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                break;


            default: throw new SQLException("Failed to update" + uri);
        }

        return nup;
    }

    public static Cursor getAllStores( ContentResolver cr ) {
        String[] result_columns = StoreContract.StoreEntry.COLUMNS;
        String where = null;
        String[] where_args = null;
        String order = null;
        return cr.query( CONTENT_URI_STORE,result_columns,where,where_args,order);
    }

    public static Cursor getAllBooksInStore(ContentResolver cr, int store_id) {
        String[] result_columns = BookContract.BookEntry.COLUMNS;
        String where = BookContract.BookEntry.COLUMN_NAME_STORE_ID+"=" + store_id;
        String[] where_args = null;
        String order = null;
        return cr.query( CONTENT_URI_BOOK,result_columns,where,where_args,order);
    }

    public static Cursor getAllBooks(ContentResolver cr) {
        String[] result_columns = BookContract.BookEntry.COLUMNS;
        String where = null;
        String[] where_args = null;
        String order = null;
        return cr.query( CONTENT_URI_BOOK,result_columns,where,where_args,order);
    }
}
