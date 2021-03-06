package com.example.comicsexchange_new;


import android.graphics.Color;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import BDD.ComicDB;
import BDD.DbHelper;

public class ExchangeFragment extends Fragment {

    private Fragment fragment;
    private FragmentManager fragmentManager;


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
                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new ExchangeAddFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);
                return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Permet de dire qu'on veut avoir des boutons dans notre menu
        setHasOptionsMenu(true);


        // get the id of the connected user
        int currentUserId=0;
        if(currentIdNull(currentUserId)){
            if(getActivity().getIntent().getExtras().getInt(this.getString(R.string.currentUserId))!=0){
                currentUserId = getActivity().getIntent().getExtras().getInt(this.getString(R.string.currentUserId));
            }
            else{
                currentUserId = getActivity().getIntent().getExtras().getInt(this.getString(R.string.currentUserIdFromSettings));
            }
        }

        // inflate the view with the layout fragment_exchange
        View view = inflater.inflate(R.layout.fragment_exchange, container, false);

        // set the title in the actionBar
        getActivity().setTitle(this.getString(R.string.iExchange));

        // set the listview for the exchange part
        final ListView exchangeListView = (ListView) view.findViewById(R.id.listViewExchange);

        // filling the list with comics
        List<Comic> comics = generateComics(currentUserId);

        // set the adapter to the list
        ComicAdapter_Exchange adapter = new ComicAdapter_Exchange(this.getContext(), comics);
        exchangeListView.setAdapter(adapter);

        // let navigate from fragment "News" to "details"
        exchangeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int comicId = ((Comic) exchangeListView.getItemAtPosition(position)).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("SelectedComicId", comicId);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new ExchangeDetailsFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);
            }
        });



        return view;
    }

    // generate a list of comic from the database
    private List<Comic> generateComics(int currentUserId){
        List<Comic> comics = new ArrayList<Comic>();

        DbHelper db = new DbHelper(getContext());
        ComicDB comicDB = new ComicDB(db);

        comics = comicDB.getComicsPerUser(currentUserId);

        if(comics.size() == 0){
            Toast.makeText(getContext(),this.getString(R.string.noComics),Toast.LENGTH_SHORT).show();
        }

        return comics;
    }

    public boolean currentIdNull(int currentUserId){

        if(currentUserId==0){
            return true;
        }
        return false;
    }
}

