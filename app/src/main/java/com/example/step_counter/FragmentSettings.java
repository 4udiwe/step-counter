package com.example.step_counter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.step_counter.db.DBManager;

public class FragmentSettings extends Fragment {

    private DBManager dbManager;
    private EditText edTarget;
    private Button bSetTarget;
    private Button bClearDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        dbManager = new DBManager((MainActivity) this.getContext());
        edTarget = view.findViewById(R.id.edDayTarget);

        int target = dbManager.getTarget();
        if (target > 0){
            edTarget.setText(String.valueOf(target));
        }


        
        bSetTarget = view.findViewById(R.id.bSetTarget);
        bSetTarget.setOnClickListener(v -> {
            Log.d("RRR", "onclick : " + edTarget.getInputType());
            dbManager.setTarget(Integer.parseInt(edTarget.getText().toString()));
            Toast.makeText(this.getContext(), "New daily target: " + edTarget.getText(), Toast.LENGTH_SHORT).show();
        });

        
        return view;
    }

}