package BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.List;

import cloud.InsertAuthorsAsync;
import cloud.InsertComicAsync;
import cloud.InsertOwnerBooksAsync;
import cloud.InsertSerieAsync;
import cloud.InsertUserAsync;

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
    public Context context;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    public void toCloudAuthor(){

        SQLiteDatabase dbR= new DbHelper(context).getReadableDatabase();
        Cursor c = dbR.rawQuery("SELECT * from "+ Contract.Authors.TABLE_NAME, null);

        if (c.moveToFirst())
        {
            do {
                entities.authorsApi.model.Authors authors = new entities.authorsApi.model.Authors();
                authors.setId(Long.valueOf(c.getString(0)));
                authors.setName(c.getString(1));

                new InsertAuthorsAsync(authors).execute();

            }while (c.moveToNext());
        }
        Log.e("debugCloud","all authors data saved");
    }

    public void fromCloudAuhtor(List<entities.authorsApi.model.Authors> items){
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete(Contract.Authors.TABLE_NAME, null, null);

        for (entities.authorsApi.model.Authors t : items) {
            ContentValues value = new ContentValues();
            value.put(Contract.Authors._ID, t.getId());
            value.put(Contract.Authors.COLUMN_NAME_LASTNAME, t.getName());

            sqlDB.insert(Contract.Authors.TABLE_NAME, null,value);
        }
        sqlDB.close();
        Log.e("debugCloud","all author data got");
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

    public void toCloudSerie(){

        SQLiteDatabase dbR= new DbHelper(context).getReadableDatabase();
        Cursor c = dbR.rawQuery("SELECT * from "+ Contract.Series.TABLE_NAME, null);


        if (c.moveToFirst())
        {
            do {
                entities.serieApi.model.Serie serie = new entities.serieApi.model.Serie();
                serie.setId(Long.valueOf(c.getString(0)));
                serie.setName(c.getString(1));
                serie.setEditionHouse(c.getString(2));
                serie.setIdAuthor(c.getInt(3));

                new InsertSerieAsync(serie).execute();

            }while (c.moveToNext());
        }
        Log.e("debugCloud","all Serie data saved");
    }

    public void fromCloudSerie(List<entities.serieApi.model.Serie> items){
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete(Contract.Series.TABLE_NAME, null, null);

        for (entities.serieApi.model.Serie t : items) {
            ContentValues value = new ContentValues();
            value.put(Contract.Series._ID, t.getId());
            value.put(Contract.Series.COLUMN_NAME_SERIENAME, t.getName());
            value.put(Contract.Series.COLUMN_NAME_EDITION_HOUSE, t.getEditionHouse());
            value.put(Contract.Series.COLUMN_NAME_IDAUTHOR,t.getIdAuthor());

            sqlDB.insert(Contract.Series.TABLE_NAME, null,value);
        }
        sqlDB.close();
        Log.e("debugCloud","all Serie data got");
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

    public void toCloudComic(){

        SQLiteDatabase dbR= new DbHelper(context).getReadableDatabase();

        Cursor c = dbR.rawQuery("SELECT * from "+ Contract.Comic.TABLE_NAME, null);


        if (c.moveToFirst())
        {
            do {
                entities.comicApi.model.Comic comic = new entities.comicApi.model.Comic();
                comic.setId(Long.valueOf(c.getString(0)));
                comic.setIdAuthor(c.getInt(1));
                comic.setIdSerie(c.getInt(2));
                comic.setTitre(c.getString(3));
                comic.setLanguage(c.getString(4));
                comic.setSynopsis(c.getString(5));
                comic.setPhoto(c.getString(6));
                comic.setNumber(c.getString(7));

                new InsertComicAsync(comic).execute();

            }while (c.moveToNext());
        }
        Log.e("debugCloud","all Comic data saved");
    }

    public void fromCloudComic(List<entities.comicApi.model.Comic> items){
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete(Contract.Comic.TABLE_NAME, null, null);

        for (entities.comicApi.model.Comic t : items) {
            ContentValues value = new ContentValues();
            value.put(Contract.Comic._ID, t.getId());
            value.put(Contract.Comic.COLUMN_NAME_IDAUTHOR, t.getIdAuthor());
            value.put(Contract.Comic.COLUMN_NAME_IDSERIE,t.getIdSerie());
            value.put(Contract.Comic.COLUMN_NAME_TITRE, t.getTitre());
            value.put(Contract.Comic.COLUMN_NAME_LANGUAGE,t.getLanguage());
            value.put(Contract.Comic.COLUMN_NAME_SYNOPSIS, t.getSynopsis());
            value.put(Contract.Comic.COLUMN_NAME_PHOTO,t.getPhoto());
            value.put(Contract.Comic.COLUMN_NAME_NUMBER, t.getNumber());


            sqlDB.insert(Contract.Comic.TABLE_NAME, null,value);
        }
        sqlDB.close();
        Log.e("debugCloud","all Comic data got");
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

    public void toCloudUser(){

        SQLiteDatabase dbR= new DbHelper(context).getReadableDatabase();

        Cursor c = dbR.rawQuery("SELECT * from "+ Contract.Users.TABLE_NAME, null);


        if (c.moveToFirst())
        {
            do {
                entities.userApi.model.User user = new entities.userApi.model.User();
                user.setId(Long.valueOf(c.getString(0)));
                user.setUsername(c.getString(1));
                user.setPassword(c.getString(2));
                user.setEmail(c.getString(3));

                new InsertUserAsync(user).execute();

            }while (c.moveToNext());
        }
        Log.e("debugCloud","all User data saved");
    }

    public void fromCloudUser(List<entities.userApi.model.User> items){
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete(Contract.Users.TABLE_NAME, null, null);

        for (entities.userApi.model.User t : items) {
            ContentValues value = new ContentValues();
            value.put(Contract.Users._ID, t.getId());
            value.put(Contract.Users.COLUMN_NAME_USERNAME, t.getUsername());
            value.put(Contract.Users.COLUMN_NAME_PASSWORD, t.getPassword());
            value.put(Contract.Users.COLUMN_NAME_EMAIL,t.getEmail());


            sqlDB.insert(Contract.Users.TABLE_NAME, null,value);
        }
        sqlDB.close();
        Log.e("debugCloud","all User data got");
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

    public void toCloudOwnerBook(){

        SQLiteDatabase dbR= new DbHelper(context).getReadableDatabase();

        Cursor c = dbR.rawQuery("SELECT * from "+ Contract.Ownerbooks.TABLE_NAME, null);


        if (c.moveToFirst())
        {
            do {
                entities.ownerBooksApi.model.OwnerBooks ownerBook = new entities.ownerBooksApi.model.OwnerBooks();
                ownerBook.setId(Long.valueOf(c.getString(0)));
                ownerBook.setIdUser(c.getInt(1));
                ownerBook.setIdComic(c.getInt(2));

                new InsertOwnerBooksAsync(ownerBook).execute();

            }while (c.moveToNext());
        }
        Log.e("debugCloud","all OwnerBook data saved");
    }

    public void fromCloudOwnerBook(List<entities.ownerBooksApi.model.OwnerBooks> items){
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete(Contract.Ownerbooks.TABLE_NAME, null, null);

        for (entities.ownerBooksApi.model.OwnerBooks t : items) {
            ContentValues value = new ContentValues();
            value.put(Contract.Ownerbooks._ID, t.getId());
            value.put(Contract.Ownerbooks.COLUMN_NAME_IDUSER, t.getIdUser());
            value.put(Contract.Ownerbooks.COLUMN_NAME_IDBOOK, t.getIdComic());


            sqlDB.insert(Contract.Ownerbooks.TABLE_NAME, null,value);
        }
        sqlDB.close();
        Log.e("debugCloud","all OwnerBook data got");
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
