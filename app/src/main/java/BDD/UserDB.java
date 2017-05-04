package BDD;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.comicsexchange_new.User;


/**
 * Created by Julie on 04.05.2017.
 */

public class UserDB {

    private DbHelper db;

    public UserDB(DbHelper db){ this.db=db;}

    // method to get only one comic with its id
    public User getUser(int idUser){
        String selectQuery = "SELECT * FROM "+Contract.Users.TABLE_NAME +" WHERE "+Contract.Users._ID+" = "+idUser;// SQL request

        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(selectQuery,null);

        if(c!=null)
            c.moveToFirst();

        User user= new User();
        user.setId(c.getInt(c.getColumnIndex(Contract.Users._ID)));
        user.setPassword(c.getString(c.getColumnIndex(Contract.Users.COLUMN_NAME_PASSWORD)));
        user.setUsername(c.getString(c.getColumnIndex(Contract.Users.COLUMN_NAME_USERNAME)));
        user.setEmail(c.getString(c.getColumnIndex(Contract.Users.COLUMN_NAME_EMAIL)));

        return user;
    }

}
