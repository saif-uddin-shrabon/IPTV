package com.retrosoft.iptv;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


    EditText inputUrl,inputRfrl;
    Button btnPlay,btnDelete;

    Dialog myDialog;

    FloatingActionButton floatingActionButton;


    RecyclerView recyclerView;
    ArrayList<TableName> dataholder;
    String Url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        floatingActionButton = view.findViewById(R.id.flotingBtn);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog = new Dialog(getActivity());
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
                        new dbmanagert(getContext()).addRecord(channelName,Url);

                        // link extraction database
                        new GetChannelsTask(getContext()).execute(Url);
                    }
                });



                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new dbmanager((getContext())).deleteAllData();
//                        new dbmanagert((getContext())).deleteAllData();
                    }
                });
                myDialog.show();
            }
        });



        recyclerView = view.findViewById(R.id.listview);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        Cursor cursor = new dbmanagert(getContext()).readAllData();

        dataholder = new ArrayList<TableName>(); // Initialize the ArrayList before adding data to it

        while (cursor.moveToNext()){
            TableName obj =  new TableName(cursor.getString(1),cursor.getString(2));
            dataholder.add(obj);
        }
        listadapter adapter = new listadapter(dataholder);
        recyclerView.setAdapter(adapter);

        return view;
    }

}