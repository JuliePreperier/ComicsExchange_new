package com.example.comicsexchange_new;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);


        // pour le titre de l'actionbar
        getActivity().setTitle("Search");

        // pour la listView
        ListView listView = (ListView) view.findViewById(R.id.listViewExplorer);
        List<Comic> comics = generateComics();
        //Adapter pour la listView
        ComicAdapter_Explorer adapter = new ComicAdapter_Explorer(this.getContext(),comics);
        listView.setAdapter(adapter);

        return view;
    }

    private List<Comic> generateComics(){
        List<Comic> comics = new ArrayList<Comic>();
        comics.add(new Comic(Color.BLACK, "Hunger Game","premier d'une grande trilogie"));
        comics.add(new Comic(Color.BLUE,"Harry Potter", "Troisième volet de la sage"));
        comics.add(new Comic(Color.RED, "Iron man","Incontournable de la license Marvel"));
        comics.add(new Comic(Color.BLACK, "Hunger Game","premier d'une grande trilogie"));
        comics.add(new Comic(Color.BLUE,"Harry Potter", "Troisième volet de la sage"));
        comics.add(new Comic(Color.RED, "Iron man","Incontournable de la license Marvel"));
        comics.add(new Comic(Color.BLACK, "Hunger Game","premier d'une grande trilogie"));
        comics.add(new Comic(Color.BLUE,"Harry Potter", "Troisième volet de la sage"));
        comics.add(new Comic(Color.RED, "Iron man","Incontournable de la license Marvel"));

        return comics;
    }
}
