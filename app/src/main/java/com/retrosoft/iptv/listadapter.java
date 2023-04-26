package com.retrosoft.iptv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class listadapter extends RecyclerView.Adapter<listadapter.myviewholder> {

    ArrayList<TableName> dataholder;

    public listadapter(ArrayList<TableName> dataholder){
        this.dataholder = dataholder;
    }
    @NonNull
    @Override
    public listadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card,parent,false);
        return new listadapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listadapter.myviewholder holder, int position) {


            holder.Llink.setText(dataholder.get(position).getCOLUMN_LINK());
            holder.cName.setText(dataholder.get(position).getCOLUMN_NAME());

            holder.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String link = dataholder.get(position).getCOLUMN_LINK();

                    new GetChannelsTask(v.getContext()).execute(link);

                }
            });

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView Llink,cName;
        View viewLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            Llink=(TextView) itemView.findViewById(R.id.urlLink);
            cName = (TextView) itemView.findViewById(R.id.CName);

            viewLayout = itemView.findViewById(R.id.viewLayoutL);

        }
    }
}



