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

//        holder.Llink.setText(dataholder.get(position).getCOLUMN_LINK());
//        holder.cName.setText(dataholder.get(position).getCOLUMN_NAME());
//
//
//
////        holder.viewLayout.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//////                Context context = v.getContext();
//////                Intent intent = new Intent(context, Player_Fragment.class);
////                String link = dataholder.get(position).getCOLUMN_LINK();
////                new Player_Fragment().saveList(link);
//////                new Player_Fragment().new GetChannelsTask() {
//////                    @Override
//////                    protected void onPostExecute(List<Map<String, String>> channels) {
//////                        // pass the link and channels data to the Player activity
//////                        intent.putExtra("link", link);
//////                       intent.putExtra("channels", (Serializable) channels);
//////                        context.startActivity(intent);
//////                    }
//////                }.execute(link);
////            }
////        });
//
//        holder.viewLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String link = dataholder.get(position).getCOLUMN_LINK();
//
//                // Create a bundle to pass the link to the Player_Fragment
//                Bundle bundle = new Bundle();
//                bundle.putString("link", link);
//
// //                Create an instance of the Player_Fragment and set the bundle
//                Player_Fragment playerFragment = new Player_Fragment();
//                playerFragment.setArguments(bundle);
//                playerFragment.saveList();
//
//                // Replace the current fragment with the Player_Fragment
////                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////                transaction.replace(R.id.fragment_container, playerFragment);
////                transaction.addToBackStack(null);
////                transaction.commit();
//            }
//        });


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



