package com.example.step_counter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.step_counter.db.DBManager;


public class FragmentMain extends Fragment {
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

        Log.d("RRR", view.toString());

        swipeRefresh = view.findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dbManager.openDB();
                tvDictance.setText(String.valueOf(dbManager.readLastFromDB()));
                dbManager.closeDB();
            }
        });


        return view;
    }

}