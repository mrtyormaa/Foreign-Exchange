package data.prep;// Created by mrtyormaa on 9/17/15.

import java.util.ArrayList;

public class RawData {
    public ArrayList<java.util.Date> time;
    public String currency;
    public ArrayList<Float> bid;
    public ArrayList<Float> ask;

    public RawData() {
        this.time = new ArrayList<>();
        this.currency = "";
        this.bid = new ArrayList<>();
        this.ask = new ArrayList<>();
    }
}
