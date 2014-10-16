package joss.jacobo.lol.lcs.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import javax.inject.Inject;

import joss.jacobo.lol.lcs.Datastore;
import joss.jacobo.lol.lcs.MainApp;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.interfaces.BaseFragmentListener;
import joss.jacobo.lol.lcs.purchaseUtils.PurchaseConstants;
import joss.jacobo.lol.lcs.purchaseUtils.IabHelper;
import joss.jacobo.lol.lcs.purchaseUtils.IabResult;
import joss.jacobo.lol.lcs.purchaseUtils.Inventory;
import joss.jacobo.lol.lcs.purchaseUtils.Purchase;
import joss.jacobo.lol.lcs.views.ActionBarCustomTitle;

/**
 * Created by jossayjacobo on 7/20/14
 */
public class BaseActivity extends ActionBarActivity implements BaseFragmentListener, IabHelper.OnIabSetupFinishedListener, IabHelper.QueryInventoryFinishedListener {

    @Inject
    Datastore datastore;

    ActionBarCustomTitle customTitle;

    IabHelper mHelper;

    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);
        ((MainApp) getApplication()).inject(this);
    }

    @Override
    public void onResume(){
        super.onResume();

        if(!datastore.isAdsFree()){
            switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)){
                case ConnectionResult.SUCCESS:
                    mHelper = new IabHelper(this, PurchaseConstants.PUBLIC_KEY);
                    mHelper.startSetup(this);
                    break;
            }
        }

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

    @Override
    public void onIabSetupFinished(IabResult result) {
        if (!result.isSuccess()) {
            // Oh noes, there was a problem.
            return;
        }

        // Have we been disposed of in the meantime? If so, quit.
        if (mHelper == null) return;

        // IAB is fully set up. Now, let's get an inventory of stuff we own.
        mHelper.queryInventoryAsync(this);
    }

    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        // Have we been disposed of in the meantime? If so, quit.
        if (mHelper == null) return;

        // Is it a failure?
        if (result.isFailure()) {
            return;
        }

        /**
         * Query inventory was successful
         *
         * Check for items we own. Notice that for each purchase, we check
         * the developer payload to see if it's correct! See
         * verifyDeveloperPayload().
         */

        Purchase adsFreePurchase = inv.getPurchase(PurchaseConstants.SKU_ADS_FREE);
        datastore.persistIsAdsFree(adsFreePurchase != null);
    }
}
