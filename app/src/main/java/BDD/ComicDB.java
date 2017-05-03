package BDD;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.comicsexchange_new.Comic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julie on 03.05.2017.
 */

public class ComicDB {

    private DbHelper db;

    public ComicDB(DbHelper db){
        this.db=db;
    }

    public List<Comic> getComics(){
        List<Comic> comics = new ArrayList<Comic>();

        String selectQuery = "SELECT * FROM " +Contract.Comic.TABLE_NAME;

        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(selectQuery,null);

        //looping through all rows and adding to list

        if(c.moveToFirst()){
            do{
                int id = c.getInt(c.getColumnIndex(Contract.Comic._ID));
                String titre = c.getString(c.getColumnIndex(Contract.Comic.COLUMN_NAME_TITRE));
                String text = c.getString(c.getColumnIndex(Contract.Comic.COLUMN_NAME_SYNOPSIS));
                String picture = c.getString(c.getColumnIndex(Contract.Comic.COLUMN_NAME_PHOTO));
                Comic comic =  new Comic(id,picture,titre,text);

                // adding to comic list
                comics.add(comic);
            }while(c.moveToNext());
        }

        return comics;
    }

    public Comic getCanton(int idComic){
        String selectQuery = "SELECT * FROM "+Contract.Comic.TABLE_NAME +" WHERE "+Contract.Comic._ID+" = "+idComic;

        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(selectQuery,null);

        if(c!=null)
            c.moveToFirst();

        Comic comic = new Comic();
        comic.setId(c.getInt(c.getColumnIndex(Contract.Comic._ID)));
        comic.setText(c.getString(c.getColumnIndex(Contract.Comic.COLUMN_NAME_SYNOPSIS)));
        comic.setPicture(c.getString(c.getColumnIndex(Contract.Comic.COLUMN_NAME_PHOTO)));
        comic.setTitre(c.getString(c.getColumnIndex(Contract.Comic.COLUMN_NAME_TITRE)));

        return comic;
    }
}
