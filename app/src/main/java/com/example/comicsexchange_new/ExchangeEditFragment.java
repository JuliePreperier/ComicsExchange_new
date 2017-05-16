package com.example.comicsexchange_new;

import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import BDD.Contract;
import BDD.DbHelper;

/**
 * Created by Sandy on 04.05.2017.
 */

public class ExchangeEditFragment extends Fragment {


    String idAuthor;
    String idComic;
    String idSerie;
    String idPhoto;

    // definition des view
    EditText serieView;
    EditText titreView;
    EditText numberView;
    EditText synopsisView;
    EditText languageView;
    EditText authorView;

    private Fragment fragment;
    private FragmentManager fragmentManager;


    public ExchangeEditFragment() {
        // Required empty public constructor
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.exchange_edit_menu, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.edit_button_save:
                Toast.makeText(getContext(), this.getString(R.string.editSaved), Toast.LENGTH_SHORT).show();
                updateUser();
                Bundle bundle = new Bundle();
                fragmentManager = getActivity().getSupportFragmentManager();
                bundle.putInt(this.getString(R.string.selectedcomicid), Integer.valueOf(idComic));
                fragment = new ExchangeDetailsFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
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

        View view = inflater.inflate(R.layout.fragment_exchange_edit, container, false);

        getActivity().setTitle(this.getString(R.string.edit));

        SQLiteDatabase db = new DbHelper(this.getContext()).getReadableDatabase();
        int transferredId = getArguments().getInt(this.getString(R.string.selectedcomicid));


        /* -- RECUPERATION DES INFORMATIONS DANS LA BASE DE DONNEES -- */

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Contract.Comic.TABLE_NAME+" WHERE "+ Contract.Comic._ID+" = '"+transferredId+"'",null);
        Resources res = getResources();


        if(cursor.moveToFirst()) {
            // récupération de l'id du Comic
            String info = cursor.getString(0);
            TextView idComicView = (TextView) view.findViewById(R.id.idComic);
            idComicView.setText(info);

            // récupération du title du Comic
            info = cursor.getString(3);
            titreView = (EditText) view.findViewById(R.id.Title_view);
            titreView.setText(info);

            // récupération du number du Comic
            info = cursor.getString(7);
            numberView = (EditText) view.findViewById(R.id.Number_view);
            numberView.setText(info);

            // récupération du synopsis du Comic
            info = cursor.getString(5);
            synopsisView = (EditText) view.findViewById(R.id.Synopsis_view);
            synopsisView.setText(info);

            // récupération du language du Comic
            info = cursor.getString(4);
            languageView = (EditText) view.findViewById(R.id.language_view);
            languageView.setText(info);

            // récupération de la photo du Comic
            info=cursor.getString(6);
            if(info==null){
                info = "ic_default_cover";
            }
            int resId = res.getIdentifier(info,"drawable", view.getContext().getPackageName());
            ImageView imageComicView = (ImageView) view.findViewById(R.id.imageView);
            imageComicView.setImageResource(resId);



            // Ici ce sont les données que l'ont veut prendre dans la table Comic avant de changer le cursor
            idAuthor = cursor.getString(1);
            idComic = cursor.getString(0);
            idSerie = cursor.getString(2);
            idPhoto = cursor.getString(6);

        }

        /*-- ICI C'EST DE LA RÉCUPÉRATION DE DONNÉES QUI NE SONT PAS DANS LA MÊME TABLE --*/


        // récupération de l'author du Comic
        cursor = db.rawQuery("SELECT * FROM "+ Contract.Authors.TABLE_NAME+" WHERE "+ Contract.Authors._ID+" = "+idAuthor,null);

        if(cursor.moveToFirst()){
            String info=cursor.getString(1);
            authorView = (EditText) view.findViewById(R.id.Author_view);
            authorView.setText(info);
        }

        // récupération du Owner du Comic

        Cursor cursor2 = db.rawQuery("SELECT * FROM "+Contract.Ownerbooks.TABLE_NAME+" WHERE "+ Contract.Ownerbooks.COLUMN_NAME_IDBOOK+" = '"+transferredId+"'",null);

        String idOwner="";

        if(cursor2.moveToFirst()){
            idOwner=cursor2.getString(1);
        }

