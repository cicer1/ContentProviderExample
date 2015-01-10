package com.federico.cicerone.contentproviderexample.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.federico.cicerone.contentproviderexample.contract.BookContract;
import com.federico.cicerone.contentproviderexample.contract.StoreContract;

/**
 * Created by cicerone on 09/01/15.
 */
public class DB_Helper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static String DATABASE_NAME;

    private static final String BOOK_TABLE = BookContract.BookEntry.TABLE_NAME;
    private static final String STORE_TABLE = StoreContract.StoreEntry.TABLE_NAME;

    // Store Table Create statement
    private static final String DATABASE_CREATE_BOOK =        " create table "                          +
            BookContract.BookEntry.TABLE_NAME               + " ( "                                     +
            BookContract.BookEntry.COLUMN_NAME_ID           + " INTEGER PRIMARY KEY AUTOINCREMENT, "    +
            BookContract.BookEntry.COLUMN_NAME_AUTHOR       + " TEXT NOT NULL , "                       +
            BookContract.BookEntry.COLUMN_NAME_STORE_ID     + " INTEGER NOT NULL, "                     +
            BookContract.BookEntry.COLUMN_NAME_TITLE        + " TEXT NOT NULL  "                        +
            ");";

    // Book Table Create statement
    private static final String DATABASE_CREATE_STORE =   " create table "                              +
            StoreContract.StoreEntry.TABLE_NAME         + " ("                                          +
            StoreContract.StoreEntry.COLUMN_NAME_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT, "        +
            StoreContract.StoreEntry.COLUMN_NAME_LAT    + " INTEGER , "                                 +
            StoreContract.StoreEntry.COLUMN_NAME_LON    + " INTEGER , "                                 +
            StoreContract.StoreEntry.COLUMN_NAME_NAME   + " TEXT NOT NULL  "                            +
            ");";


    private final Context mContext;


    public DB_Helper(Context context, String dbName) {
        super(context, dbName, null, DATABASE_VERSION);
        this.DATABASE_NAME = dbName;
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(DATABASE_CREATE_BOOK);
        db.execSQL(DATABASE_CREATE_STORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        deleteAll(db);
        // create new tables
        onCreate(db);
    }

    private void deleteAll(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + BookContract.BookEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + StoreContract.StoreEntry.TABLE_NAME);

    }

    public static String getBookTable() {
        return BOOK_TABLE;
    }
    public static String getStoreTable() {
        return STORE_TABLE;
    }
}