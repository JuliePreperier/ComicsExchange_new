package com.example.comicsexchange_new;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    public static int spinnerposition=1;

    private ProgressDialog progressing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        int currentUserId=0;
        if(currentIdNull(currentUserId)){
            if(getIntent().getExtras().getInt(this.getString(R.string.currentUserId))!=0){
                currentUserId = getIntent().getExtras().getInt(this.getString(R.string.currentUserId));
            }
            else{
                currentUserId = getIntent().getExtras().getInt(this.getString(R.string.currentUserIdFromSettings));
            }

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.inflateMenu(R.menu.navigation);

        fragmentManager = getSupportFragmentManager();
        loadingProgress();
        fragment = new ExplorerDefaultFragment();


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.navigation_news:
                        fragment = new ExplorerDefaultFragment(); // new Fragment();
                        break;
                    case R.id.navigation_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.navigation_exchange:
                        fragment = new ExchangeFragment();
                        break;
                    case R.id.navigation_settings:
                        fragment = new SettingsFragment();
                        break;
                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);
                return true;
            }
        });


    }

    public boolean currentIdNull(int currentUserId){

        if(currentUserId==0){
            return true;
        }
        return false;
    }

    public void loadingProgress(){
        progressing=new ProgressDialog(this);
        progressing.setMessage(getString(R.string.loading)); // Setting Message
        progressing.setTitle(getString(R.string.description)); // Setting Title
        progressing.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressing.show(); // Display Progress Dialog
        progressing.setCancelable(false);
        
        new Thread(new Runnable() {
            public void run() {
                try {
                    int cpt=0;
                    while (cpt<=6){
                        cpt=0;

                        for(int i = 0; i<SyncToCloud.loadingDone.size(); i++){

                            if (SyncToCloud.loadingDone.get(i)){
                                cpt++;
                            }
                        }
                        if(cpt==5){
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressing.dismiss();

            }

        }).start();

        fragment = new ExplorerDefaultFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
        transaction.addToBackStack(null);
    };



}
