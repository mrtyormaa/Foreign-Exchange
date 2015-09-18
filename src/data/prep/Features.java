package data.prep;

// Created by mrtyormaa on 9/16/15.
public class Features {

    private String currency;
    private float meanBid;
    private float meanAsk;
    private float minBid;
    private float minAsk;
    private float maxBid;
    private float maxAsk;
    private float meanDelta;
    private float minDelta;
    private float maxDelta;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getMeanBid() {
        return meanBid;
    }

    public float getMeanAsk() {
        return meanAsk;
    }

    public float getMinBid() {
        return minBid;
    }

    public float getMaxAsk() {
        return maxAsk;
    }

    public float getMeanDelta() {
        return meanDelta;
    }

    public float getMinDelta() {
        return minDelta;
    }

    public float getMaxDelta() {
        return maxDelta;
    }

    public void setMeanBid(float meanBid) {
        this.meanBid = meanBid;
    }

    public void setMeanAsk(float meanAsk) {
        this.meanAsk = meanAsk;
    }

    public void setMinBid(float minBid) {
        this.minBid = minBid;
    }

    public void setMaxAsk(float maxAsk) {
        this.maxAsk = maxAsk;
    }

    public void setMeanDelta(float meanDelta) {
        this.meanDelta = meanDelta;
    }

    public void setMinDelta(float minDelta) {
        this.minDelta = minDelta;
    }

    public void setMaxDelta(float maxDelta) {
        this.maxDelta = maxDelta;
    }

    public float getMinAsk() {
        return minAsk;
    }

    public float getMaxBid() {
        return maxBid;
    }

    public void setMinAsk(float minAsk) {
        this.minAsk = minAsk;
    }

    public void setMaxBid(float maxBid) {
        this.maxBid = maxBid;
    }


}
