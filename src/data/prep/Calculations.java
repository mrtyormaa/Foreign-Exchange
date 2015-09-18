package data.prep;

import java.util.ArrayList;
import java.util.Collections;

// Created by mrtyormaa on 9/17/15.
public class Calculations {
    public static float getMin(ArrayList<Float> data) {
        // get Minimum Calculations
        if (data == null || data.isEmpty())
            return 0;
        return Collections.min(data);
    }

    public static float getMax(ArrayList<Float> data) {
        // basic Calculations
        if (data == null || data.isEmpty())
            return 0;
        return Collections.max(data);
    }

    public static float getMean(ArrayList<Float> data) {
        // 'average' is undefined if there are no elements in the list.
        if (data == null || data.isEmpty())
            return 0;
        // Calculate the summation of the elements in the list
        double sum = 0;
        int n = data.size();
        // Iterating manually is faster than using an enhanced for loop.
        for (Float aData : data) sum += aData;
        // We don't want to perform an integer division, so the cast is mandatory.
        return (float) sum / n;
    }

    public static ArrayList<Float> getDelta(ArrayList<Float> ask, ArrayList<Float> bid) {
        ArrayList<Float> delta = new ArrayList<>();
        assert ask.size() == bid.size();
        for (int i = 0; i < ask.size(); i++) {
            delta.add(ask.get(i) - bid.get(i));
        }
        return delta;
    }
}
