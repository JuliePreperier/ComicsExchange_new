package com.example.comicsexchange_new;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import BDD.Contract;
import BDD.DbHelper;
import cloud.InsertAuthorsAsync;

/**
 * Created by Sandy on 04.05.2017.
 */

public class ExchangeAddFragment extends Fragment{

    EditText serie;
    EditText title;
    EditText number;
    EditText author;
    EditText synopsis;
    EditText language;
    int currentUserId=0;



    private Fragment fragment;
    private FragmentManager fragmentManager;


    public ExchangeAddFragment() {
        // Required empty public constructor
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.exchange_add_menu, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.add_button_save:

                    createComic();
                    Toast.makeText(getContext(), this.getString(R.string.newcomicsaved), Toast.LENGTH_SHORT).show();
                    fragment = new ExchangeFragment();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment).commit();

                return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Permet de dire qu'on veut avoir des boutons dans notre menu
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_exchange_add, container, false);

        getActivity().setTitle(this.getString(R.string.add));

        // get the id of the connected user

        serie = (EditText) view.findViewById(R.id.Serie_view);
        title = (EditText) view.findViewById(R.id.Title_view);
        number = (EditText) view.findViewById(R.id.Number_view);
        author = (EditText) view.findViewById(R.id.Author_view);
        synopsis = (EditText) view.findViewById(R.id.Synopsis_view);
        language = (EditText) view.findViewById(R.id.language_view);


        if(currentIdNull(currentUserId)){
            if(getActivity().getIntent().getExtras().getInt(this.getString(R.string.currentUserId))!=0){
                currentUserId = getActivity().getIntent().getExtras().getInt(this.getString(R.string.currentUserId));
            }
            else{
                currentUserId = getActivity().getIntent().getExtras().getInt(this.getString(R.string.currentUserIdFromSettings));
            }
        }

        return view;
    }

    public void createComic(){

        String strgSerie=serie.getText().toString().trim();
        if(strgSerie == null){
            strgSerie = "";
        }
        String strgTitle= title.getText().toString().trim();
        if(strgTitle==null){
            strgTitle="";
        }
        String strgNumber= number.getText().toString().trim();
        if(strgNumber==null){
            strgNumber="";
        }
        String strgAuthor= author.getText().toString().trim();
        if(strgAuthor==null){
            strgAuthor="";
        }
        String strgSynopsis= synopsis.getText().toString().trim();
        if(strgSynopsis==null){
            strgSynopsis="";
        }
        String strgLanguage= language.getText().toString().trim();
        if(strgLanguage==null){
            strgLanguage="";
        }

        int idAuthor;
        int idSerie;

        SQLiteDatabase db = new DbHelper(getContext()).getWritableDatabase();

        DbHelper database = new DbHelper(getContext());
        database.insertAuthors(getContext(),strgAuthor);


        Cursor c = db.rawQuery("SELECT * FROM "+ Contract.Authors.TABLE_NAME+" WHERE "+ Contract.Authors.COLUMN_NAME_LASTNAME+" = '"+strgAuthor+"'",null);

        c.moveToFirst();
        idAuthor = c.getInt(0);

        database.insertSeries(getContext(),strgSerie,"",idAuthor);

        Cursor c2 = db.rawQuery("SELECT * FROM "+ Contract.Series.TABLE_NAME+" WHERE "+ Contract.Series.COLUMN_NAME_SERIENAME+" = '"+strgSerie+"'",null);

        c2.moveToFirst();
        idSerie = c2.getInt(0);

        database.insertComic(getContext(),currentUserId,Integer.valueOf(strgNumber),idAuthor,idSerie,strgTitle,strgLanguage,strgSynopsis,"ic_default_cover");

    }

    public boolean currentIdNull(int currentUserId){

        if(currentUserId==0){
            return true;
        }
        return false;
    }


    }
