package com.retrosoft.iptv;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class home_Fragment extends Fragment{
    Button  btn1,btn2;
    RecyclerView recyclerView;

    SearchView searchView;

    ArrayList<model> dataholder;
    myadapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        searchView = view.findViewById(R.id.homesearchbar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        recyclerView = view.findViewById(R.id.recview);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

        Cursor cursor = new dbmanager(getContext()).readAllData();

        dataholder = new ArrayList<>(); // Initialize the ArrayList before adding data to it

        while (cursor.moveToNext()) {
            model obj = new model(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(0));
            dataholder.add(obj);
        }

         adapter = new myadapter(dataholder);

        recyclerView.setAdapter(adapter);
      //  recyclerView.getAdapter().notifyDataSetChanged();


        return view;
    }

    private void filterList(String newText) {
        List<model> filterdList = new ArrayList<>();

        for (model item : dataholder){

            if(item.getCOLUMN_NAME().toLowerCase().contains(newText.toLowerCase())){
                filterdList.add(item);
            }
        }
        if(filterdList.isEmpty()){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }else {
                 adapter.filter((ArrayList<model>) filterdList);
        }
    }

}

