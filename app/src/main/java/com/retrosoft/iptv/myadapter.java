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

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    ArrayList<model> dataholder;

    public myadapter(ArrayList<model> dataholder){
        this.dataholder = dataholder;
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.cname.setText(dataholder.get(position).getCOLUMN_NAME());
//        String firstChar = String.valueOf(dataholder.get(position).getCOLUMN_NAME().charAt(0)).trim();
//        holder.logoName.setText(firstChar);
//        Glide.with(holder.clogo.getContext()).load(dataholder.get(position).getCOLUMN_LOGO()).into(holder.clogo);

        Glide.with(holder.clogo.getContext())
                .load(dataholder.get(position).getCOLUMN_LOGO())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.cnlloading)
                .error(R.drawable.cnlloading)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Handle failure
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // Image loaded successfully
                        return false;
                    }
                })
                .into(holder.clogo);


//        Glide.with(holder.clogo.getContext())
//                .load(dataholder.get(position).getCOLUMN_LOGO())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.cnlloading)
//                .error(R.drawable.cnlloading)
//                .into(holder.clogo);



        holder.viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, Player.class);
                Player.websiteUri = dataholder.get(position).getCOLUMN_LINK();
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView cname,logoName;
        ImageView clogo;

        View viewLayout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            cname =(TextView) itemView.findViewById(R.id.cardName);
            clogo = (ImageView) itemView.findViewById(R.id.viewImage);
//            logoName = (TextView) itemView.findViewById(R.id.logoname);
            viewLayout = itemView.findViewById(R.id.viewLayout);

        }
    }
}

