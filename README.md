# Foreign-Exchange
## Big Data Finals
Please visit (this link)[https://github.com/mrtyormaa/pubMed-text-mining/] for the finals.

##HomeWork 6
Homework 6 details can be found inside scala-random-forest folder. Please refer to the readme file inside that folder for further information.

##HomeWork 5
Homework 5 details can be found inside hw5 folder in src. Please refer to the readme file inside that folder for further information.

##HomeWork 4
Homework 4 details can be found inside hw4 folder in src. Please refer to the readme file inside that folder for further information.

##HomeWork 3
Homework 3 details can be found in the Readme file inside src/randomforest

There are a few code in src\decisiontree too.

##HomeWork 2
Homework 2 details can be found in the Readme file inside src/decisiontree

## This program has the option of setting the following:
1. Offset - Offset length of the labels [variable: offset]
2. Interval Duration [variable: duration]
      - m - Minute
      - h - Hour
      - d - Day
      - o - mOnth
      - y - Year
3. folder path [variable: testDir]
      - the program recursively looks through the folder and subfolders to find all the CSV files and creates a corresponding file with "-dataprep.csv" suffix to store the processed data.

Change the above parameters in "Main.java" to get the desired result.

## File Structure 
- src
  - Main.java [Run the Main file to run the program.]
  - data.prep
    - Calculations.java [This class has all the calculations on the raw data e.g. min, max, median ask-bid spread]
    - DateTime.java [This is a custom Date and Time class]
    - Features.java [This has all the features]
    - Labels.java [This has all the labels]
    - ProcessData.java [It reads the files and processes the data to be preped]
    - Queue.java [ Custom Linked List data structure]
    - RawData.java [Just raw data]
    - WriteData.java [Writes data to appropriate file]

# To be implemented
  - the features are limited to one file. This will be changed in the future version to include features from other files.
  - time alignment
  - command line arguement passing.
