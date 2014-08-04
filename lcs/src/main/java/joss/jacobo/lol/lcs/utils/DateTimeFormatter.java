package joss.jacobo.lol.lcs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Joss on 7/24/2014
 */
public class DateTimeFormatter {

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

    public static String formatMillis(Long millis){
        Date date = new Date(millis);

        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mmaa MMM dd");
        return fmtOut.format(date);
    }

    public static String formatMillisToAgo(Long millis){

        Long current = Calendar.getInstance().getTime().getTime();

        Long tweetTime = current - millis;
        if(tweetTime < ONE_SECOND){
            return "<1s ago";
        }else if(tweetTime < ONE_MINUTE){
            return ((int) (tweetTime / ONE_SECOND)) + "s ago";
        }else if(tweetTime < ONE_HOUR){
            return ((int) (tweetTime / ONE_MINUTE)) + "m ago";
        }else if(tweetTime < ONE_DAY){
            return ((int) (tweetTime / ONE_HOUR)) + "h ago";
        }else if(tweetTime < ONE_WEEK){
            return ((int) (tweetTime / ONE_DAY)) + "d ago";
        }else if(tweetTime < ONE_MONTH){
            return ((int) (tweetTime / ONE_WEEK)) + "w ago";
        }else if(tweetTime < ONE_YEAR) {
            return ((int) (tweetTime / ONE_MONTH)) + "m ago";
        }
        return ((int) (tweetTime / ONE_YEAR) + "y ago");
    }
}
