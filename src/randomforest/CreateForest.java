package randomforest;// Created by mrtyormaa on 10/17/15.

import decisiontree.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CreateForest {
    public static void main(String[] args) {
        int totalFeatures = 9;
        int numberOfFeaturesPerDT = 3;
        int numberOfTrees = 500;
        ArrayList<DecisionTree> randomForest = new ArrayList<>();
        ArrayList<int[]> allDTFeatures = new ArrayList<>();
        for (int i = 0; i < numberOfTrees; i++) {
            // get three random features from 9 features for each tree
            int[] randomFeatures = getRandom(numberOfFeaturesPerDT, totalFeatures);

            //get a decison tree and add to the forest
            randomForest.add(CreateDT.getDT(randomFeatures));
            allDTFeatures.add(randomFeatures);
        }

        String masterPath = "/Users/mrtyormaa/Downloads/test/test";
        FileNames fileNames = new FileNames();
        ArrayList<File> files = fileNames.getAllFileNames(masterPath, "predict");

        //Discretize data based on the first file - Mean of the values
        BinaryDiscretization bd = new BinaryDiscretization(files.get(0));


        //view results
        System.out.println("Prediction Started...");
        for (File f : files) {
            System.out.println("Prediction for File: " + f.toString());
                predictData(f, randomForest, bd, allDTFeatures);
        }
    }

    private static void predictData(File file, ArrayList<DecisionTree> forest, BinaryDiscretization bd, ArrayList<int[]> allDTFeatures) {
        BufferedReader fileBuffer = null;
        int trueCount = 0;
        int falseCount = 0;
        double success = 0;
        double total = 0;

        try {
            String eachLine;
            fileBuffer = new BufferedReader(new FileReader(file));

            // Read it line by line
            while ((eachLine = fileBuffer.readLine()) != null) {
                //System.out.println("Raw CSV data: " + eachLine);
                String[] splitData = eachLine.split("\\s*,\\s*");
                assert splitData[0] != null;
                total++;
                for (int i = 0; i < forest.size(); i++){
                    Map<String, String> record = CreateDT.formatRecord(splitData, bd, allDTFeatures.get(i));
                    if(forest.get(i).apply(record))
                        trueCount++;
                    else falseCount++;
                }
                if (trueCount >= falseCount){
                    if(splitData[10].equalsIgnoreCase("1"))
                        success++;
                }
                else {
                    if(splitData[10].equalsIgnoreCase("0"))
                        success++;
                }
            }
        } catch (IOException | BadDecisionException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileBuffer != null) fileBuffer.close();
            } catch (IOException fileReadException) {
                fileReadException.printStackTrace();
            }
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
