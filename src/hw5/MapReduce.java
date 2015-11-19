package hw5;// Created by mrtyormaa on 11/19/15.

import java.io.IOException;
import java.util.*;

import decisiontree.CreateDT;
import decisiontree.DecisionTree;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;


public class MapReduce {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "mapReduceRandomForest");
        job.setJarByClass(MapReduce.class);
        job.setMapperClass(RandomForestMap.class);
        job.setCombinerClass(RandomForestReduce.class);
        job.setReducerClass(RandomForestReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class RandomForestMap extends Mapper<Object, Text, Text, Text> {

        public void map(Object key, Text value, Context context) {

            int totalFeatures = 9;
            int numberOfFeaturesPerDT = 3;
            ArrayList<int[]> allDTFeatures = new ArrayList<>();
            int[] randomFeatures = getRandom(numberOfFeaturesPerDT, totalFeatures);

            // get a decision tree.
            // It fetches data from Cassandra
            DecisionTree decisionTree = CreateDT.getCassandraDT(randomFeatures);
            allDTFeatures.add(randomFeatures);

            //Serialize Data so that reducer can use it later.
            Text serializedTree = new Text(Serialize.serializeTree(decisionTree));

            Text treeText = new Text();
            treeText.set(serializedTree);

            Text treeId = new Text();
            treeId.set(decisionTree.hashCode() + "");

            try {
                context.write(treeId, treeText);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static class RandomForestReduce extends Reducer<Text, Text, Text, Text> {

        //Reducer for Random Forest.
        public void reduce(Text key, Iterable<Text> values, Reducer.Context context) throws IOException, InterruptedException {
            StringBuilder forest = new StringBuilder();

            for (Text val : values)
                forest.append(val.toString()).append("\n");

            WriteDataToCassandra(forest.toString());
            context.write(key, new Text(forest.toString()));
        }
    }

    public static void WriteDataToCassandra(String serializedTree) {
        Cluster cluster;
        Session session;

        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        session = cluster.connect("mapreducedata");
        String query = "INSERT INTO Tree (tree) VALUES ('" + serializedTree + "');";
        session.execute(query);

        // Clean up the connection by closing it
        cluster.close();
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
