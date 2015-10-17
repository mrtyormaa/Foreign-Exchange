package decisiontree;// Created by mrtyormaa on 10/1/15.

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateDT {

    public static DecisionTree getDT(int[] features){
        System.out.println("Creating DecisionTree");

        String masterPath = "/Users/mrtyormaa/Downloads/test/test";
        FileNames fileNames = new FileNames();
        ArrayList<File> files = fileNames.getAllFileNames(masterPath, "dataprep");

        //Discretize data based on the first file - Mean of the values
        BinaryDiscretization bd = new BinaryDiscretization(files.get(0));

        System.out.println("Training Datasets...");
        DecisionTree decisionTree = trainData(files, bd, features);
        System.out.println("Training Complete...");
        files.clear();
        return decisionTree;
    }

    public static void main(String[] args) throws UnknownDecisionException, BadDecisionException {
        System.out.println("Creating DecisionTree");
        int[] features = new int[9];
        for (int i = 0; i < 9; i++) {
            features[i] = i+1;
        }
        String masterPath = "/Users/mrtyormaa/Downloads/test/test";
        FileNames fileNames = new FileNames();
        ArrayList<File> files = fileNames.getAllFileNames(masterPath, "dataprep");

        //Discretize data based on the first file - Mean of the values
        BinaryDiscretization bd = new BinaryDiscretization(files.get(0));

        System.out.println("Training Datasets...");
        DecisionTree decisionTree = trainData(files, bd, features);
        System.out.println("Training Complete...");
        files.clear();
        files = fileNames.getAllFileNames(masterPath, "predict");
        //view results
        System.out.println("Prediction Started...");
        for (File f : files) {
            System.out.println("Prediction for File: " + f.toString());
            if (decisionTree != null)
                predictData(f, decisionTree, bd, features);
        }

    }

    private static void predictData(File file, DecisionTree decisionTree, BinaryDiscretization bd, int[] features) {
        BufferedReader fileBuffer = null;
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
                Map<String, String> record = formatRecord(splitData, bd, features);
                total++;
                if(decisionTree.apply(record) == splitData[10].equalsIgnoreCase("1"))
                    success++;
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
        System.out.println("Success rate: " + success / total * 100);
    }

    private static DecisionTree trainData(ArrayList<File> files, BinaryDiscretization bd, int[] features) {
        BufferedReader fileBuffer = null;

        DecisionTree decisionTree = new DecisionTree();
        decisionTree.setAttributes(new String[]{"minbid", "minask", "mindelta", "maxbid", "maxask", "maxdelta", "meanbid", "meanask", "meandelta"});
        for (File file : files) {
            System.out.println("Training File: " + file.toString());
            try {
                String eachLine;
                fileBuffer = new BufferedReader(new FileReader(file));

                // Read it line by line
                while ((eachLine = fileBuffer.readLine()) != null) {
                    //System.out.println("Raw CSV data: " + eachLine);
                    String[] splitData = eachLine.split("\\s*,\\s*");
                    assert splitData[0] != null;
                    Map<String, String> record = formatRecord(splitData, bd, features);

                    decisionTree.addRecord(record, splitData[10].equalsIgnoreCase("1"));
                }
            } catch (IOException | UnknownDecisionException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileBuffer != null) fileBuffer.close();
                } catch (IOException fileReadException) {
                    fileReadException.printStackTrace();
                }
            }
        }
        decisionTree.compile();
        return decisionTree; //to shut up compiler
    }

    public static Map<String, String> formatRecord(String[] splitData, BinaryDiscretization bd, int[] features) {
        Map<String, String> record = new HashMap<>();
        Arrays.sort(features);
        for (int i = 1; i < splitData.length; i++) {
            if(BinarySearch.indexOf(features, i) == -1)
                continue;

            float data = Float.valueOf(splitData[i]);
            switch (i) {
                case 1:
                    record.put("minbid", bd.discretizeData("minbid", data));
                    break;
                case 2:
                    record.put("minask", bd.discretizeData("minask", data));
                    break;
                case 3:
                    record.put("mindelta", bd.discretizeData("mindelta", data));
                    break;
                case 4:
                    record.put("maxbid", bd.discretizeData("maxbid", data));
                    break;
                case 5:
                    record.put("maxask", bd.discretizeData("maxask", data));
                    break;
                case 6:
                    record.put("maxdelta", bd.discretizeData("maxdelta", data));
                    break;
                case 7:
                    record.put("meanbid", bd.discretizeData("meanbid", data));
                    break;
                case 8:
                    record.put("meanask", bd.discretizeData("meanask", data));
                    break;
                case 9:
                    record.put("meandelta", bd.discretizeData("meandelta", data));
                    break;
            }
        }
        //System.out.println(record.toString());
        return record;
    }

}
