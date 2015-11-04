package hw4;// Created by mrtyormaa on 11/4/15.

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import decisiontree.FileNames;

import java.io.File;
import java.util.ArrayList;

public class CopyCSVToCassandra {
    public static void main(String[] args) {

        Cluster cluster;
        Session session;

        // Connect to the cluster and keyspace "demo"
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect("bigdata");

        String masterPath = "/Users/mrtyormaa/Downloads/test/test";
        FileNames fileNames = new FileNames();
        ArrayList<File> files = fileNames.getAllFileNames(masterPath, "dataprep");

        for (File f: files) {
            String cmd = "COPY bigdata.trainingdata (datetime, minbid, minask, mindelta, maxbid, maxask, maxdelta, meanbid, meanask, meandelta, askrirectionality, biddirectionality) FROM '" + f + "';";
            session.execute(cmd);
        }

//        // Insert one record into the users table
//        session.execute("INSERT INTO traindata (datetime, age, city, email, firstname) VALUES ('Jones', 35, 'Austin', 'bob@example.com', 'Bob')");
//
//        // Use select to get the user we just entered
//        ResultSet results = session.execute("SELECT * FROM users WHERE lastname='Jones'");
//        for (Row row : results) {
//            System.out.format("%s %d\n", row.getString("firstname"), row.getInt("age"));
//        }
//
//        // Update the same user with a new age
//        session.execute("update users set age = 36 where lastname = 'Jones'");
//
//        // Select and show the change
//        results = session.execute("select * from users where lastname='Jones'");
//        for (Row row : results) {
//            System.out.format("%s %d\n", row.getString("firstname"), row.getInt("age"));
//        }
//
//        // Delete the user from the users table
//        session.execute("DELETE FROM users WHERE lastname = 'Jones'");
//
//        // Show that the user is gone
//        results = session.execute("SELECT * FROM users");
//        for (Row row : results) {
//            System.out.format("%s %d %s %s %s\n", row.getString("lastname"), row.getInt("age"),  row.getString("city"), row.getString("email"), row.getString("firstname"));
//        }
//
//        // Clean up the connection by closing it
//        cluster.close();
    }
}
