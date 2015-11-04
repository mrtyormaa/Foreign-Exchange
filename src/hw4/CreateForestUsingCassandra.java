package hw4;// Created by mrtyormaa on 11/4/15.

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import decisiontree.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CreateForestUsingCassandra {
    public static void main(String[] args) {
        int totalFeatures = 9;
        int numberOfFeaturesPerDT = 3;
        int numberOfTrees = 3;
        ArrayList<DecisionTree> randomForest = new ArrayList<>();
        ArrayList<int[]> allDTFeatures = new ArrayList<>();

        // Run Training Data
        for (int i = 0; i < numberOfTrees; i++) {
            // get three random features from 9 features for each tree
            int[] randomFeatures = getRandom(numberOfFeaturesPerDT, totalFeatures);

            //get a decision tree and add to the forest
            randomForest.add(CreateDT.getCassandraDT(randomFeatures));
            allDTFeatures.add(randomFeatures);
        }

        //Discretize data based on the first file - Mean of the values
        BinaryDiscretization bd = new BinaryDiscretization();

        // Run CreateTrainingDatainCassandra Data to check accuracy
        System.out.println("Prediction Started...");
        predictData(randomForest, bd, allDTFeatures);
    }

    private static void predictData(ArrayList<DecisionTree> forest, BinaryDiscretization bd, ArrayList<int[]> allDTFeatures) {
        int trueCount = 0;
        int falseCount = 0;
        double success = 0;
        double total = 0;
        Cluster cluster;
        Session session;

        try {
            // Connect to the cluster and keyspace "demo"
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            session = cluster.connect("bigdata");

            ResultSet results = session.execute("SELECT * FROM testdata");
            String[] allAttributes = new String[]{"minbid", "minask", "mindelta", "maxbid", "maxask", "maxdelta", "meanbid", "meanask", "meandelta"};
            String[] splitData = new String[11];

            DecisionTree decisionTree = new DecisionTree();
            decisionTree.setAttributes(allAttributes);
            for (Row row : results) {
                for (int i = 0; i < 10; i++) {
                    splitData[i] = row.getString(allAttributes[i]);
                }
                splitData[10] = row.getString("askrirectionality");
                total++;
                for (int i = 0; i < forest.size(); i++) {
                    Map<String, String> record = CreateDT.formatRecord(splitData, bd, allDTFeatures.get(i));
                    if (forest.get(i).apply(record))
                        trueCount++;
                    else falseCount++;
                }
                if (trueCount >= falseCount) {
                    if (splitData[10].equalsIgnoreCase("1"))
                        success++;
                } else {
                    if (splitData[10].equalsIgnoreCase("0"))
                        success++;
                }
            }
            // Clean up the connection by closing it
            cluster.close();
        } catch (BadDecisionException e) {
            e.printStackTrace();
        }

        System.out.println("Success rate of the random Forest: " + success / total * 100);
    }

    private static int[] getRandom(int numberOfFeaturesPerDT, int totalFeatures) {
        List<Integer> range = new ArrayList<>();
        for (int i = 1; i <= totalFeatures; i++) {
            range.add(i);
        }
        Collections.shuffle(range);
        int[] output = new int[numberOfFeaturesPerDT];
        for (int i = 0; i < numberOfFeaturesPerDT; i++)
            output[i] = range.get(i);
        return output;
    }
}
