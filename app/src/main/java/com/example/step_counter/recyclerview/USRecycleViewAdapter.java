package com.example.step_counter.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull USRecycleViewAdapter.MyViewHolder holder, int position) {
        int target = dbManager.getTarget();
        int progress = userStatModels.get(position).getProgress();
        holder.tvDate.setText(userStatModels.get(position).getDate());
        holder.tvProg.setText(String.valueOf(userStatModels.get(position).getProgress()));
        holder.progressBar.setMax(target);
        holder.progressBar.setProgress(progress);
        ProgressBarAnimation animation = new ProgressBarAnimation(holder.progressBar, 0, progress);
        animation.setDuration(1500);
        holder.progressBar.setAnimation(animation);
        if (progress >= target)
            //holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(200, 30, 60)));
            holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(218, 59, 59)));
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
    class ProgressBarAnimation extends Animation {
        private ProgressBar progressBar;
        private float from;
        private float  to;

        public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }

    }
}
