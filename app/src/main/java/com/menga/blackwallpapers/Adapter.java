package com.menga.blackwallpapers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.menga.blackwallpapers.databinding.RvdesignBinding;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    List<model> list;
    Context context;

    public Adapter(List<model> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rvdesign,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
     model models=list.get(position);
   Picasso.get().load(models.getMedium()).placeholder(R.drawable.loading).into(holder.binding.image);
//        Glide.with(context).load(models.getMedium()).into(holder.binding.imageView);

        holder.binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DownloadWallpaper.class);
                intent.putExtra("key",models.getMedium());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        RvdesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RvdesignBinding.bind(itemView);

        }
    }
}
