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
import android.widget.TextView;
import android.widget.Toast;

import BDD.Contract;
import BDD.DbHelper;

/**
 * Created by Sandy on 04.05.2017.
 */

public class ExchangeAddFragment extends Fragment{

    TextView serie;
    TextView Title;
    TextView Number;
    TextView Owner;
    TextView Author;
    TextView Synopsis;
    TextView Language;



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
                Toast.makeText(getContext(), this.getString(R.string.newcomicsaved), Toast.LENGTH_SHORT).show();
                fragment = new ExchangeFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();

                if(serie == null || Title == null || Number == null || Author == null || Synopsis == null || Language == null ){
                    Toast.makeText(getContext(), this.getString((R.string.infosFiled)), Toast.LENGTH_SHORT).show();
                }
                else{
                    createComic();
                    Toast.makeText(getContext(), "New comic saved", Toast.LENGTH_SHORT).show();
                    fragment = new ExchangeFragment();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment).commit();
                }




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

        return view;
    }

    public void createComic(){

        String strgSerie=serie.getText().toString().trim();
        String strgTitle=Title.getText().toString().trim();
        String strgNumber=Number.getText().toString().trim();
        String strgOwner=Owner.getText().toString().trim();
        String strgAuthor= Author.getText().toString().trim();
        String strgSynopsis=Synopsis.getText().toString().trim();
        String strgLanuage=Language.getText().toString().trim();

        int idAuthor;
        int idSerie;

        SQLiteDatabase db = new DbHelper(getContext()).getWritableDatabase();
        SQLiteDatabase db2 = new DbHelper(getContext()).getReadableDatabase();

        String strSQL1 = "INSERT INTO "+ Contract.Authors.TABLE_NAME+" ("+ Contract.Authors.COLUMN_NAME_LASTNAME+") VALUES" +
                " ('"+strgAuthor+"')";

        Cursor c = db.rawQuery("SELECT * FROM "+ Contract.Authors.TABLE_NAME+" WHERE "+ Contract.Authors.COLUMN_NAME_LASTNAME+" = '"+strgAuthor+"'",null);

        c.moveToFirst();
        idAuthor = c.getInt(0);


        String strSQL2 = " INSERT INTO "+ Contract.Series.TABLE_NAME+" ("+ Contract.Series.COLUMN_NAME_EDITION_HOUSE+", "+ Contract.Series.COLUMN_NAME_SERIENAME+", "+ Contract.Series.COLUMN_NAME_IDAUTHOR+")" +
                " VALUES ('edition hous', '"+strgSerie+"', '"+idAuthor+"')";

        Cursor c2 = db.rawQuery("SELECT * FROM "+ Contract.Series.TABLE_NAME+" WHERE "+ Contract.Series.COLUMN_NAME_SERIENAME+" = '"+strgSerie+"'",null);

        c.moveToFirst();
        idSerie = c.getInt(0);

        String strSQL3 = " INSERT INTO "+ Contract.Comic.TABLE_NAME+" ("+ Contract.Comic.COLUMN_NAME_IDSERIE+", "+ Contract.Comic.COLUMN_NAME_IDAUTHOR+", "
                + Contract.Comic.COLUMN_NAME_SYNOPSIS+", "+ Contract.Comic.COLUMN_NAME_LANGUAGE+", "+ Contract.Comic.COLUMN_NAME_NUMBER+", "+ Contract.Comic.COLUMN_NAME_TITRE+")" +
                " VALUES ('"+idSerie+"', '"+idAuthor+"', '"+strgAuthor+"', '"+strgSynopsis+"', '"+strgLanuage+"', '"+strgNumber+"', '"+strgTitle+"')";

    }


    }
