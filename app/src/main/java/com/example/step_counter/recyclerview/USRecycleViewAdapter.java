package com.example.step_counter.recyclerview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.step_counter.MainActivity;
import com.example.step_counter.R;
import com.example.step_counter.db.DBManager;

import java.util.ArrayList;

public class USRecycleViewAdapter extends RecyclerView.Adapter<USRecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<UserStatModel> userStatModels;
    DBManager dbManager;


    public USRecycleViewAdapter (Context context, ArrayList<UserStatModel> userStatModels){
        this.context = context;
        this.userStatModels = userStatModels;
        dbManager = new DBManager((MainActivity) context);
    }



    @NonNull
    @Override
    public USRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_elem, parent, false);

        return new USRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull USRecycleViewAdapter.MyViewHolder holder, int position) {
        int target = dbManager.getTarget();
        int progress = userStatModels.get(position).getProgress();
        holder.tvDate.setText(userStatModels.get(position).getDate());
        holder.tvProg.setText(String.valueOf(userStatModels.get(position).getProgress()));
        holder.progressBar.setMax(target);
        holder.progressBar.setProgress(progress);
        if (progress >= target)
            holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
    }

    @Override
    public int getItemCount() {
        return userStatModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate, tvProg;
        ProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.rv_elem_tvDate);
            tvProg = itemView.findViewById(R.id.rv_elem_tvProgress);
            progressBar = itemView.findViewById(R.id.rv_elem_pbStat);

        }
    }
}
