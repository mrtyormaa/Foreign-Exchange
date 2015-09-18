# Foreign-Exchange

## This program has the option of setting the following:
1. offset 
2. Interval Duration
      a. m - Minute
      b. h - Hour
      c. d - Day
      d. o - mOnth
      e. y - Year
3. folder path
      - the program recursively looks through the folder and subfolders to find all the CSV files and creates a corresponding file with "-dataprep.csv" suffix to store the processed data.

Change the above parameters in "Main.java" to get the desired result.

To be implemented
  - the features are limited to one file. This will be changed in the future version to include features from other files.
  - time alignment
  - command line arguement passing.

## File Structure 
- src
  - Main,java [Run the Main file to run the program.]
  - data.prep
    - Calculations.java [This class has all the calculations on the raw data e.g. min, max, median ask-bid spread]
    - DateTime.java [This is a custom Date and Time class]
    - Features.java [This has all the features]
    - Labels.java [This has all the labels]
    - ProcessData.java [It reads the files and processes the data to be preped]
    - Queue.java [ Custom Linked List data structure]
    - RawData.java [Just raw data]
    - WriteData.java [Writes data to appropriate file]
    - 

