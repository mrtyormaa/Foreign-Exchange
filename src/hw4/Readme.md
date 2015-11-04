##HomeWork 4
The change from reading from files to reading from Cassandra database was pretty simple.
There were a few changes in the files

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
