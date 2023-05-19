package com.retrosoft.iptv;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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


    public  void  setfilter(ArrayList<TableName> filterdList){

        this.dataholder = filterdList;

        notifyDataSetChanged();
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

        holder.viewLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.option1:

                                String linkdelete = dataholder.get(position).getCOLUMN_LINK();
                                new dbmanager(v.getContext()).deleteAllData(linkdelete);

                                int idToDelete = Integer.parseInt(dataholder.get(position).getCOLUMN_ID());
                                boolean success = new dbmanagert(v.getContext()).deleteRecord(idToDelete);

//                               new Player_Fragment().loadData();
                                if (success) {
                                    // Remove the item from your data list and refresh the RecyclerView
                                    Toast.makeText(v.getContext(), "Delete list", Toast.LENGTH_SHORT).show();
                                }


                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
                return true;
            }
        });




    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    public interface OnDataChangedListener {
        void onDataChanged();
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



