package data.prep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// Created by mrtyormaa on 9/16/15.

public class ProcessData {
    private ArrayList<File> fileNames;
    private RawData data = new RawData();
    private Queue<RawData> dataQueue = new Queue<>();

    private Date endTime = null;

    private int offSet;
    private String duration;
    private String fileName;
    private WriteData wd = new WriteData();

    public ProcessData(String inputPath, int offset, String interval) {
        this.fileNames = new ArrayList<>();
        listAllFiles(inputPath);
        this.offSet = offset;
        this.duration = interval;
    }

    //    Returns list of all csv files found in the directory
    private void listAllFiles(String inputPath) {
        File directory = new File(inputPath);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        assert fList != null;
        for (File file : fList) {
            if (file.isFile()) {
                String[] tokens = file.toString().split("\\.(?=[^\\.]+$)");
                if (tokens[1].equalsIgnoreCase("csv")) {
                    if (!(tokens[0].toLowerCase().contains("dataprep")))
                        this.fileNames.add(file);
                    //System.out.println(file.getAbsolutePath());
                }
            } else if (file.isDirectory()) {
                listAllFiles(file.getAbsolutePath());
            }
        }
    }

    public ArrayList<File> getFileNames() {
        return fileNames;
    }

    public void readAndProcessFile(File file) {
        BufferedReader fileBuffer = null;
        String[] tokens = file.toString().split("\\.(?=[^\\.]+$)");
        fileName = tokens[0];

        try {
            String eachLine;
            fileBuffer = new BufferedReader(new FileReader(file));

            // Read it line by line
            while ((eachLine = fileBuffer.readLine()) != null) {
                //System.out.println("Raw CSV data: " + eachLine);
                if (dataQueue.size() <= offSet)
                    processEachLineInterval(eachLine, true);
                processEachLineInterval(eachLine, false);
            }
            processData();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileBuffer != null) fileBuffer.close();
            } catch (IOException fileReadException) {
                fileReadException.printStackTrace();
            }
        }
    }

    // Split the line to get individual parameters
    private void processEachLineInterval(String line, boolean buildQueue) throws ParseException {
        DateTime parseTime = new DateTime();
        Calendar cal = Calendar.getInstance();
        Date temp;

        if (line != null) {
            String[] splitData = line.split("\\s*,\\s*");
            assert splitData[0] != null;
            this.data.currency = splitData[0];

            for (int i = 1; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    if (i == 1) {
                        temp = parseTime.getDate(splitData[i]);
                        if (endTime == null) {
                            cal.setTime(temp);
                            switch (duration.toLowerCase()) {
                                case "m":
                                    cal.add(Calendar.MINUTE, 1);
                                    break;
                                case "h":
                                    cal.add(Calendar.HOUR, 1);
                                    break;
                                case "d":
                                    cal.add(Calendar.HOUR, 24);
                                    break;
                                case "o":
                                    cal.add(Calendar.MONTH, 1);
                                    break;
                                case "y":
                                    cal.add(Calendar.YEAR, 1);
                                    break;
                            }
                            endTime = cal.getTime();
                            //System.out.println(endTime);
                        }
                        if (temp.after(endTime) && !buildQueue) {
                            processData();
                        } else if (temp.after(endTime) && buildQueue) {
                            dataQueue.enqueue(data);
                            reset();
                        }
                        this.data.time.add(temp);
                    }
                    if (i == 2)
                        this.data.bid.add(Float.valueOf(splitData[i]));
                    if (i == 3)
                        this.data.ask.add(Float.valueOf(splitData[i]));
                }
            }
        }
    }

    private void reset() {
        endTime = null;
        data = new RawData();
    }

    private void processData() {
        if(dataQueue.size() > offSet)
            wd.writeData(data.currency, dataQueue.dequeue(), dataQueue.fetchTail(), fileName);
        reset();
    }

}
