package com.example.step_counter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class FragmentStat extends Fragment {


    private DBManager dbManager;
    private ArrayList<UserStatModel> userStatModels = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView tvAverageDist, tvTargetAcheaving;
    private float averagedist = 0, targetach = 0, target;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat, container, false);
        dbManager = new DBManager((MainActivity) this.getContext());
        tvAverageDist = view.findViewById(R.id.tvAverageDistance);
        tvTargetAcheaving = view.findViewById(R.id.tvTargetAcheavment);
        recyclerView = view.findViewById(R.id.rvStatistic);

        target = dbManager.getTarget();

        SetUpUserStatModels();

        tvAverageDist.setText(String.valueOf((int) averagedist));
        tvTargetAcheaving.setText(String.valueOf((int) targetach));

        return view;
    }

    public void SetUpUserStatModels(){

        dbManager.openDB();
        List<Pair<String, Integer>> stats = dbManager.readFromDB();
        for (Pair<String, Integer> data : stats){
            userStatModels.add(new UserStatModel(data.first, data.second));
            averagedist += data.second;
            if (data.second >= target)
                targetach++;
        }
        dbManager.closeDB();

        averagedist /= stats.size();
        targetach /= stats.size();
        targetach *= 100;

        USRecycleViewAdapter adapter = new USRecycleViewAdapter(this.getContext(), userStatModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}