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
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),numberOfColumns));

        Cursor cursor = new dbmanager(getContext()).readAllData();

        dataholder = new ArrayList<>(); // Initialize the ArrayList before adding data to it

        while (cursor.moveToNext()){
            model obj =  new model(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(0));
            dataholder.add(obj);
        }

        myadapter adapter= new myadapter(dataholder);

        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
        return view;
    }


}