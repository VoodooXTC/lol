package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

/**
 * Created by Joss on 8/1/2014.
 */
public class DenyFocusRequestScrollView extends ScrollView {
    public DenyFocusRequestScrollView(Context context) {
        super(context);
    }

    public DenyFocusRequestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DenyFocusRequestScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if(focused instanceof WebView){
            return;
        }
        super.requestChildFocus(child, focused);
    }
}
