package joss.jacobo.lol.lcs.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import javax.inject.Inject;

import joss.jacobo.lol.lcs.Datastore;
import joss.jacobo.lol.lcs.MainApp;
import joss.jacobo.lol.lcs.interfaces.BaseFragmentListener;
import joss.jacobo.lol.lcs.views.ActionBarCustomTitle;

/**
 * Created by jossayjacobo on 7/20/14
 */
public class BaseActivity extends ActionBarActivity implements BaseFragmentListener{

    @Inject
    Datastore datastore;

    ActionBarCustomTitle customTitle;

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
    }

    @Override
    public void onSetActionBarTitle(String title, String subtitle) {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(customTitle == null){
            customTitle = new ActionBarCustomTitle(this);
            getSupportActionBar().setCustomView(customTitle);
        }

        customTitle.setContent(title, subtitle);

        if(title == null) {
            customTitle.hideTitle();
        }else{
            customTitle.showTitle();
        }

        if(subtitle == null) {
            customTitle.hideSubtitle();
        }else{
            customTitle.showSubtitle();
        }
    }

    @Override
    public void teamSelected(int teamId) {

    }
}
