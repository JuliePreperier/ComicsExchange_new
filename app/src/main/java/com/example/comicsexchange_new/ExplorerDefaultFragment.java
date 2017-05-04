package com.example.comicsexchange_new;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import BDD.ComicDB;
import BDD.Contract;
import BDD.DbHelper;

public class ExplorerDefaultFragment extends Fragment {

    private ListView listView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    public ExplorerDefaultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explorer_default, container, false);
        getActivity().setTitle("News");
        listView = (ListView) view.findViewById(R.id.listViewExplorer);

        List<Comic> comics = generateComics();

        ComicAdapter_Explorer adapter = new ComicAdapter_Explorer(this.getContext(),comics);
        listView.setAdapter(adapter);



        // permet de passer de la news au details d'un comic
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int comicId = ((Comic) listView.getItemAtPosition(position)).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("SelectedComicId", comicId);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new ExplorerDetailsFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();

            }
        });

        return view;
    }

    private List<Comic> generateComics(){
        List<Comic> comics = new ArrayList<Comic>();

        DbHelper db = new DbHelper(getContext());
        ComicDB comicDB = new ComicDB(db);

        comics = comicDB.getLastComic();

        return comics;
    }



}
