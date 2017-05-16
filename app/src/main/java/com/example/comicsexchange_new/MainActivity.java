package com.example.comicsexchange_new;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    public static int spinnerposition=1;

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

}
