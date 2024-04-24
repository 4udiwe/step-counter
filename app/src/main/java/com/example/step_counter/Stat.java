package com.example.step_counter;

public class Stat {
    public String date;
    public int steps;

    public Stat(String date, int steps) {
        this.date = date;
        this.steps = steps;
    }
    @Override
    public String toString() {
        return "Stat{" +
                "date='" + date + '\'' +
                ", steps=" + steps +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
