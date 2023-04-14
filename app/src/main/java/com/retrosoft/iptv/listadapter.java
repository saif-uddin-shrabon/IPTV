package com.retrosoft.iptv;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class listadapter extends RecyclerView.Adapter<listadapter.myviewholder> {

    ArrayList<model> dataholder;

    public listadapter(ArrayList<model> dataholder){
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



//        holder.viewLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, Player.class);
//                Player.websiteUri = dataholder.get(position).getCOLUMN_LINK();
//                context.startActivity(intent);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView Llink;
        View viewLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            Llink=(TextView) itemView.findViewById(R.id.urlLink);

            viewLayout = itemView.findViewById(R.id.viewLayout);

        }
    }
}

