package com.example.step_counter;

import java.util.ArrayList;

public class Root {
    public String name;
    public ArrayList<Stat> stat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Stat> getStat() {
        return stat;
    }

    public void setStat(ArrayList<Stat> stat) {
        this.stat = stat;
    }

    public Root(String name, ArrayList<Stat> stat) {
        this.name = name;
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "Root{" +
                "name='" + name + '\'' +
                ", stat=" + stat +
                '}';
    }
}
