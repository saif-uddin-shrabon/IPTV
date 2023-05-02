package com.retrosoft.iptv;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.myviewholder> {

    ArrayList<model> dataholder;

    public FavAdapter(ArrayList<model> dataholder){
        this.dataholder = dataholder;
    }
    @NonNull
    @Override
    public FavAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new FavAdapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.myviewholder holder, int position) {

        // holder.cname.setText(dataholder.get(position).getCOLUMN_NAME());
        holder.cname.setText(dataholder.get(position).getCOLUMN_fvrt());
//        String firstChar = String.valueOf(dataholder.get(position).getCOLUMN_NAME().charAt(0)).trim();
//        holder.logoName.setText(firstChar);
//        Glide.with(holder.clogo.getContext()).load(dataholder.get(position).getCOLUMN_LOGO()).into(holder.clogo);


        holder.viewLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.fav_manue, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.option1:
                                // Handle option 1 click
                                Toast.makeText(v.getContext(), "Already added to favorites list", Toast.LENGTH_SHORT).show();


                                return true;
                            case R.id.option2:
                                // Handle option 2 click
                                new dbmanager(v.getContext()).RemoveFavorite(position);
//                                 Get the cursor for the current position

                                int isNotFavorite = Integer.parseInt(dataholder.get(position).getCOLUMN_ID());
                                boolean success = new dbmanager(v.getContext()).RemoveFavorite(isNotFavorite);
                                if (success) {
                                    // Remove the item from your data list and refresh the RecyclerView
                                    Toast.makeText(v.getContext(), "Remove Favorite List", Toast.LENGTH_SHORT).show();
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


