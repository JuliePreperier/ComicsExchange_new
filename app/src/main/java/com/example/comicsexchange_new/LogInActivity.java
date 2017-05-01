package com.example.comicsexchange_new;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import BDD.DbHelper;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DbHelper myDBHelper = new DbHelper(this); // L'Activité est le Context qu'il faut donner
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        SQLiteDatabase db = myDBHelper.getReadableDatabase();


        final Button loginButton = (Button) findViewById(R.id.buttonConnect);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,MainActivity.class); // ATTENTION --> Nom des class java
                startActivity(intent);
            }
        });

        final TextView signIn = (TextView) findViewById(R.id.text_SignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });


        myDBHelper.insertAuthors(this,"firstname","lastname");
        myDBHelper.insertSeries(this,"Marvel","blabla",1);
        myDBHelper.insertUser(this,"Julie","Préperier","Eliwe","1234","julie.preperier@netplus.ch","Français");
        myDBHelper.insertComic(this,1,3,1,1,"Captain America","Français","capt. america","capt");


    }
}
