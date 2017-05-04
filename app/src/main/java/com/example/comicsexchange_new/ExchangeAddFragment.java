package com.example.comicsexchange_new;

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
import android.widget.Toast;

import BDD.DbHelper;

/**
 * Created by Sandy on 04.05.2017.
 */

public class ExchangeAddFragment extends Fragment{


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
                Toast.makeText(getContext(), "New comic saved", Toast.LENGTH_SHORT).show();
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

        getActivity().setTitle("Add");

        return view;
    }


    }
