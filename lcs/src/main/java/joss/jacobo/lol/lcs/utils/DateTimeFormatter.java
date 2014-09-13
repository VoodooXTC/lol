package joss.jacobo.lol.lcs.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by Joss on 7/24/2014
 */
public class DateTimeFormatter {

    public static final int TWITTER = 0;
    public static final int YOUTUBE = 1;

    public static final long ONE_SECOND = 1000;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static final long ONE_DAY = ONE_HOUR * 24;
    public static final long ONE_WEEK = ONE_DAY * 7;
    public static final long ONE_MONTH = ONE_WEEK * 4;
    public static final long ONE_YEAR = ONE_MONTH * 12;

    public static String formatDate(String dateString){

        try {

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(dateString);

            SimpleDateFormat fmtOut = new SimpleDateFormat("cccc, MMMM dd");
            return fmtOut.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String formatDatetimeToDate(String dateString){

        try {

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateString);

            SimpleDateFormat fmtOut = new SimpleDateFormat("cccc, MMMM dd");
            return fmtOut.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String formatDatetimeToTime(String dateString){

        try {

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateString);

            SimpleDateFormat fmtOut = new SimpleDateFormat("kk:mm");
            return fmtOut.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static Date getDate(String dateString){
        try {

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return fmt.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatDateTimeStringToAgo(String datetime, int type){

        try {

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(datetime);

            return formatMillisToAgo(date.getTime(), type);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return datetime;
    }

    public static String formatMillis(Long millis){
        Date date = new Date(millis);

        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mmaa MMM dd");
        return fmtOut.format(date);
    }

    public static String formatMillisToAgo(Long millis, int type){

        String SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR;
        switch (type){
            case YOUTUBE:
                SECOND = " second";
                MINUTE = " minute";
                HOUR = " hour";
                DAY = " day";
                WEEK = " week";
                MONTH = " month";
                YEAR = " year";
                break;
            default:
                SECOND = "s";
                MINUTE = "m";
                HOUR = "h";
                DAY = "d";
                WEEK = "w";
                MONTH = "m";
                YEAR = "y";
                break;
        }

        Long current = Calendar.getInstance().getTime().getTime();

        Long tweetTime = current - millis;
        if(tweetTime < ONE_SECOND){
            return "<1s ago";
        }else if(tweetTime < ONE_MINUTE){

            int seconds = (int) (tweetTime / ONE_SECOND);
            boolean plural = seconds > 1;
            return seconds + SECOND + (type == YOUTUBE && plural ?  "s" : "") + " ago";

        }else if(tweetTime < ONE_HOUR){

            int minutes = (int) (tweetTime / ONE_MINUTE);
            boolean plural = minutes > 1;
            return minutes + MINUTE + (type == YOUTUBE && plural ?  "s" : "") + " ago";

        }else if(tweetTime < ONE_DAY){

            int hours = (int) (tweetTime / ONE_HOUR);
            boolean plural = hours > 1;
            return hours + HOUR + (type == YOUTUBE && plural ?  "s" : "") + " ago";

        }else if(tweetTime < ONE_WEEK){

            int days = (int) (tweetTime / ONE_DAY);
            boolean plural = days > 1;
            return  days + DAY + (type == YOUTUBE && plural ?  "s" : "") + " ago";

        }else if(tweetTime < ONE_MONTH){

            int weeks = (int) (tweetTime / ONE_WEEK);
            boolean plural = weeks > 1;
            return  weeks + WEEK + (type == YOUTUBE && plural ?  "s" : "") + " ago";

        }else if(tweetTime < ONE_YEAR) {

            int months = (int) (tweetTime / ONE_MONTH);
            boolean plural = months > 1;
            return months + MONTH + (type == YOUTUBE && plural ?  "s" : "") + " ago";
        }

        int years = (int) (tweetTime / ONE_YEAR);
        boolean plural = years > 1;
        return years + YEAR + (type == YOUTUBE && plural ?  "s" : "") + " ago";
    }

    public static String formatCountCommas(int count){
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(count);
    }
}
