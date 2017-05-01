package BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_AUTHORS);
        db.execSQL(SQL_CREATE_COMICS);
        db.execSQL(SQL_CREATE_OWNERBOOKS);
        db.execSQL(SQL_CREATE_SERIES);
        db.execSQL(SQL_CREATE_USERS);
    }

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

    public void insertAuthors(Context context, String firstname, String lastname){
        DbHelper myDBHelper = new DbHelper(context);

        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.Authors.COLUMN_NAME_FIRSTNAME,firstname);
        values.put(Contract.Authors.COLUMN_NAME_LASTNAME,lastname);

        db.insert(Contract.Authors.TABLE_NAME,null,values);
    }


    public void insertSeries(Context context, String name, String editionHouse, int idAuthor){
        DbHelper myDBHelper = new DbHelper(context);

        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.Series.COLUMN_NAME_SERIENAME,name);
        values.put(Contract.Series.COLUMN_NAME_EDITION_HOUSE,editionHouse);
        values.put(Contract.Series.COLUMN_NAME_IDAUTHOR,idAuthor);

        db.insert(Contract.Series.TABLE_NAME,null,values);
    }

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
        int id =1;

        SQLiteDatabase dbReadable = new DbHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Contract.Comic.TABLE_NAME+" ORDER BY "+ Contract.Comic._ID+" DESC LIMIT 1",null);

        if(cursor.moveToFirst()){
            id = Integer.valueOf(cursor.getString(0));
        }

        insertOwnerBooks(context,idUser,id);

    }

    public void deleteComic(Context context,int id){
        DbHelper myDBHelper = new DbHelper(context);

        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        db.delete(Contract.Comic.TABLE_NAME, Contract.Comic._ID+" = ?",new String[]{String.valueOf(id)});

    }

    public void insertUser(Context context, String firstname, String lastname, String username, String password, String email, String language){
        DbHelper myBDHelper = new DbHelper(context);

        SQLiteDatabase db = myBDHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.Users.COLUMN_NAME_FIRSTNAME,firstname);
        values.put(Contract.Users.COLUMN_NAME_LASTNAME,lastname);
        values.put(Contract.Users.COLUMN_NAME_USERNAME,username);
        values.put(Contract.Users.COLUMN_NAME_PASSWORD,password);
        values.put(Contract.Users.COLUMN_NAME_EMAIL,email);
        values.put(Contract.Users.COLUMN_NAME_LANGUAGE,language);

        db.insert(Contract.Users.TABLE_NAME,null,values);

    }

    public void insertOwnerBooks(Context context, int idUser, int idBook){
        DbHelper myBDHelper = new DbHelper(context);

        SQLiteDatabase db = myBDHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Contract.Ownerbooks.COLUMN_NAME_IDUSER,idUser);
        values.put(Contract.Ownerbooks.COLUMN_NAME_IDBOOK, idBook);

        db.insert(Contract.Ownerbooks.TABLE_NAME,null,values);
    }


}
