package com.example.comicsexchange_new;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import BDD.Contract;
import BDD.DbHelper;

public class SignInActivity extends AppCompatActivity {

    public EditText username;
    public EditText email;
    public EditText password;
    public SQLiteDatabase db;
    DbHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDbHelper = new DbHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = (EditText) findViewById(R.id.UsernameSignIn);
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                username.setFocusable(true);
                username.setHint("");
            }
        });



        email = (EditText) findViewById(R.id.EmailSignIn);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                email.setFocusable(true);
                email.setHint("");
            }
        });

        password = (EditText) findViewById(R.id.Sign_in_Password);
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password.setFocusable(true);
                password.setHint("");
            }
        });

        db = myDbHelper.getReadableDatabase();

        final Button loginButton = (Button) findViewById(R.id.button_Sign);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser();


            }
        });
    }

    public void createNewUser(){
        String strgUsername = username.getText().toString().trim();
        String strgEmail = email.getText().toString().trim();
        String strgPassword = password.getText().toString().trim();
        db = myDbHelper.getReadableDatabase();

        // check si doublon

        Cursor c = db.rawQuery("SELECT * FROM "+ Contract.Users.TABLE_NAME+" WHERE "+ Contract.Users.COLUMN_NAME_USERNAME
                +" = '"+strgUsername+"'",null);

        if(c.moveToFirst()){
            Toast.makeText(getApplicationContext(),this.getString(R.string.UsernameUsed),Toast.LENGTH_LONG).show();
            this.recreate();
        }
        else {
            Cursor c2 = db.rawQuery("SELECT * FROM "+ Contract.Users.TABLE_NAME+" WHERE "+ Contract.Users.COLUMN_NAME_EMAIL
                    +" = '"+strgEmail+"'",null);
            if(c2.moveToFirst()){
                Toast.makeText(getApplicationContext(),this.getString(R.string.emailUsed),Toast.LENGTH_LONG).show();
                this.recreate();
            }
            else {

                myDbHelper.insertUser(this,strgUsername,strgPassword,strgEmail);
                Toast.makeText(getApplicationContext(),this.getString(R.string.UserCreated),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignInActivity.this,LogInActivity.class); // ATTENTION --> Nom des class java
                startActivity(intent);
            }
        }


    }

}
