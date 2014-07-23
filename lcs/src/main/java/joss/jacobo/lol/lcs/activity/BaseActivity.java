package joss.jacobo.lol.lcs.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import javax.inject.Inject;

import joss.jacobo.lol.lcs.Datastore;
import joss.jacobo.lol.lcs.MainApp;

/**
 * Created by jossayjacobo on 7/20/14.
 */
public class BaseActivity extends ActionBarActivity {

    @Inject
    Datastore datastore;

    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);
        ((MainApp) getApplication()).inject(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = this.getWindow();

        // Eliminates color banding
        window.setFormat(PixelFormat.RGBA_8888);
    }

    public void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.ab_sentara_logo);
    }
}
