package com.retrosoft.iptv;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;


public class Player_Fragment extends Fragment {

     public static  String channelName;
    EditText inputUrl,inputRfrl;
    Button btnPlay;
    Dialog myDialog;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    static ArrayList<TableName> dataholder;
    String Url;

    public static String triger = "";
    SearchView searchView;
    listadapter adapter;

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        floatingActionButton = view.findViewById(R.id.flotingBtn);
        if (getContext() == null) {
            return view;
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addChannle();
            }
        });


        searchView = view.findViewById(R.id.listSearch);
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
        recyclerView = view.findViewById(R.id.listview);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        Cursor cursor = new dbmanagert(getContext()).readAllData();

        dataholder = new ArrayList<TableName>(); // Initialize the ArrayList before adding data to it

        while (cursor.moveToNext()){
            TableName obj =  new TableName(cursor.getString(1),cursor.getString(2),cursor.getString(0));
            dataholder.add(obj);
        }
        adapter = new listadapter(dataholder);
        recyclerView.setAdapter(adapter);




        return view;
    }



    public void addChannle(){

        myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.link_gateway);
        btnPlay = myDialog.findViewById(R.id.playerbtn);
        inputUrl = myDialog.findViewById(R.id.url);
        inputRfrl = myDialog.findViewById(R.id.channelrf);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Url = inputUrl.getText().toString().trim();
                channelName = inputRfrl.getText().toString();


                // link extraction database
                new GetChannelsTask(getContext()).execute(Url);

                myDialog.dismiss();

            }
        });

        myDialog.show();

    }


    public void refresh(){
        ////For reload the page
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getFragmentManager().beginTransaction().detach(Player_Fragment.this).commitNow();
            getFragmentManager().beginTransaction().attach(Player_Fragment.this).commitNow();
        } else {
            getFragmentManager().beginTransaction().detach(Player_Fragment.this).attach(Player_Fragment.this).commit();
        }
    }


    private void filterList(String newText) {
        List<TableName> filterdList = new ArrayList<>();

        for (TableName item : dataholder){

            if(item.getCOLUMN_NAME().toLowerCase().contains(newText.toLowerCase())){
                filterdList.add(item);
            }
        }
        if(filterdList.isEmpty()){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }else {
            adapter.setfilter((ArrayList<TableName>) filterdList);
        }
    }


}