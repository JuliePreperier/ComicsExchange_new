package com.example.comicsexchange_new;

import java.util.ArrayList;

/**
 * Created by Julie on 17.05.2017.
 */

public class SyncToCloud {

    private boolean userUpdated = false;
    private boolean authorsUpdated = false;
    private boolean serieUpdated = false;
    private boolean comicUpdated = false;
    private boolean ownerBookUpdated = false;

    public static ArrayList<Boolean> loadingDone = new ArrayList<>();


    public SyncToCloud(){
        loadingDone.add(userUpdated);
        loadingDone.add(authorsUpdated);
        loadingDone.add(serieUpdated);
        loadingDone.add(comicUpdated);
        loadingDone.add(ownerBookUpdated);
    }


    /*
    Setter to set the boolean to true
     */

    public static void setUserUpdatedTrue(){
        loadingDone.set(0,true);
    }

    public static void setAuthorUpdatedTrue(){
        loadingDone.set(1,true);
    }

    public static void setSerieUpdatedTrue(){
        loadingDone.set(2,true);
    }

    public static void setComicUpdatedTrue(){
        loadingDone.set(3,true);
    }

    public static void setOwnerBookUpdatedTrue(){
        loadingDone.set(4,true);
    }


}
