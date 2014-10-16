package joss.jacobo.lol.lcs.purchaseUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oak.util.OakUtils;

/**
 * Created by ericrichardson on 7/31/13
 */
public class TypefaceUtil {

    /**
     * Iterates through a ViewGroups children to change typefaces on TextView it comes across
     *
     * @param root
     */
    public static void changeTabFonts(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(OakUtils.getStaticTypeFace(root.getContext(), "OpenSans-Semibold.ttf"));
            } else if (v instanceof ViewGroup) {
                changeTabFonts((ViewGroup) v);
            }
        }
    }

    /**
     * Iterates through a ViewGroups children to change the typefaces on the TextViews is comes
     * across, giving a bolder weight to the selected tab.
     *
     * @param root     the root
     * @param position the position of the selected tab
     */
    public static void changeTabFonts(ViewGroup root, int position) {
        if (position < 0) position = 0;

        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(i == position ? OakUtils.getStaticTypeFace(root.getContext(), "OpenSans-Bold.ttf") : OakUtils.getStaticTypeFace(root.getContext(), "OpenSans-Semibold.ttf"));
            } else if (v instanceof ViewGroup) {
                changeTabFonts((ViewGroup) v, position);
            }
        }
    }
}
