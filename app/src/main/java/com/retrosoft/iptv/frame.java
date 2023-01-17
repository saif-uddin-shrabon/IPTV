package com.retrosoft.iptv;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

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