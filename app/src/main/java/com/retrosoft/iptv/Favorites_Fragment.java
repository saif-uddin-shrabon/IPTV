package com.retrosoft.iptv;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Favorites_Fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<model> dataholder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);


        recyclerView = view.findViewById(R.id.favrecview);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),numberOfColumns));

        Cursor cursor = new dbmanager(getContext()).readAllFavData();

        dataholder = new ArrayList<>(); // Initialize the ArrayList before adding data to it

        while (cursor.moveToNext()){
            model obj =  new model(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(0));
            dataholder.add(obj);
        }

        FavAdapter adapter= new FavAdapter(dataholder);
        recyclerView.setAdapter(adapter);
        return view;
    }
}