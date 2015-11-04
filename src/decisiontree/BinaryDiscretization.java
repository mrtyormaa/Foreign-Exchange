package decisiontree;// Created by mrtyormaa on 10/1/15.

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BinaryDiscretization implements Discretize {

    private static double meanbid = 0;
    private static double meanask = 0;
    private static double meandelta = 0;
    private static double minbid = 0;
    private static double minask = 0;
    private static double mindelta = 0;
    private static double maxbid = 0;
    private static double maxask = 0;
    private static double maxdelta = 0;

    public BinaryDiscretization(File file) {
        BufferedReader fileBuffer = null;
        int counter = 0;
        try {
            String eachLine;
            fileBuffer = new BufferedReader(new FileReader(file));

            // Read it line by line
            while ((eachLine = fileBuffer.readLine()) != null) {
                String[] splitData = eachLine.split("\\s*,\\s*");
                assert splitData[0] != null;
                meanbid = meanbid + Float.valueOf(splitData[1]);
                meanask = meanask + Float.valueOf(splitData[2]);
                meandelta = meandelta + Float.valueOf(splitData[3]);
                minbid = minbid + Float.valueOf(splitData[4]);
                minask = minask + Float.valueOf(splitData[5]);
                mindelta = mindelta + Float.valueOf(splitData[6]);
                maxbid = maxbid + Float.valueOf(splitData[7]);
                maxask = maxask + Float.valueOf(splitData[8]);
                maxdelta = maxdelta + Float.valueOf(splitData[9]);
                counter++;
            }
            meanbid = meanbid / counter;
            meanask = meanask / counter;
            meandelta = meandelta / counter;
            minbid = minbid / counter;
            minask = minask / counter;
            mindelta = mindelta / counter;
            maxbid = maxbid / counter;
            maxask = maxask / counter;
            maxdelta = maxdelta / counter;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileBuffer != null) fileBuffer.close();
            } catch (IOException fileReadException) {
                fileReadException.printStackTrace();
            }
        }
    }

    public BinaryDiscretization() {
        int counter = 0;

        Cluster cluster;
        Session session;

        // Connect to the cluster and keyspace "demo"
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect("bigdata");

        ResultSet results = session.execute("SELECT * FROM trainingdata");
        String[] allAttributes = new String[]{"minbid", "minask", "mindelta", "maxbid", "maxask", "maxdelta", "meanbid", "meanask", "meandelta"};
        String[] splitData = new String[11];

        DecisionTree decisionTree = new DecisionTree();
        decisionTree.setAttributes(allAttributes);
        for (Row row : results) {
            for (int i = 0; i < 10; i++) {
                splitData[i] = row.getString(allAttributes[i]);
            }
            splitData[10] = row.getString("askrirectionality");
            meanbid = meanbid + Float.valueOf(splitData[1]);
            meanask = meanask + Float.valueOf(splitData[2]);
            meandelta = meandelta + Float.valueOf(splitData[3]);
            minbid = minbid + Float.valueOf(splitData[4]);
            minask = minask + Float.valueOf(splitData[5]);
            mindelta = mindelta + Float.valueOf(splitData[6]);
            maxbid = maxbid + Float.valueOf(splitData[7]);
            maxask = maxask + Float.valueOf(splitData[8]);
            maxdelta = maxdelta + Float.valueOf(splitData[9]);
            counter++;
        }
        meanbid = meanbid / counter;
        meanask = meanask / counter;
        meandelta = meandelta / counter;
        minbid = minbid / counter;
        minask = minask / counter;
        mindelta = mindelta / counter;
        maxbid = maxbid / counter;
        maxask = maxask / counter;
        maxdelta = maxdelta / counter;

        // Clean up the connection by closing it
        cluster.close();
    }

    @Override
    public String discretizeData(String attributeName, float attributeValue) {
        switch (attributeName) {
            case "meanbid":
                if (attributeValue > meanbid)
                    return "high";
                return "low";
            case "meanask":
                if (attributeValue > meanask)
                    return "high12";
                return "low12";
            case "meandelta":
                if (attributeValue > meandelta)
                    return "high13";
                return "low13";
            case "minbid":
                if (attributeValue > minbid)
                    return "high14";
                return "low14";
            case "minask":
                if (attributeValue > minask)
                    return "high15";
                return "low15";
            case "mindelta":
                if (attributeValue > mindelta)
                    return "high16";
                return "low16";
            case "maxbid":
                if (attributeValue > maxbid)
                    return "high17";
                return "low17";
            case "maxask":
                if (attributeValue > maxask)
                    return "high18";
                return "low18";
            case "maxdelta":
                if (attributeValue > maxdelta)
                    return "high19";
                return "low19";
            default:
                return "newlow";
        }
    }
}
