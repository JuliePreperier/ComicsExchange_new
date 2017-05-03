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
        myDBHelper.insertSeries(this,"Marsupilami","FilmOffice",1);
        myDBHelper.insertSeries(this,"Spirou","Dupuis",1);
        myDBHelper.insertUser(this,"Julie","Préperier","Eliwe","1234","julie.preperier@netplus.ch","Français");
        myDBHelper.insertUser(this,"Sandy","Millius","Devil","5678","sandy.millius@students.hevs.ch","Français");
        myDBHelper.insertComic(this,1,3,1,1,"Captain America","Français","capt. america","capt");
        myDBHelper.insertComic(this,1,9,1,1,"Hulk","Français","comic des aventures de Hulk","hulk");
        myDBHelper.insertComic(this,1,13,1,1,"Iron Man","Français","Nouveau Marvel sur Iron Man","iron");
        myDBHelper.insertComic(this,2,64,1,1,"Thor","Français","Thor dans une nouvelle aventure","thor");
        myDBHelper.insertComic(this,2,24,1,1,"Wolverine","Français","Le préféré de tous les X-men","wol");
        myDBHelper.insertComic(this,2,15,1,3,"Spirou","Français","Edition spéciale des 75 ans de Spirou","spirou");
        myDBHelper.insertComic(this,2,7,1,2,"Marsupilami","Français","Les aventures du Marsupilami","marsu");

    }
}
