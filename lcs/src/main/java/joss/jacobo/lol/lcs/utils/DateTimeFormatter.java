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

    public static String formatDatetime(String dateString){

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

        String second, minute, hour, day, week, month, year;
        switch (type){
            case YOUTUBE:
                second = " second";
                minute = " minute";
                hour = " hour";
                day = " day";
                week = " week";
                month = " month";
                year = " year";
                break;
            default:
                second = "s";
                minute = "m";
                hour = "h";
                day = "d";
                week = "w";
                month = "m";
                year = "y";
                break;
        }

        Long current = Calendar.getInstance().getTime().getTime();

        Long tweetTime = current - millis;
        if(tweetTime < ONE_SECOND){
            return "<1s ago";
        }else if(tweetTime < ONE_MINUTE){
            return ((int) (tweetTime / ONE_SECOND)) + second + " ago";
        }else if(tweetTime < ONE_HOUR){
            return ((int) (tweetTime / ONE_MINUTE)) + minute + " ago";
        }else if(tweetTime < ONE_DAY){
            return ((int) (tweetTime / ONE_HOUR)) + hour + " ago";
        }else if(tweetTime < ONE_WEEK){
            return ((int) (tweetTime / ONE_DAY)) + day + " ago";
        }else if(tweetTime < ONE_MONTH){
            return ((int) (tweetTime / ONE_WEEK)) + week + " ago";
        }else if(tweetTime < ONE_YEAR) {
            return ((int) (tweetTime / ONE_MONTH)) + month + " ago";
        }
        return ((int) (tweetTime / ONE_YEAR) + year + " ago");
    }

    public static String formatPTime(String ptime){

        String output = "";
        if(ptime != null){
            String time = ptime.replace("PT", "");
            String hour[] = time.split("H");
            String minute[];
            String second[];

            if(hour.length > 1){

                if(hour[0].length() < 2){
                    output += "0";
                }

                output += hour[0] + ":";
                minute = hour[1].split("M");

            }else{
                minute = hour[0].split("M");
            }

            if(minute.length > 1){

                if(minute[0].length() < 2){
                    output += "0";
                }

                output += minute[0] + ":";
                second = minute[1].split("S");
            }else{
                second = minute[0].split("S");
            }

            if(second.length >= 1) {

                if(second[0].length() < 2){
                    output += "0";
                }

                output += second[0];
            }
        }

        return output.length() > 1 ? output : ptime;
    }

    public static String formatCountCommas(int count){
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(count);
    }
}
