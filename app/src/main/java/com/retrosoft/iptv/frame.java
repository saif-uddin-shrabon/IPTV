package com.retrosoft.iptv;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class frame extends AppCompatActivity {

    BottomNavigationView btNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        btNav = findViewById(R.id.btm_nav);

        btNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();

                if(id==R.id.nav_home){

                    loadFrg(new home_Fragment(),true);

                }
                else if (id==R.id.nav_player){
                    loadFrg(new Player_Fragment(),false);

                }
                else if (id==R.id.nav_fav){
                    loadFrg(new Favorites_Fragment(),false);

                }
                else {
                    loadFrg(new Setting_Fragment(),false);

                }

                return true;
            }

        });
        btNav.setSelectedItemId(R.id.nav_home);
    }
    public  void loadFrg (Fragment fragment, boolean flag){
        FragmentManager frgM = getSupportFragmentManager();
        FragmentTransaction frgT= frgM.beginTransaction();

        if(flag){
            frgT.add(R.id.FrmLay,fragment);


        }
        else {
            frgT.replace(R.id.FrmLay,fragment);
        }


        frgT.commit();

    }
}