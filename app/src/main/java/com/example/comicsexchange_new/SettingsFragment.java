package com.example.comicsexchange_new;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Locale;

import BDD.Contract;
import BDD.DbHelper;
import cloud.ListUserAsync;

import static com.example.comicsexchange_new.SyncToCloud.loadingDone;


public class SettingsFragment extends Fragment{

    Spinner spinner;
    View view;
    int currentUserId;
    EditText username;
    EditText password;
    EditText email;

    private ProgressDialog progressing;

    private Fragment fragment;
    private FragmentManager fragmentManager;


    public SettingsFragment() {
        // Required empty public constructor
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.settings_menu, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item){
        DbHelper db = new DbHelper(getContext());
        switch (item.getItemId()){
            case R.id.settings_button_save:
                updateUser();
                Toast.makeText(getContext(), this.getString(R.string.settingsSaved), Toast.LENGTH_SHORT).show();
                if(spinner.getSelectedItem().toString().equals("Français")){
                    changeToFR(view);
                }
                if(spinner.getSelectedItem().toString().equals("English")){
                    changeToEN(view);
                }
                if(spinner.getSelectedItem().toString().equals("Deutsch")){
                    changeToDE(view);
                }
                return true;
            case R.id.settings_button_sync:
                // remettre l'arrayList en false
                for(int i =0; i<loadingDone.size();i++){
                    loadingDone.set(i,false);
                }

                toCloudSynchronizedCloud();
                new ListUserAsync(db).execute();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.putExtra(this.getString(R.string.currentUserIdFromSettings),currentUserId);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Permet de dire qu'on veut avoir des boutons dans notre menu
        setHasOptionsMenu(true);

        if(currentIdNull(currentUserId)){
            if(getActivity().getIntent().getExtras().getInt(this.getString(R.string.currentUserId))!=0){
                currentUserId = getActivity().getIntent().getExtras().getInt(this.getString(R.string.currentUserId));
            }
            else{
                currentUserId = getActivity().getIntent().getExtras().getInt(this.getString(R.string.currentUserIdFromSettings));
            }
        }

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle(this.getString(R.string.settings));

        spinner = (Spinner) view.findViewById(R.id.spinner);
        updateSpinner();
        spinner.setSelection(MainActivity.spinnerposition);


        Button deleteButton = (Button) view.findViewById(R.id.suppressButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelper db = new DbHelper(view.getContext());
                db.deleteUser(view.getContext(),currentUserId);

                Intent intent = new Intent(getActivity(),LogInActivity.class);
                startActivity(intent);

            }
        });




        SQLiteDatabase db = new DbHelper(this.getContext()).getReadableDatabase();

                /* -- RECUPERATION DES INFORMATIONS DANS LA BASE DE DONNEES -- */

        Cursor c = db.rawQuery("SELECT * FROM "+ Contract.Users.TABLE_NAME+" WHERE "+ Contract.Users._ID+" = '"+currentUserId+"'",null);

        if(c.moveToFirst()){
            // récupération du username du current user
            String info = c.getString(1);
            username = (EditText) view.findViewById(R.id.settings_username_edit);
            username.setText(info);

            // récupération du password du current user
            info = c.getString(2);
            password = (EditText) view.findViewById(R.id.settings_password_edit);
            password.setText(info);


            // récupération de l'email du current user
            info = c.getString(3);
            email = (EditText) view.findViewById(R.id.settings_email_edit);
            email.setText(info);

        }

        return view;
    }

    public void updateUser(){

        DbHelper database = new DbHelper(getContext());

        String strgUsername = username.getText().toString().trim();
        String strgPassword = password.getText().toString().trim();
        String strgEmail = email.getText().toString().trim();

        SQLiteDatabase db = database.getWritableDatabase();

        String strSQL = "UPDATE "+ Contract.Users.TABLE_NAME+" SET '"
                + Contract.Users.COLUMN_NAME_EMAIL+"' = '"+strgEmail+"', '"
                + Contract.Users.COLUMN_NAME_USERNAME+"' = '"+strgUsername+"', '"
                + Contract.Users.COLUMN_NAME_PASSWORD+"' = '"+strgPassword+"'" +
                "WHERE "+ Contract.Users._ID+" = '"+currentUserId+"'";
        db.execSQL(strSQL);

        // mettre le user dans le cloud
        database.toCloudUser();

    }


    //Méthode pour changer la langue en français
    public void changeToFR(View v){
        MainActivity.spinnerposition=0;
        String languageToLoad="fr";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale=locale;

        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());


        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.putExtra(this.getString(R.string.currentUserIdFromSettings),currentUserId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Méthode pour changer la langue en anglais
    public void changeToEN(View v){
        MainActivity.spinnerposition=1;
        String languageToLoad="en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale=locale;

        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.putExtra(this.getString(R.string.currentUserIdFromSettings),currentUserId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Méthode pour changer la langue en allemand
    public void changeToDE(View v){
        MainActivity.spinnerposition=2;
        String languageToLoad="de";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale=locale;

        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());


        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.putExtra(this.getString(R.string.currentUserIdFromSettings),currentUserId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    // check si le Extras currentUserId
    public boolean currentIdNull(int currentUserId){

        if(currentUserId==0){
            return true;
        }
        return false;
    }

    public void updateSpinner(){
        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.language_array,
                android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void toCloudSynchronizedCloud(){
        DbHelper db = new DbHelper(getContext());

        db.toCloudUser();
        db.toCloudAuthor();
        db.toCloudSerie();
        db.toCloudComic();
        db.toCloudOwnerBook();
    }


}
