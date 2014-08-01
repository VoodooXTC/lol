package joss.jacobo.lol.lcs.utils;

/**
 * Created by Joss on 8/1/2014
 */
public class Bootstrap {

    public static final String BASE_URL = "file:///android_asset/bootstrap/";
    public static final String MIME_TYPE = "text/html";
    public static final String ENCODING = "utf-8";

    public static final String HEADER =
            "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "  <head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "\n" +
            "    <link href=\"css/bootstrap-theme.css\" rel=\"stylesheet\">\n" +
            "    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
            "\t<link href=\"css/lcs.css\" rel=\"stylesheet\">\n" +
            "\n" +
            "    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->\n" +
            "    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->\n" +
            "    <!--[if lt IE 9]>\n" +
            "      <script src=\"https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js\"></script>\n" +
            "      <script src=\"https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js\"></script>\n" +
            "    <![endif]-->\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    \n" +
            "    <div class=\"container\">";

    public static final String FOOTER =
            "\t</div><!-- /div.container -->\n" +
            "\n" +
            "    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->\n" +
            "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js\"></script>\n" +
            "    <!-- Include all compiled plugins (below), or include individual files as needed -->\n" +
            "    <script src=\"js/bootstrap.min.js\"></script>\n" +
            "  </body>\n" +
            "</html>";

    public static String wrap(String html){
        return HEADER + html + FOOTER;
    }
}
