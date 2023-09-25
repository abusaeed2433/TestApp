package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<EachData> list;
    private Context context;

    public DataAdapter(Context context,List<EachData> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_data_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EachData data = list.get(position);

        holder.tvDys.setText(data.getDys());
        holder.tvSys.setText(data.getSys());
        holder.tvHeartRate.setText(data.getHeart_rate());

        Glide.with(context)
                .load(data.getPhoto())
                .into(holder.ivPhoto);

        holder.itemView.setOnClickListener((View v)->{
            Toast.makeText(context, list.get(holder.getAdapterPosition()).getHeart_rate(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivPhoto;
        private final TextView tvHeartRate, tvSys, tvDys;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvHeartRate = itemView.findViewById(R.id.tvHeartRate);
            tvSys = itemView.findViewById(R.id.tvSys);
            tvDys = itemView.findViewById(R.id.tvDys);
        }
    }

}
