#DecisonTree

###Design

#####Interfaces
The two main things to be considered for the task of creating a Decison Tree is the following:
- Algorithm for the Decision Tree creation [ID3 for this project]
- Discretization Process
    - A lot of learning algorithms require a set discrete data to train the models.

These two things have been implemented as an interface so that they can be changed at any point of time.

#####File Structure
 - src.decisiontree
    - Algorithm [Interface: Can be used to plug some other interface later on.]
    - Attribute [This class houses the features to be used for the decision tree]
    - BadDecisionException [Special exception when unknown values of data is encountered during prediction]
    - BinaryDiscretization [Hardcoded discretization of the featuress]
    - Decisions [Attribute Values]
    - Decision Tree [The tre structure for the decision tree]
    - Discretize [Interface: Can be used to plug other discretization methods]
    - Filenames [Recurses through folders and fetches desired files]
    - ID3Algorithm [Standard ID3 algorithm for decision tree creation]
    - Records [Instance of each record from the processed data]
    - Test [It runs the program]
    - UnknownDecisionException[Special exception when unknown values of data is encountered during prediction]


###Program Flow:
- The prepped data is read.
- Continuous data is changed to discrete values.
- Decision Tree Training: Records are added to the decision tree. [ trainData() ]
- Prediction: When the tree is ready, the data is fed into the tree to check prediction. [ predictData() ]

###Test Cases:
I have tested the data for duration 2010-2015 and currency "AUD-JPY"

###Improvements:
- Need to improve the discretization process.
    - Presently, it is hardcoded. I am planning to implement K-Means Discretization. This should be fairly easy due to the present architecture.
- The predictions are printed to the console and are not being written to an output file. It is a small task and will be completed for homework 6.

The algorithm has been based on the following open source project. Appropriate changes and modifications have been made to cater to this homework

https://github.com/saebyn/java-decision-tree

