package com.example.step_counter;

import android.content.Context;
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
    private Button bSetTarget, bSetFakeDB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        dbManager = new DBManager((MainActivity) this.getContext());
        bSetTarget = view.findViewById(R.id.bSetTarget);
        edTarget = view.findViewById(R.id.edDayTarget);
        bSetFakeDB = view.findViewById(R.id.bSetFake);


        int target = dbManager.getTarget();
        if (target > 0){
            edTarget.setText(String.valueOf(target));
        }







        bSetTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RRR", "new target set: " + edTarget.getText());
                dbManager.setTarget(Integer.parseInt(edTarget.getText().toString()));
            }
        });

        bSetFakeDB.setOnClickListener(v -> {
            dbManager.openDB();
            dbManager.clearDB();
            dbManager.insertToDB("01/01/24", 160);
            dbManager.insertToDB("02/01/24", 453);
            dbManager.insertToDB("03/01/24", 564);
            dbManager.insertToDB("04/01/24", 5647);
            dbManager.insertToDB("05/01/24", 4645);
            dbManager.insertToDB("06/01/24", 9432);
            dbManager.insertToDB("07/01/24", 45);
            dbManager.insertToDB("08/01/24", 5423);
            dbManager.insertToDB("09/01/24", 3134);
            dbManager.insertToDB("10/01/24", 5435);
            dbManager.insertToDB("11/01/24", 424);
            dbManager.insertToDB("12/01/24", 7654);
            dbManager.insertToDB("13/01/24", 4532);
            dbManager.insertToDB("14/01/24", 5643);
            dbManager.insertToDB("15/01/24", 5432);
            dbManager.insertToDB("16/01/24", 654);
            dbManager.insertToDB("17/01/24", 11345);
            dbManager.insertToDB("18/01/24", 6234);
            dbManager.closeDB();
        });

        return view;
    }
}