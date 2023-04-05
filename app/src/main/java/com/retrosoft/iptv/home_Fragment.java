package com.retrosoft.iptv;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class home_Fragment extends Fragment {
    Button  btn1,btn2;
    RecyclerView recyclerView;
    ArrayList<model> dataholder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recview);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),numberOfColumns));

        Cursor cursor = new dbmanager(getContext()).readAllData();

        dataholder = new ArrayList<>(); // Initialize the ArrayList before adding data to it

        while (cursor.moveToNext()){
            model obj =  new model(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            dataholder.add(obj);
        }

        myadapter adapter= new myadapter(dataholder);
        recyclerView.setAdapter(adapter);


//        btn1 = view.findViewById(R.id.cnl1);
//        btn2 = view.findViewById(R.id.cnl2);

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                go("https://v4.tustreaming.cl/tevexinter/index.m3u8");
//
//            }
//        });
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                go("https://ndtvprofitelemarchana.akamaized.net/hls/live/2003680-b/ndtvprofit/master.m3u8");
//
//            }
//        });

        return view;
    }

//    private void go(String url){
//
//
//        Player.websiteUri = url;
//        startActivity(new Intent(getContext(),Player.class));
//
//    }
}