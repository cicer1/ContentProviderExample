package com.federico.cicerone.contentproviderexample.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.federico.cicerone.contentproviderexample.Contract.StoreContract;
import com.google.gson.Gson;

/**
 * Created by cicerone on 09/01/15.
 */
public class Store{
    private int _id;
    private int lat;
    private int lon;
    private String name;

    public Store( String name, int lat, int lon) {
        setName(name);
        setLat(lat);
        setLon(lon);
    }

    public Store( Cursor c ){
        set_id( c.getInt( c.getColumnIndex(StoreContract.StoreEntry.COLUMN_NAME_ID)) );
        setName( c.getString( c.getColumnIndex(StoreContract.StoreEntry.COLUMN_NAME_NAME)) );
        setLat( c.getInt( c.getColumnIndex(StoreContract.StoreEntry.COLUMN_NAME_LAT)) );
        setLon( c.getInt( c.getColumnIndex(StoreContract.StoreEntry.COLUMN_NAME_LON)) );
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentValues asContentValue() {
        ContentValues store = new ContentValues();
        store.put( StoreContract.StoreEntry.COLUMN_NAME_LAT, getLat() );
        store.put( StoreContract.StoreEntry.COLUMN_NAME_LON, getLon() );
        store.put( StoreContract.StoreEntry.COLUMN_NAME_NAME, getName() );
        return store;
    }
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson( this );
    }

    public static Store fromJSON( String json ) {
        Gson gson = new Gson();
        return gson.fromJson(json, Store.class);
    }
}
