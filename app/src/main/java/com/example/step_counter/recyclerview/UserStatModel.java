package com.example.step_counter.recyclerview;

public class UserStatModel {
    int progress;
    String date;

    public int getProgress() {
        return progress;
    }

    public String getDate() {
        return date;
    }

    public UserStatModel(int progress, String date) {
        this.progress = progress;
        this.date = date;
    }
}
