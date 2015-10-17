package data.prep;

// Created by mrtyormaa on 9/16/15.
public class Labels {
    private int bidDirectionality;
    private int askDirectionality;

    public int getAskDirectionality() {
        return askDirectionality;
    }

    public int getBidDirectionality() {
        return bidDirectionality;
    }

    public void setAskDirectionality(int askDirectionality) {
        this.askDirectionality = askDirectionality;
    }

    public void setBidDirectionality(int bidDirectionality) {
        this.bidDirectionality = bidDirectionality;
    }
}
