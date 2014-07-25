package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerTitleStrip;
import android.util.AttributeSet;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;
import oak.util.OakUtils;

/**
 * Created by jossayjacobo on 7/25/14.
 */
public class PagerTitleStripWithFont extends PagerTitleStrip {

    Context context;
    String fontName;

    public PagerTitleStripWithFont(Context context) {
        this(context, null);
    }

    public PagerTitleStripWithFont(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        if(attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerTitleStripWithFont);

            try{
                fontName = typedArray.getString(R.styleable.PagerTitleStripWithFont_font);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }

            typedArray.recycle();
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b){
        super.onLayout(changed, l, t, r, b);

        if(fontName != null){
            for(int i = 0; i < this.getChildCount(); i++){
                if(this.getChildAt(i) instanceof TextView){
                    ((TextView) this.getChildAt(i)).setTypeface(OakUtils.getStaticTypeFace(getContext(), fontName));
                }
            }
        }
    }

}
