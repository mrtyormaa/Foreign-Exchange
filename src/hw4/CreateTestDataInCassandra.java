package hw4;// Created by mrtyormaa on 11/4/15.

import decisiontree.FileNames;

import java.io.*;
import java.util.ArrayList;

public class CreateTestDataInCassandra {

    public static void main(String[] args) {

        String masterPath = "/Users/mrtyormaa/Downloads/test/test";
        FileNames files = new FileNames();
        ArrayList<File> fileNames = files.getAllFileNames(masterPath, "predict");

        try {
            String sql;
            FileWriter foutstream = new FileWriter("/Users/mrtyormaa/IdeaProjects/ForexData/src/hw4/testqueries.txt");
            BufferedWriter out = new BufferedWriter(foutstream);
            for (File file : fileNames) {
                sql = "COPY bigdata.testdata (datetime, minbid, minask, mindelta, maxbid, maxask, maxdelta, meanbid, meanask, meandelta, askrirectionality, biddirectionality) FROM '" + file + "';";
                out.write(sql + "\n");
            }
            out.close();

            //String sysEnvStr = System.getenv("CASSANDRA_HOME");
            Process p = Runtime.getRuntime().exec("/Users/mrtyormaa/Documents/dsc-cassandra-2.1.11/bin/cqlsh -k bigdata -f /Users/mrtyormaa/IdeaProjects/ForexData/src/hw4/testqueries.txt");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String s = "";
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println("Std ERROR : " + s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
