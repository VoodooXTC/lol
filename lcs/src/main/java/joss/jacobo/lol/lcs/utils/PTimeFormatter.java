package joss.jacobo.lol.lcs.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by jossayjacobo on 9/8/14
 */
public class PTimeFormatter {

    private static final String regex2two = "(?<=[^\\d])(\\d)(?=[^\\d])";
    private static final String two = "0$1";
    private Map<String, String> regexMap;

    public PTimeFormatter(){
        regexMap = new HashMap<String, String>();

        regexMap.put("PT(\\d\\d)S", "00:$1");
        regexMap.put("PT(\\d\\d)M", "$1:00");
        regexMap.put("PT(\\d\\d)H", "$1:00:00");
        regexMap.put("PT(\\d\\d)M(\\d\\d)S", "$1:$2");
        regexMap.put("PT(\\d\\d)H(\\d\\d)S", "$1:00:$2");
        regexMap.put("PT(\\d\\d)H(\\d\\d)M", "$1:$2:00");
        regexMap.put("PT(\\d\\d)H(\\d\\d)M(\\d\\d)S", "$1:$2:$3");
    }

    public String formatPTimeRegex(String ptime){
        String output = "";
        if(ptime != null){
            // Add leading 0 if single digit
            String time = ptime.replaceAll(regex2two, two);

            String regex = getRegex(time);
            if(regex != null){
                output = time.replaceAll(regex, regexMap.get(regex));
            }
        }
        return output.length() > 0 ? output : ptime;
    }

    private String getRegex(String date) {
        for (String r : regexMap.keySet())
            if (Pattern.matches(r, date))
                return r;
        return null;
    }
}
