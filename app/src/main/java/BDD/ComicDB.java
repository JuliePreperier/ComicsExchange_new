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
    int compteur=0;

    public ComicDB(DbHelper db){
        this.db=db;
    }


    // method to get all the comics of the database
    public ArrayList<Comic> getComics(){
        ArrayList<Comic> comics = new ArrayList<Comic>();

        String selectQuery = "SELECT * FROM " +Contract.Comic.TABLE_NAME; // SQL request

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


    // method to get only one comic with its id
    public Comic getComic(int idComic){
        String selectQuery = "SELECT * FROM "+Contract.Comic.TABLE_NAME +" WHERE "+Contract.Comic._ID+" = "+idComic;// SQL request

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


    // method to get all the comic of a user (with his id)
    public List<Comic> getComicsPerUser(int currentUserId){
        List<Comic> comics = new ArrayList<Comic>();

        String selectQuery = "SELECT * FROM " +Contract.Comic.TABLE_NAME+", "+Contract.Ownerbooks.TABLE_NAME+" WHERE Comic."+Contract.Comic._ID+" = "+Contract.Ownerbooks.COLUMN_NAME_IDBOOK
                +" AND "+ Contract.Ownerbooks.COLUMN_NAME_IDUSER+" = '"+currentUserId+"'";// SQL request

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


    // Method to get the 5 lasts comics of the table Comic (latest upload) for New's fragment
    public List<Comic> getLastComic(){
        List<Comic> comics = new ArrayList<Comic>();

        String selectQuery = "SELECT * FROM " +Contract.Comic.TABLE_NAME;// SQL request

        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(selectQuery,null);

        //looping through the 5 rows from the bottom of the table and adding the object to list

        if(c.moveToLast()){
            do{
                    int id = c.getInt(c.getColumnIndex(Contract.Comic._ID));
                    String titre = c.getString(c.getColumnIndex(Contract.Comic.COLUMN_NAME_TITRE));
                    String text = c.getString(c.getColumnIndex(Contract.Comic.COLUMN_NAME_SYNOPSIS));
                    String picture = c.getString(c.getColumnIndex(Contract.Comic.COLUMN_NAME_PHOTO));
                    Comic comic = new Comic(id, picture, titre, text);

                    // adding to comic list
                    comics.add(comic);
                    c.moveToPrevious();
                    compteur++;
            }while (c.moveToPrevious() && compteur<5);
        }

        return comics;
    }


}
