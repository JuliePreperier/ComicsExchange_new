package com.example.comicsexchange_new;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import BDD.DbHelper;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DbHelper myDBHelper = new DbHelper(this); // L'Activité est le Context qu'il faut donner
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


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


    }
}
