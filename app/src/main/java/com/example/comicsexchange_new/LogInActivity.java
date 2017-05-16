package com.example.comicsexchange_new;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import BDD.Contract;
import BDD.DbHelper;
import BDD.UserDB;
import cloud.ListAuthorsAsync;
import cloud.ListUserAsync;

public class LogInActivity extends AppCompatActivity {

    public EditText username;
    public EditText password;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DbHelper myDBHelper = new DbHelper(this); // L'Activité est le Context qu'il faut donner
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in);
        username = (EditText) findViewById(R.id.username_editText);
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                username.setFocusable(true);
                username.setHint("");
            }
        });
        password = (EditText) findViewById(R.id.password_editText);
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password.setFocusable(true);
                password.setHint("");
            }
        });

        db = myDBHelper.getReadableDatabase();
        new ListUserAsync(new DbHelper(this)).execute();

        Button loginButton = (Button) findViewById(R.id.buttonConnect);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkData();
            }
        });

        TextView signIn = (TextView) findViewById(R.id.text_SignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });


        Cursor c = db.rawQuery("SELECT * FROM "+ Contract.Comic.TABLE_NAME,null);

    }


    public void checkData(){

         String strgUsername = username.getText().toString().trim();
         String strgPassword = password.getText().toString().trim();

        db = new DbHelper(this).getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM "+Contract.Users.TABLE_NAME+" WHERE "
                +Contract.Users.COLUMN_NAME_USERNAME+" = '"+strgUsername+
                "' AND "+Contract.Users.COLUMN_NAME_PASSWORD+" = '"+strgPassword+"'",null);

        if(c.moveToFirst()){
            int currentUserId = c.getInt(0);
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            intent.putExtra(this.getString(R.string.currentUserId),currentUserId);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(),this.getString(R.string.wrongLogin),Toast.LENGTH_SHORT).show();
        }
    }
}
