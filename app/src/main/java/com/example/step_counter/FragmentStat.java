package com.example.step_counter;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.step_counter.db.DBManager;

public class FragmentStat extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private TextView tvStat;
    private SwipeRefreshLayout swipeRefresh;
    private DBManager dbManager;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat, container, false);
        tvStat = view.findViewById(R.id.tvStat);
        dbManager = new DBManager((MainActivity) this.getContext());
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(this);

        onRefresh();

        return view;
    }

    @Override
    public void onRefresh() {
        Log.d("RRR", "Refresh");
        tvStat.setText("");
        dbManager.openDB();
        for (Pair<String, Integer> data : dbManager.readFromDB()){
            tvStat.append(data + "\n");
        }
        dbManager.closeDB();
        swipeRefresh.setRefreshing(false);
    }
}