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
    private TextView tvDictance, tvSteps;
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
        TextView tvTarget = view.findViewById(R.id.tvTarget);
        tvDictance = view.findViewById(R.id.tvDist);
        tvSteps = view.findViewById(R.id.tvSteps);


        dbManager = new DBManager((MainActivity) this.getContext());
        progressBar = view.findViewById(R.id.progressBar);


        int target = dbManager.getTarget();
        progressBar.setMax(target);
        if (target > 0){
            tvTarget.setText(String.valueOf(target));
        }
        swipeRefresh = view.findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(this);

        onRefresh();

        return view;
    }

    @Override
    public void onRefresh() {
        Log.d("RRR", "Refresh");
        dbManager.openDB();
        int dist = dbManager.readLastFromDB();
        tvDictance.setText(String.valueOf(dist));
        tvSteps.setText(String.valueOf((int) (dist * 1.4)));
        progressBar.setProgress(dist,true);
        dbManager.closeDB();
        swipeRefresh.setRefreshing(false);

    }
}