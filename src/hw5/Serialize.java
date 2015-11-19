package hw5;// Created by mrtyormaa on 11/19/15.

import com.google.gson.Gson;
import decisiontree.DecisionTree;

public class Serialize {
    public static String serializeTree(DecisionTree tree)
    {
        Gson gson = new Gson();
        String json = gson.toJson(tree);
        return json;
    }
}
