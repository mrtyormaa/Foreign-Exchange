package decisiontree;// Created by mrtyormaa on 10/1/15.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws UnknownDecisionException, BadDecisionException {
        System.out.println("testing....");

        String masterPath = "/Users/mrtyormaa/Downloads/test";
        FileNames fileNames = new FileNames();
        ArrayList<File> files = fileNames.getAllFileNames(masterPath, "dataprep");
        DecisionTree decisionTree = new DecisionTree();
        for (File f : files) {
            decisionTree = trainData(f);
        }

        files = fileNames.getAllFileNames(masterPath, "predict");
        //view results
        for (File f : files) {
            if (decisionTree != null)
                predictData(f, decisionTree);
        }
    }

    private static String fileName;

    private static void predictData(File file, DecisionTree decisionTree) {
        BufferedReader fileBuffer = null;
        String[] tokens = file.toString().split("\\.(?=[^\\.]+$)");
        fileName = tokens[0];

        decisionTree.setAttributes(new String[]{"minbid", "minask", "mindelta", "maxbid", "maxask", "maxdelta", "meanbid", "meanask", "meandelta"});

        try {
            String eachLine;
            fileBuffer = new BufferedReader(new FileReader(file));

            // Read it line by line
            while ((eachLine = fileBuffer.readLine()) != null) {
                //System.out.println("Raw CSV data: " + eachLine);
                String[] splitData = eachLine.split("\\s*,\\s*");
                assert splitData[0] != null;
                Map<String, String> record = formatRecord(splitData);
                System.out.print(decisionTree.apply(record));
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
    }

    private static DecisionTree trainData(File file) {
        BufferedReader fileBuffer = null;

        DecisionTree decisionTree = new DecisionTree();
        decisionTree.setAttributes(new String[]{"minbid", "minask", "mindelta", "maxbid", "maxask", "maxdelta", "meanbid", "meanask", "meandelta"});

        try {
            String eachLine;
            fileBuffer = new BufferedReader(new FileReader(file));

            // Read it line by line
            while ((eachLine = fileBuffer.readLine()) != null) {
                //System.out.println("Raw CSV data: " + eachLine);
                String[] splitData = eachLine.split("\\s*,\\s*");
                assert splitData[0] != null;
                Map<String, String> record = formatRecord(splitData);

                decisionTree.addRecord(record, splitData[10].equalsIgnoreCase("1"));
            }
            return decisionTree;
        } catch (IOException | UnknownDecisionException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileBuffer != null) fileBuffer.close();
            } catch (IOException fileReadException) {
                fileReadException.printStackTrace();
            }
        }
        return null; //to shut up compiler
    }

    private static Map<String, String> formatRecord(String[] splitData) {
        BinaryDiscretization bd = new BinaryDiscretization();
        Map<String, String> record = new HashMap<>();
        for (int i = 1; i < splitData.length; i++) {
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
        return record;
    }
}
