package com.example.step_counter;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.step_counter.db.DBManager;
import com.example.step_counter.recyclerview.USRecycleViewAdapter;
import com.example.step_counter.recyclerview.UserStatModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentStat extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    //private TextView tvStat;
    //private SwipeRefreshLayout swipeRefresh;
    private DBManager dbManager;
    private ArrayList<UserStatModel> userStatModels = new ArrayList<>();
    RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat, container, false);
        //tvStat = view.findViewById(R.id.tvStat);
        dbManager = new DBManager((MainActivity) this.getContext());
        //swipeRefresh = view.findViewById(R.id.swiperefresh);
        //swipeRefresh.setOnRefreshListener(this);

        recyclerView = view.findViewById(R.id.rvStatistic);

        SetUpUserStatModels();






        //onRefresh();

        return view;
    }

    public void SetUpUserStatModels(){
        /*
        dbManager.openDB();
        List<Pair<String, Integer>> stats = dbManager.readFromDB();
        for (Pair<String, Integer> data : stats){
            userStatModels.add(new UserStatModel(data.second, data.first));
        }
        dbManager.closeDB();
        */
        userStatModels.add(new UserStatModel("00/00/00", 100));
        userStatModels.add(new UserStatModel("01/10/10", 222));

        USRecycleViewAdapter adapter = new USRecycleViewAdapter(this.getContext(), userStatModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onRefresh() {
        Log.d("RRR", "Refresh");
        /*
        tvStat.setText("");
        dbManager.openDB();
        for (Pair<String, Integer> data : dbManager.readFromDB()){
            tvStat.append(data + "\n");
        }

         */
        //dbManager.closeDB();
        //swipeRefresh.setRefreshing(false);
    }
}