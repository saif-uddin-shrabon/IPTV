package com.retrosoft.iptv;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class home_Fragment extends Fragment {
    Button  btn1,btn2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn1 = view.findViewById(R.id.cnl1);
        btn2 = view.findViewById(R.id.cnl2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                go("https://6n3yogbnd9ok-hls-live.5centscdn.com/threetamil/d0dbe915091d400bd8ee7f27f0791303.sdp/index.m3u8");

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                go("https://d2e1asnsl7br7b.cloudfront.net/7782e205e72f43aeb4a48ec97f66ebbe/index.m3u8");

            }
        });

        return view;
    }

    private void go(String url){


        Player.websiteUri = url;
        startActivity(new Intent(getContext(),Player.class));

    }
}