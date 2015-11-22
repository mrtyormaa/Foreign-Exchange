##HomeWork - 6

This project has been built using sbt.
To run this project please do the following:
- Make sure cassandra is running in localhost.
- Make sure the cassandra keyspace, table names and the label and feature names are same as shown in the code.
- Go to the project directory.
- Run 'sbt'
- Run 'run'

The application reads the data from the cassandra tables created earlier. It then uses the spark-mllib to create random forest and predict data.
spark-mllib has the functionality to split training and testdata from a single source. However, as we have already split the data in our earlier assignments I am using the old data instead of splitting the data here. This will save some running time.