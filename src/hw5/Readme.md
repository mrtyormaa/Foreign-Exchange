## Homework 5
Due to earlier designs of the application, this homework requires very few changes.

I have created two classes as follows:

1. Serialize: Used for serializing the data using gson.
2. MapReduce: This class uses the previous classes CreateDT and etc. It has the following sub classes.
    - RandomForestMap: This classes maps the creation of different decision trees.
    - RandomForestReduce: This class reduces the results of the mapper and stores the entire collection of trees to create the new random forest.
  
  Finally, I store the random forest in derialzed form to cassandra for future use.