        cursor = db.rawQuery("SELECT * FROM "+ Contract.Users.TABLE_NAME+" WHERE "+ Contract.Users._ID+" = "+idOwner,null);

        if(cursor.moveToFirst()){
            String ownerName=cursor.getString(1);
            TextView ownerView = (TextView) view.findViewById(R.id.Owner_view);

            ownerView.setText(ownerName);
        }

        // récupération de la série du Comic
        cursor = db.rawQuery("SELECT * FROM "+ Contract.Series.TABLE_NAME+" WHERE "+ Contract.Series._ID+" = "+idSerie,null);

        if(cursor.moveToFirst()){
            String info=cursor.getString(1);
            serieView = (EditText) view.findViewById(R.id.Serie_view);
            serieView.setText(info);
        }

        return view;
    }

    public void updateUser(){

        String strgAuthor = authorView.getText().toString().trim();
        String strgSerie = serieView.getText().toString().trim();

        String strgTitle = titreView.getText().toString().trim();
        String strgNumber = numberView.getText().toString().trim();
        String strgSynopsis = synopsisView.getText().toString().trim();
        String strgLanguage = languageView.getText().toString().trim();



        int editedAuthorId = authorAlreadyExist(strgAuthor);
        int editedSerieId = serieAlreadyExist(editedAuthorId,strgSerie);

        SQLiteDatabase db = new DbHelper(getContext()).getWritableDatabase();

        String strSQL = "UPDATE "+ Contract.Comic.TABLE_NAME+" SET '"
                + Contract.Comic.COLUMN_NAME_TITRE+"' = '"+strgTitle+"', '"
                + Contract.Comic.COLUMN_NAME_NUMBER+"' = '"+strgNumber+"', '"
                + Contract.Comic.COLUMN_NAME_LANGUAGE+"' = '"+strgLanguage+"', '"
                + Contract.Comic.COLUMN_NAME_SYNOPSIS+"' = '"+strgSynopsis+"', '"
                + Contract.Comic.COLUMN_NAME_IDAUTHOR+"' = '"+editedAuthorId+"', '"
                + Contract.Comic.COLUMN_NAME_IDSERIE+"' = '"+editedSerieId+"'" +
                " WHERE "+ Contract.Comic._ID+" = "+idComic;
        db.execSQL(strSQL);

    }

    public int authorAlreadyExist(String strgAuthors){
        int returnedId=0;
        SQLiteDatabase db = new DbHelper(this.getContext()).getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM "+ Contract.Authors.TABLE_NAME+" WHERE "
                + Contract.Authors.COLUMN_NAME_LASTNAME+" = '"+strgAuthors+"'",null);

        if(c.moveToFirst()){
            returnedId = c.getInt(0);

        }
        else{
            DbHelper database = new DbHelper(this.getContext());
            database.insertAuthors(this.getContext(),strgAuthors);

            database.getReadableDatabase();

            Cursor c2 = db.rawQuery("SELECT * FROM "+ Contract.Authors.TABLE_NAME+" WHERE "
                    + Contract.Authors.COLUMN_NAME_LASTNAME+" = '"+strgAuthors+"'",null);

            if(c2.moveToFirst()){
                returnedId = c2.getInt(0);
            }
        }
        return returnedId;
    }

    public int serieAlreadyExist(int idAuthor, String strgSerie){
        int returnedId=0;
        SQLiteDatabase db = new DbHelper(this.getContext()).getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM "+ Contract.Series.TABLE_NAME+" WHERE "
                + Contract.Series.COLUMN_NAME_SERIENAME+" = '"+strgSerie+"'",null);

        if(c.moveToFirst()){
            returnedId = c.getInt(0);
        }
        else{
            DbHelper database = new DbHelper(this.getContext());
            database.insertSeries(this.getContext(),strgSerie,"",idAuthor);

            database.getReadableDatabase();

            Cursor c2 = db.rawQuery("SELECT * FROM "+ Contract.Series.TABLE_NAME+" WHERE "
                    + Contract.Series.COLUMN_NAME_SERIENAME+" = '"+strgSerie+"'",null);

            if(c2.moveToFirst()){
                returnedId = c2.getInt(0);
            }
        }

        return returnedId;

    }

}
