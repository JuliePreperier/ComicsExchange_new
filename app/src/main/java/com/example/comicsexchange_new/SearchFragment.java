package com.example.comicsexchange_new;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import BDD.ComicDB;
import BDD.DbHelper;

public class SearchFragment extends Fragment {

    private ListView listView;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    EditText inputSearch;
    ComicSearch_Adapter adapter;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);


        // pour le titre de l'actionbar
        getActivity().setTitle("Search");

        inputSearch = (EditText) view.findViewById(R.id.inputSearch);

        // pour la listView
        listView = (ListView) view.findViewById(R.id.listViewExplorer);
        ArrayList<Comic> comics = generateComics();
        //Adapter pour la listView
        adapter = new ComicSearch_Adapter(getActivity(),comics);
        listView.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // not used
            }
        });

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

    private ArrayList<Comic> generateComics(){
        ArrayList<Comic> comics = new ArrayList<Comic>();

        DbHelper db = new DbHelper(getContext());
        ComicDB comicDB = new ComicDB(db);

        comics = comicDB.getComics();

        return comics;
    }
}
