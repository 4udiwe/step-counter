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

    public UserStatModel(String date, int progress) {
        this.progress = progress;
        this.date = date;
    }
}
