package com.federico.cicerone.contentproviderexample.Contract;

import android.provider.BaseColumns;

/**
 * Created by cicerone on 09/01/15.
 */
public class BookContract {

    // This empty constructor prevent someone from accidentally instantiating the contract class
    public BookContract() {}

    /* Inner class that defines the table contents */
    public static abstract class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "book";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_STORE_ID = "store_id";
        public static final String COLUMN_NAME_TITLE = "title";


        public static final String[] COLUMNS =
                {
                COLUMN_NAME_ID,
                COLUMN_NAME_AUTHOR,
                COLUMN_NAME_STORE_ID,
                COLUMN_NAME_TITLE
                };
    }
}