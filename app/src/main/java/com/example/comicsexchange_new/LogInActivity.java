package com.example.comicsexchange_new;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

import static com.example.comicsexchange_new.SyncToCloud.loadingDone;

public class LogInActivity extends AppCompatActivity {

    public EditText username;
    public EditText password;
    public SQLiteDatabase db;
    private ProgressDialog progressing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DbHelper myDBHelper = new DbHelper(this); // L'Activité est le Context qu'il faut donner
        super.onCreate(savedInstanceState);

        // initialisation de la classe SyncToCloud
        SyncToCloud stc = new SyncToCloud();

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

        // remettre l'arrayList en false
        for(int i =0; i<loadingDone.size();i++){
            loadingDone.set(i,false);
        }

        loadingProgress();

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

    public void loadingProgress(){
        progressing=new ProgressDialog(this);
        progressing.setMessage(getString(R.string.loading)); // Setting Message
        progressing.setTitle(getString(R.string.description)); // Setting Title
        progressing.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressing.show(); // Display Progress Dialog
        progressing.setCancelable(false);

        new Thread(new Runnable() {
            public void run() {
                try {
                    int cpt=0;
                    while (cpt<=6){
                        cpt=0;

                        for(int i = 0; i<SyncToCloud.loadingDone.size(); i++){

                            if (SyncToCloud.loadingDone.get(i)){
                                cpt++;
                            }
                        }
                        if(cpt==5){
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressing.dismiss();

            }

        }).start();



    };

}
