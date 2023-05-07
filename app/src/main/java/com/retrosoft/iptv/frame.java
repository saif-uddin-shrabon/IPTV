package com.retrosoft.iptv;


import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class frame extends AppCompatActivity {

    BottomNavigationView btNav;
    ImageView imageView,ref;

    Dialog myDialog;
    Button btnPlay,btnDelete;
    EditText inputUrl,inputRfrl;
    String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        btNav = findViewById(R.id.btm_nav);
        imageView = findViewById(R.id.add_btn);
        ref = findViewById(R.id.ref_btn);
        AdView mAdView;

        mAdView = findViewById(R.id.adView);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog = new Dialog(frame.this);
                myDialog.setContentView(R.layout.link_gateway);
                btnPlay = myDialog.findViewById(R.id.playerbtn);
                inputUrl = myDialog.findViewById(R.id.url);
                inputRfrl = myDialog.findViewById(R.id.channelrf);
                btnDelete = myDialog.findViewById(R.id.delete_all);
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Url = inputUrl.getText().toString().trim();
                        String channelName = inputRfrl.getText().toString();

                        // link and channel name store database
                        new dbmanagert(frame.this).addRecord(channelName,Url);

                        // link extraction database
                        new GetChannelsTask(frame.this).execute(Url);
                        myDialog.dismiss();
                    }
                });



                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new dbmanager((frame.this)).deleteAllData();

                    }
                });
                myDialog.show();


            }
        });

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