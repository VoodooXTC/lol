package joss.jacobo.lol.lcs.purchaseUtils;

import android.content.Intent;

/**
 * Created by fdoyle on 9/24/14.
 */
public class ShareIntentBuilder {
    //"https://www.guidelinecentral.com/share/<CONTENT_TYPE>/<CONTENT_ID>/";
    public static final String baseShareString = "https://www.guidelinecentral.com/share/%s/%s/";

    public static Intent buildShareIntent(String contentType, String contentId) {
        String shareString = String.format(baseShareString, contentType, contentId);
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, shareString);
        i.setType("text/plain");
        return i;
    }
}
