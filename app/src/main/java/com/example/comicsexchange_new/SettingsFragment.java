package com.example.comicsexchange_new;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Locale;



public class SettingsFragment extends Fragment{

    Spinner spinner;
    View view;

    public SettingsFragment() {
        // Required empty public constructor
    }

    //Create an action bar button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.settings_menu, menu);
    }

    //Handle button activities
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.settings_button_save:
                Toast.makeText(getContext(), "The language was changed", Toast.LENGTH_SHORT).show();
                if(spinner.getSelectedItem().toString()=="Français"){
                    changeToFR(view);
                }
                if(spinner.getSelectedItem().toString()=="English"){
                    changeToEN(view);
                }
                if(spinner.getSelectedItem().toString()=="Deutsch"){
                    changeToDE(view);
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

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle("Settings");

        spinner = (Spinner) view.findViewById(R.id.spinner);
        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.language_array,
                android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        return view;
    }


    //Méthode pour changer la langue en français
    public void changeToFR(View v){
        String languageToLoad="fr";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //noinspection depreciation
        config.locale=locale;

        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(),SettingsFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Méthode pour changer la langue en anglais
    public void changeToEN(View v){
        String languageToLoad="en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.locale=locale;

        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Méthode pour changer la langue en allemand
    public void changeToDE(View v){
        String languageToLoad="de";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //noinspection depreciation
        config.locale=locale;

        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(),SettingsFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
