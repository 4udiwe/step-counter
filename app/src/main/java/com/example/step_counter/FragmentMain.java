package com.example.step_counter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.step_counter.db.DBManager;


public class FragmentMain extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private TextView tvDictance;
    private DBManager dbManager;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tvDictance = view.findViewById(R.id.tvDist);
        dbManager = new DBManager((MainActivity) this.getContext());
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setMax(dbManager.getTarget());


        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(this);
        onRefresh();


        return view;
    }

    @Override
    public void onRefresh() {
        Log.d("RRR", "Refresh");
        dbManager.openDB();
        int steps = dbManager.readLastFromDB();
        tvDictance.setText(String.valueOf(steps));
        progressBar.setProgress(steps,true);
        dbManager.closeDB();
        swipeRefresh.setRefreshing(false);

    }
}