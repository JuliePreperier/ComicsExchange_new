package com.example.comicsexchange_new;


import android.graphics.Color;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ExchangeFragment extends Fragment {



    public ExchangeFragment() {
        // Required empty public constructor
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.exchangedefault_menu, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle item selection
        switch (item.getItemId()){
            case R.id.exchangedefault_button_add:
                //do sth
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Permet de dire qu'on veut avoir des boutons dans notre menu
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_exchange, container, false);
        getActivity().setTitle("I exchange");

        ListView exchangeListView = (ListView) view.findViewById(R.id.listViewExchange);

        List<Comic> comics = generateComics();

        ComicAdapter_Exchange adapter = new ComicAdapter_Exchange(this.getContext(), comics);

        exchangeListView.setAdapter(adapter);

        return view;
    }

    private List<Comic> generateComics(){
        List<Comic> comics = new ArrayList<Comic>();
        comics.add(new Comic(1, Color.BLACK, "Hunger Game","premier d'une grande trilogie"));
        comics.add(new Comic(2, Color.BLUE,"Harry Potter", "Troisième volet de la sage"));
        comics.add(new Comic(3, Color.RED, "Iron man","Incontournable de la license Marvel"));

        return comics;
    }
}

