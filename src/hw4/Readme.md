##HomeWork 4

Creating the Cassandra Database and tables were done by the following commands
```
CREATE KEYSPACE bigdata WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};

CREATE TABLE trainingdata (
           datetime text,
           minbid text,
           minask text,
           mindelta text,
           maxbid text,
           maxask text,
           maxdelta text,
           meanbid text,
           meanask text,
           meandelta text,
           askrirectionality text,
           biddirectionality text,
           primary key (datetime));

CREATE TABLE testdata (
           datetime text,
           minbid text,
           minask text,
           mindelta text,
           maxbid text,
           maxask text,
           maxdelta text,
           meanbid text,
           meanask text,
           meandelta text,
           askrirectionality text,
           primary key (datetime));
```

Data was populated using the following files.
 - **CreateTrainingDatainCassandra** : Populates the training data.
 - **CreateTestDataInCassandra** : Populates test Data.

The main execution is done from 
 - **CreateForestUsingCassandra** : It is similar to the previous assignment. Only minor changes have been made to replace reading from files to reading from localhost Cassandra database.

The change from reading from files to reading from Cassandra database was pretty simple.
There were a few changes in the files
 - decisiontree.CreateDT
  - Added two new functions 
   - *getCassandraDT* : this is used in place of the existing funtion *getDT*
   - *trainCassandraData* : this is used in place of the existing funtion *trainData*
 - decisiontree.BinaryDiscretization
  - New *constructor* to read from Cassandra instead of files.

###Statistics
- Decision tree Efficiency : 50.808%
- Random Forest Efficiency : 53.6732%

The output can be verified in the console. I have used logger to display all calculations i.e information gain, entropy, fetaures selected etc... 


###Random Forest Properties
There are total of 9 features. I am selecting 3 features at random for each decision tree.

The forest properties can be changed as per requirement.
The default values are as below:
 - totalFeatures = 9; // This part is hardcoded. This should not be changed. This is the total number of available fetures.
 - numberOfFeaturesPerDT = 3; // This can be changed anywhere between 1 - 9. This is the number of features per decision tree.
 - numberOfTrees = 500; // Total number of Decision Trees in the Forest. Can be Changed.
