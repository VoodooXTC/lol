package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Joss on 9/7/2014
 */
public class LoLScrollView extends ScrollView implements View.OnTouchListener{

    private static final String TAG = "LoLScrollView";

    private float downX;
    private float downY;
    private boolean down = false;

    public LoLScrollView(Context context) {
        this(context, null);
    }

    public LoLScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoLScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                down = true;
                return false;

            case MotionEvent.ACTION_MOVE:
                if (!down) {
                    downX = event.getX();
                    downY = event.getY();
                    down = true;
                }else{
                    if(event.getY() > downY){
                        // Scroll Up
                        v.getParent().requestDisallowInterceptTouchEvent(getScrollY() != 0);
                    }else{
                        // Scroll Down
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

                return false;

            case MotionEvent.ACTION_UP:
                if (!down)
                    return false;

                down = false;
                return false;

            default:
                return false;
        }
    }
}
