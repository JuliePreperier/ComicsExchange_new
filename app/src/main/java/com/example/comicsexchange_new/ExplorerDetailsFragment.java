package com.example.comicsexchange_new;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import BDD.Contract;
import BDD.DbHelper;


public class ExplorerDetailsFragment extends Fragment {

    String idAuthor;
    String idComic;
    String idSerie;
    String idPhoto;

    public ExplorerDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explorer_details, container, false);

        getActivity().setTitle("Details");

        SQLiteDatabase db = new DbHelper(this.getContext()).getReadableDatabase();

        //Bundle bundle = getArguments();
        //String id=bundle.toString();
        String id="3";

        /* -- RECUPERATION DES INFORMATIONS DANS LA BASE DE DONNEES -- */

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Contract.Comic.TABLE_NAME+" WHERE "+ Contract.Comic._ID+" = "+id,null);
        Resources res = getResources();


        if(cursor.moveToFirst()) {
            // récupération de l'id du Comic
            String info = cursor.getString(0);
            TextView idComicView = (TextView) view.findViewById(R.id.idComic);
            idComicView.setText(info);

            // récupération du title du Comic
            info = cursor.getString(3);
            TextView titreView = (TextView) view.findViewById(R.id.Title_view);
            titreView.setText(info);

            // récupération du number du Comic
            info = cursor.getString(7);
            TextView numberView = (TextView) view.findViewById(R.id.Number_view);
            numberView.setText(info);

            // récupération du synopsis du Comic
            info = cursor.getString(5);
            TextView synopsisView = (TextView) view.findViewById(R.id.Synopsis_view);
            synopsisView.setText(info);

            // récupération du language du Comic
            info = cursor.getString(4);
            TextView languageView = (TextView) view.findViewById(R.id.language_view);
            languageView.setText(info);

            // récupération de la photo du Comic
            info=cursor.getString(6);
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
            info = info +" "+ cursor.getString(2);
            TextView authorView = (TextView) view.findViewById(R.id.Author_view);
            authorView.setText(info);
        }

        // récupération du Owner du Comic
        //cursor = db.rawQuery("SELECT USERNAME FROM "+ Contract.Users.TABLE_NAME+", "+ Contract.Ownerbooks.TABLE_NAME+" WHERE "+ Contract.Ownerbooks.COLUMN_NAME_IDUSER+" = '"+ Contract.Users._ID+"' AND "+ Contract.Ownerbooks.COLUMN_NAME_IDBOOK+" = "+id,null);

        Cursor cursor2 = db.rawQuery("SELECT * FROM "+Contract.Ownerbooks.TABLE_NAME+" WHERE "+ Contract.Ownerbooks.COLUMN_NAME_IDBOOK+" = "+id,null);

        String idOwner="";

        if(cursor2.moveToFirst()){
            idOwner=cursor2.getString(0);
        }

        cursor = db.rawQuery("SELECT * FROM "+ Contract.Users.TABLE_NAME+" WHERE "+ Contract.Users._ID+" = "+idOwner,null);

        if(cursor.moveToFirst()){
            String info=cursor.getString(3);
            TextView ownerView = (TextView) view.findViewById(R.id.Owner_view);
            ownerView.setText(info);
        }

        // récupération de la série du Comic
        cursor = db.rawQuery("SELECT * FROM "+ Contract.Series.TABLE_NAME+" WHERE "+ Contract.Series._ID+" = "+idSerie,null);

        if(cursor.moveToFirst()){
            String info=cursor.getString(1);
            TextView serieView = (TextView) view.findViewById(R.id.Serie_view);
            serieView.setText(info);
        }

        return view;
    }

}
