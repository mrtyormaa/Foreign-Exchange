// Created by mrtyormaa on 11/19/15.

import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import com.datastax.spark.connector._

object RandomForestScala {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("RandomForest").setMaster("local[1]")
    conf.set("spark.cassandra.connection.host", "localhost")
    val sc = new SparkContext(conf)

    val trainingData = sc.cassandraTable("bigdata", "trainingdata")
    val rddTrain: RDD[LabeledPoint] = trainingData.map { row => {
      LabeledPoint(row.getString("askrirectionality").toDouble, Vectors.dense(
        row.getString("minbid").toDouble,
        row.getString("minask").toDouble,
        row.getString("mindelta").toDouble,
        row.getString("maxbid").toDouble,
        row.getString("maxask").toDouble,
        row.getString("maxdelta").toDouble,
        row.getString("meanbid").toDouble,
        row.getString("meanask").toDouble,
        row.getString("meandelta").toDouble))
    }
    }

    val testData = sc.cassandraTable("bigdata", "testdata")
    val rddTest: RDD[LabeledPoint] = testData.map { row => {
      LabeledPoint(row.getString("askrirectionality").toDouble, Vectors.dense(
        row.getString("minbid").toDouble,
        row.getString("minask").toDouble,
        row.getString("mindelta").toDouble,
        row.getString("maxbid").toDouble,
        row.getString("maxask").toDouble,
        row.getString("maxdelta").toDouble,
        row.getString("meanbid").toDouble,
        row.getString("meanask").toDouble,
        row.getString("meandelta").toDouble))
    }
    }

    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]()
    val numTrees = 5
    val featureSubsetStrategy = "auto"
    val impurity = "gini"
    val maxDepth = 4
    val maxBins = 32

    val model = RandomForest.trainClassifier(rddTrain, numClasses, categoricalFeaturesInfo,
      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)


    val labelAndPreds = rddTest.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
    println("Test Error = " + testErr)
    println("Learned classification forest model:\n" + model.toDebugString)
  }
}
