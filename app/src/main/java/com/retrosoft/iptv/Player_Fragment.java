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

    private final String EXT_INF_SP = "#EXTINF:-1";
    private final String KOD_IP_DROP_TYPE = "#KODIPROP:inputstream.adaptive.license_type=";
    private final String KOD_IP_DROP_KEY = "#KODIPROP:inputstream.adaptive.license_key=";
    private final String TVG_NAME = "tvg-name=";
    private final String TVG_LOGO = "tvg-logo=";
    private final String GROUP_TITLE = "group-title=";
    private final String COMMA = ",";
    private final String HTTP = "http://";
    private final String HTTPS = "https://";

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
                        new dbmanagert(getContext()).addRecord(channelName,Url);
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

//    public void saveList() {
//        String link = null;
//        // Retrieve the link from the bundle
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            link = bundle.getString("link");
//        }
//
//        // Call the saveList() method
//       new GetChannelsTask().execute(link);
//
//    }

//    public class GetChannelsTask extends AsyncTask<String, Void, List<Map<String, String>>> {
//
//        @Override
//        protected List<Map<String, String>> doInBackground(String... urls) {
//            String urlString = urls[0];
//            List<Map<String, String>> channels = new ArrayList<>();
//            Map<String, String> currentChannel = new HashMap<>();
//
//            try {
//                URL urlObj = new URL(urlString);
//                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
//                conn.setRequestMethod("GET");
//                conn.connect();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String line;
//
//                //new code
//
//                while ((line = reader.readLine()) != null) {
//                    line = line.replaceAll("\"", "");
//
//                    if (line.startsWith(KOD_IP_DROP_TYPE)) {
//                        currentChannel.put("channelDrmType", line.split(KOD_IP_DROP_TYPE)[1].trim());
//
//                    }else if (line.startsWith(KOD_IP_DROP_KEY)) {
//                        currentChannel.put("channelDrmKey", line.split(KOD_IP_DROP_KEY)[1].trim());
//
//                    } else if (line.startsWith(EXT_INF_SP)) {
//                        String[] parts = line.split(COMMA, 2);
//                        if (parts.length > 1) {
//                            String name = parts[1];
//                            if (name.contains(TVG_NAME)) {
//                                name = name.split(TVG_NAME)[1].split(TVG_LOGO)[0];
//                            }
//                            currentChannel.put("name", name);
//                        }
//
//                        if (line.contains(GROUP_TITLE)) {
//                            String groupTitle = line.split(GROUP_TITLE)[1].split(COMMA)[0];
//                            currentChannel.put("channelGroup", groupTitle);
//                        }
//
//
//
//                        if (line.contains(TVG_LOGO)) {
//                            String logo = line.split(TVG_LOGO)[1].split(COMMA)[0];
//                            currentChannel.put("logo", logo);
//                        }
//                    }
//                    else if (line.startsWith(HTTP) || line.startsWith(HTTPS)) {
//                        currentChannel.put("url", line);
//                        channels.add(currentChannel);
//                        currentChannel = new HashMap<>();
//                    }
//
//
//                }
//
//                reader.close();
//                conn.disconnect();
//
//            } catch (IOException e) {
//                Timber.e(e);
//                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            return channels;
//        }
//
//        @Override
//        protected void onPostExecute(List<Map<String, String>> channels) {
//
//            for (Map<String, String> channel : channels) {
//                String name = channel.get("name");
//                String channelGroup = channel.get("channelGroup");
//                String logo = channel.get("logo");
//                String url = channel.get("url");
//                String channelDrmType = channel.get("channelDrmType");
//                String channelDrmKey = channel.get("channelDrmKey");
//
//                new dbmanager(getContext()).addRecord(name,logo,url);
//
//     //           Log.d("Channel", "Name: " + name + ", Logo: " + logo + ", URL: " + url + ", channelGroup: " +channelGroup + ", channelDrmType: "+ channelDrmType + ", channelDrmKey: "+channelDrmKey);
////
////                Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
//            }
//
//
//
//        }
//    }

}