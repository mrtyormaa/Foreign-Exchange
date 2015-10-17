package data.prep;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Created by mrtyormaa on 9/16/15.
public class WriteData {
    private Features features = new Features();
    private Labels labels = new Labels();

    private Features offsetFeature = new Features();
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public void writeData(String currency, RawData data, RawData offsetData, String fileName) {
        calculateFeatures(currency, data.ask, data.bid);
        calculateOffsetFeatures(currency, offsetData.ask, offsetData.bid);
        calculateLabels();
        writeToFile(fileName);
    }

    private void calculateOffsetFeatures(String currency, ArrayList<Float> ask, ArrayList<Float> bid) {
        offsetFeature.setCurrency(currency);
        offsetFeature.setMinAsk(Calculations.getMin(ask));
        offsetFeature.setMinBid(Calculations.getMin(bid));
        offsetFeature.setMaxAsk(Calculations.getMax(ask));
        offsetFeature.setMaxBid(Calculations.getMax(bid));
        offsetFeature.setMeanAsk(Calculations.getMean(ask));
        offsetFeature.setMeanBid(Calculations.getMean(bid));

        ArrayList<Float> delta = Calculations.getDelta(ask, bid);
        offsetFeature.setMinDelta(Calculations.getMin(delta));
        offsetFeature.setMaxDelta(Calculations.getMax(delta));
        offsetFeature.setMeanDelta(Calculations.getMean(delta));
    }

    private void writeToFile(String fileName) {
        FileWriter fileWriter = null;
        fileName = fileName + "-dataprep.csv";

        try {
            fileWriter = new FileWriter(fileName, true);
            fileWriter.append(String.valueOf(features.getCurrency()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(features.getMinBid()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(features.getMinAsk()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(features.getMinDelta()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(features.getMaxBid()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(features.getMaxAsk()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(features.getMaxDelta()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(features.getMeanBid()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(features.getMeanAsk()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(features.getMeanDelta()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(labels.getBidDirectionality()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(labels.getAskDirectionality()));
            fileWriter.append(NEW_LINE_SEPARATOR);
        } catch (Exception e) {
            System.out.println("Error while writing to csv file !!!");
            e.printStackTrace();

        } finally {
            try {
                assert fileWriter != null;
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }

    private void calculateFeatures(String currency, ArrayList<Float> ask, ArrayList<Float> bid) {
        features.setCurrency(currency);
        features.setMinAsk(Calculations.getMin(ask));
        features.setMinBid(Calculations.getMin(bid));
        features.setMaxAsk(Calculations.getMax(ask));
        features.setMaxBid(Calculations.getMax(bid));
        features.setMeanAsk(Calculations.getMean(ask));
        features.setMeanBid(Calculations.getMean(bid));

        ArrayList<Float> delta = Calculations.getDelta(ask, bid);
        features.setMinDelta(Calculations.getMin(delta));
        features.setMaxDelta(Calculations.getMax(delta));
        features.setMeanDelta(Calculations.getMean(delta));

    }

    private void calculateLabels() {
        if (this.offsetFeature == null)
            return;
        if ((this.offsetFeature.getMeanBid() - features.getMeanBid()) > 0)
            labels.setBidDirectionality(1);
        else
            labels.setBidDirectionality(0);
        if ((this.offsetFeature.getMeanAsk() - features.getMeanBid()) > 0)
            labels.setAskDirectionality(1);
        else
            labels.setAskDirectionality(0);
    }
}
