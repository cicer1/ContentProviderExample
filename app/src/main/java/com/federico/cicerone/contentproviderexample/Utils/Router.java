package com.federico.cicerone.contentproviderexample.Utils;

import android.content.Context;
import android.content.Intent;
import com.federico.cicerone.contentproviderexample.Activities.Books;
import com.federico.cicerone.contentproviderexample.Activities.CreateBook;
import com.federico.cicerone.contentproviderexample.Activities.CreateStore;
import com.federico.cicerone.contentproviderexample.Activities.Details;

/**
 * Created by cicerone on 15/01/15.
 */
public class Router {

    private static Router instance = null;

    protected Router() {
        // Exists only to defeat instantiation.
    }
    public static Router getInstance() {
        if(instance == null) {
            instance = new Router();
        }
        return instance;
    }
    public void Navigate( Context c, String entry, String token){

        Intent intent = null;
        switch (entry) {
            case ENTRIES.TAP_ON_STOREITEM:
                intent = new Intent(c, Books.class);
                intent.putExtra( Constants.TOKEN, token );
                c.startActivity(intent);
                break;
            case ENTRIES.TAP_ON_BOOKITEM:
                intent = new Intent(c, Details.class);
                intent.putExtra( Constants.TOKEN, token );
                c.startActivity(intent);
                break;
            case ENTRIES.TAP_ON_MENU_CREATESTORE:
                intent = new Intent(c, CreateStore.class);
                intent.putExtra( Constants.TOKEN, token );
                c.startActivity(intent);
                break;
            case ENTRIES.TAP_ON_MENU_CREATEBOOK:
                intent = new Intent(c, CreateBook.class);
                intent.putExtra( Constants.TOKEN, token );
                c.startActivity(intent);
                break;
            default:
                throw new IllegalArgumentException("Invalid Entry");
        }
    }

    public static class ENTRIES{
        public static final String TAP_ON_STOREITEM = "tapOnStoreItem";
        public static final String TAP_ON_BOOKITEM = "tapOnBookItem";
        public static final String TAP_ON_MENU_CREATESTORE = "tapOnMenuCreateStore";
        public static final String TAP_ON_MENU_CREATEBOOK = "tapOnMenuCreateBook";
    }
}