package com.retrosoft.iptv;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        inputUrl = view.findViewById(R.id.url);
        inputRfrl = view.findViewById(R.id.channelrf);
        btnPlay = view.findViewById(R.id.playerbtn);
        btnDelete = view.findViewById(R.id.delete_all);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url = inputUrl.getText().toString().trim();
//                String chnnel = ch.getText().toString();
                new GetChannelsTask().execute(Url);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new dbmanager((getContext())).deleteAllData();
            }
        });


        return view;
    }

    private class GetChannelsTask extends AsyncTask<String, Void, List<Map<String, String>>> {

        @Override
        protected List<Map<String, String>> doInBackground(String... urls) {
            String urlString = urls[0];
            List<Map<String, String>> channels = new ArrayList<>();
            Map<String, String> currentChannel = new HashMap<>();

            try {
                URL urlObj = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;

                //new code

                while ((line = reader.readLine()) != null) {
                    line = line.replaceAll("\"", "");
                    if (line.startsWith(KOD_IP_DROP_TYPE)) {
                        currentChannel.put("channelDrmType", line.split(KOD_IP_DROP_TYPE)[1].trim());

                    }else if (line.startsWith(KOD_IP_DROP_KEY)) {
                        currentChannel.put("channelDrmKey", line.split(KOD_IP_DROP_KEY)[1].trim());

                    } else if (line.startsWith(EXT_INF_SP)) {
                        currentChannel.put("name", line.split(TVG_NAME).length > 1 ?
                                line.split(TVG_NAME)[1].split(TVG_LOGO)[0] :
                                line.split(COMMA)[1]);

                        currentChannel.put("channelGroup", line.split(GROUP_TITLE)[1].split(COMMA)[0]);
                        currentChannel.put("logo", line.split(TVG_LOGO).length > 1 ?
                                line.split(TVG_LOGO)[1].split(GROUP_TITLE)[0] : "");

                    }else if (line.startsWith(HTTP) || line.startsWith(HTTPS)) {
                        currentChannel.put("url", line);
                        channels.add(currentChannel);
                        currentChannel = new HashMap<>();
                    }

                }


                //old code

//                while ((line = reader.readLine()) != null) {
//                    line = line.trim();
//
//                    if (line.startsWith("#EXTINF:")) {
//                        String[] info = line.split(",(.+)", 2);
//                        currentChannel.put("name", info[1]);
//                        String logo = line.replaceAll(".tvg-logo=\"([^\"])\".*", "$1");
//                        currentChannel.put("logo", logo);
//                    } else if (line.startsWith("http")) {
//                        currentChannel.put("url", line);
//                        channels.add(currentChannel);
//                        currentChannel = new HashMap<>();
//                    }
//                }
                reader.close();
                conn.disconnect();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return channels;
        }

        @Override
        protected void onPostExecute(List<Map<String, String>> channels) {

            for (Map<String, String> channel : channels) {
                String name = channel.get("name");
                String channelGroup = channel.get("channelGroup");
                String logo = channel.get("logo");
                String url = channel.get("url");
                String channelDrmType = channel.get("channelDrmType");
                String channelDrmKey = channel.get("channelDrmKey");

                String res = String.valueOf(new dbmanager((getContext())).addRecord(name,logo,url));

                Log.d("Channel", "Name: " + name + ", Logo: " + logo + ", URL: " + url + ", channelGroup: " +channelGroup + ", channelDrmType: "+ channelDrmType + ", channelDrmKey: "+channelDrmKey);
//
                Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
            }

//            Log.d("Channels", channels.toString());

        }
    }

}