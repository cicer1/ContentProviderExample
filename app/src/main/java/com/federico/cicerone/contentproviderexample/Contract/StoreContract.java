package com.federico.cicerone.contentproviderexample.Contract;

import android.provider.BaseColumns;

/**
 * Created by cicerone on 09/01/15.
 */
public class StoreContract {

    // This empty constructor prevent someone from accidentally instantiating the contract class
    public StoreContract() {}

    /* Inner class that defines the table contents */
    public static abstract class StoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "store";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LON = "lon";
        public static final String COLUMN_NAME_NAME= "name";


        public static final String[] COLUMNS =
                {
                        COLUMN_NAME_ID,
                        COLUMN_NAME_LAT,
                        COLUMN_NAME_LON,
                        COLUMN_NAME_NAME
                };
    }
}
