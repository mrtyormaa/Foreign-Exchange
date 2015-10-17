package data.prep;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Created by mrtyormaa on 9/16/15.
public class DateTime {
    private SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");

    public enum Interval {
        MINUTE, HOUR, DAY, MONTH, YEAR;
    }

    public Date getDate(String strTime) throws ParseException {
        Date date = new Date();
        return date = parser.parse(strTime);
    }
}

