package org.example.controller;

import com.google.gson.annotations.Expose;

public class WeightDateTuple {

    @Expose
    private final double weight;
    @Expose
    private final String date;

    public WeightDateTuple(double weight, String date) {
        this.weight = weight;
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public String getDate() {
        return date;
    }

}
