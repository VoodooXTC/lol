package joss.jacobo.lol.lcs.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import javax.inject.Inject;

import butterknife.InjectView;
import joss.jacobo.lol.lcs.Datastore;
import joss.jacobo.lol.lcs.MainApp;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.purchaseUtils.PurchaseConstants;
import joss.jacobo.lol.lcs.purchaseUtils.IabHelper;
import joss.jacobo.lol.lcs.purchaseUtils.IabResult;
import joss.jacobo.lol.lcs.purchaseUtils.Inventory;
import joss.jacobo.lol.lcs.purchaseUtils.Purchase;
import joss.jacobo.lol.lcs.views.ToolbarTitle;

/**
 * Created by jossayjacobo on 7/20/14
 */
public abstract class BaseActivity extends ActionBarActivity implements
        IabHelper.OnIabSetupFinishedListener, IabHelper.QueryInventoryFinishedListener {

    @Inject
    Datastore datastore;

    @InjectView(R.id.toolbar)
    public Toolbar toolbar;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = this.getWindow();

        // Eliminates color banding
        window.setFormat(PixelFormat.RGBA_8888);
    }

    protected void setupActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void setToolbarTitle(String title) {
        setTitle(title, null);
    }

    public void setToolbarTitleAndSubtitle(String title, String subtitle) {
        setTitle(title, subtitle);
    }

    public void setTitle(String title, String subtitle){
        if (getSupportActionBar() != null && title != null){

            if (toolbar == null){
                return;
            }

            ToolbarTitle toolbarTitle = (ToolbarTitle) toolbar.findViewById(R.id.toolbar_title);
            toolbarTitle.setContent(title, subtitle);
        }
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
