package BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.comicsexchange_new.Comic;

import java.util.ArrayList;

import static BDD.Contract.SQL_CREATE_AUTHORS;
import static BDD.Contract.SQL_CREATE_COMICS;
import static BDD.Contract.SQL_CREATE_OWNERBOOKS;
import static BDD.Contract.SQL_CREATE_SERIES;
import static BDD.Contract.SQL_CREATE_USERS;
import static BDD.Contract.SQL_DELETE_AUTHORS;
import static BDD.Contract.SQL_DELETE_COMIC;
import static BDD.Contract.SQL_DELETE_OWNERBOOKS;
import static BDD.Contract.SQL_DELETE_SERIES;
import static BDD.Contract.SQL_DELETE_USERS;

/**
 * Created by Julie on 11.04.2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Comics.db";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Create all tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_AUTHORS);
        db.execSQL(SQL_CREATE_COMICS);
        db.execSQL(SQL_CREATE_OWNERBOOKS);
        db.execSQL(SQL_CREATE_SERIES);
        db.execSQL(SQL_CREATE_USERS);
    }


    // Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_AUTHORS);
        db.execSQL(SQL_DELETE_COMIC);
        db.execSQL(SQL_DELETE_OWNERBOOKS);
        db.execSQL(SQL_DELETE_SERIES);
        db.execSQL(SQL_DELETE_USERS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }


    // inserting a new Author in the database
    public void insertAuthors(Context context, String lastname){
        DbHelper myDBHelper = new DbHelper(context);

        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.Authors.COLUMN_NAME_LASTNAME,lastname);

        db.insert(Contract.Authors.TABLE_NAME,null,values);
    }

    // inserting a new Serie in the database
    public void insertSeries(Context context, String name, String editionHouse, int idAuthor){
        DbHelper myDBHelper = new DbHelper(context);

        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.Series.COLUMN_NAME_SERIENAME,name);
        values.put(Contract.Series.COLUMN_NAME_EDITION_HOUSE,editionHouse);
        values.put(Contract.Series.COLUMN_NAME_IDAUTHOR,idAuthor);

        db.insert(Contract.Series.TABLE_NAME,null,values);
    }

    // inserting a new Comic in the database
    public void insertComic(Context context,int idUser, int number, int idAuthor, int idSerie, String titre, String language, String synopsis, String photo){
        DbHelper myDBHelper = new DbHelper(context);

        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.Comic.COLUMN_NAME_NUMBER, number);
        values.put(Contract.Comic.COLUMN_NAME_IDAUTHOR, idAuthor);
        values.put(Contract.Comic.COLUMN_NAME_IDSERIE, idSerie);
        values.put(Contract.Comic.COLUMN_NAME_TITRE, titre);
        values.put(Contract.Comic.COLUMN_NAME_LANGUAGE,language);
        values.put(Contract.Comic.COLUMN_NAME_SYNOPSIS, synopsis);
        values.put(Contract.Comic.COLUMN_NAME_PHOTO, photo);

        db.insert(Contract.Comic.TABLE_NAME,null,values);
        int lastComicInsertedid =1;

        SQLiteDatabase dbReadable = new DbHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Contract.Comic.TABLE_NAME+" ORDER BY "+ Contract.Comic._ID+" DESC LIMIT 1",null);

        if(cursor.moveToFirst()){
            lastComicInsertedid = Integer.valueOf(cursor.getString(0));
        }

        insertOwnerBooks(context,idUser,lastComicInsertedid);

    }

    // deleting a comic from the database with id
    public void deleteComic(Context context,int id){
        DbHelper myDBHelper = new DbHelper(context);

        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        db.delete(Contract.Comic.TABLE_NAME, Contract.Comic._ID+" = ?",new String[]{String.valueOf(id)});

    }

    // Inserting a new User in the database
    public void insertUser(Context context, String username, String password, String email){
        DbHelper myBDHelper = new DbHelper(context);

        SQLiteDatabase db = myBDHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.Users.COLUMN_NAME_USERNAME,username);
        values.put(Contract.Users.COLUMN_NAME_PASSWORD,password);
        values.put(Contract.Users.COLUMN_NAME_EMAIL,email);

        db.insert(Contract.Users.TABLE_NAME,null,values);

    }

    // deleting a comic from the database with id
    public void deleteUser(Context context,int id){
        DbHelper myDBHelper = new DbHelper(context);

        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        deleteOwnerBook(context,id);
        db.delete(Contract.Users.TABLE_NAME, Contract.Users._ID+" = ?",new String[]{String.valueOf(id)});


    }

    // inserting data in the join table between User and Comic
    public void insertOwnerBooks(Context context, int idUser, int idBook){
        DbHelper myBDHelper = new DbHelper(context);

        SQLiteDatabase db = myBDHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.Ownerbooks.COLUMN_NAME_IDUSER,idUser);
        values.put(Contract.Ownerbooks.COLUMN_NAME_IDBOOK, idBook);

        db.insert(Contract.Ownerbooks.TABLE_NAME,null,values);
    }



    public void deleteOwnerBook(Context context, int idUser){
        DbHelper myDbHelper = new DbHelper(context);

        String query = "SELECT * FROM "+Contract.Ownerbooks.TABLE_NAME+" WHERE "+Contract.Ownerbooks.COLUMN_NAME_IDUSER+" = '"+idUser+"'";

        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
                int idComic = c.getInt(2);
                deleteComic(context,idComic);
            }while (c.moveToNext());
        }

        db=myDbHelper.getWritableDatabase();
        db.delete(Contract.Ownerbooks.TABLE_NAME,Contract.Ownerbooks.COLUMN_NAME_IDUSER+" = ?",new String[]{String.valueOf(idUser)});

       // SQLiteDatabase db = myDbHelper.getWritableDatabase();

    }




}
