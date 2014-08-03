package joss.jacobo.lol.lcs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Joss on 7/24/2014
 */
public class DateTimeFormatter {

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
}
