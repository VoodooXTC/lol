package joss.jacobo.lol.lcs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.security.SecureRandom;
import java.util.Random;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.purchaseUtils.IabHelper;
import joss.jacobo.lol.lcs.purchaseUtils.IabResult;
import joss.jacobo.lol.lcs.purchaseUtils.Purchase;
import joss.jacobo.lol.lcs.purchaseUtils.PurchaseConstants;

/**
 * Created by jossayjacobo on 9/14/14
 */
public abstract class PurchaseActivity extends Activity
        implements IabHelper.OnIabSetupFinishedListener, IabHelper.OnIabPurchaseFinishedListener{

    public final static String TAG = "PurchaseActivity";

    private int RC_REQUEST;
    private String RC_PAYLOAD;

    // The helper object
    private IabHelper billingHelper;

    private String SKU;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        setResult(RESULT_CANCELED);

        SecureRandom secureRandom = new SecureRandom();
        RandomString randomString = new RandomString(32);

        RC_REQUEST = secureRandom.nextInt(32);
        RC_PAYLOAD = randomString.nextString();

        billingHelper = new IabHelper(this, PurchaseConstants.PUBLIC_KEY);
        billingHelper.startSetup(this);
    }

    @Override
    public void onIabSetupFinished(IabResult result) {
        if (result.isSuccess()) {
            dealWithIabSetupSuccess();
        } else {
            dealWithIabSetupFailure();
        }
    }

    protected abstract void dealWithIabSetupSuccess();

    protected abstract void dealWithIabSetupFailure();

    protected abstract void dealWithIabPuchaseFailed(IabResult result);

    protected abstract void dealWithIabPurchaseSuccess(IabResult result, Purchase info);

    protected void purchaseItem(String sku) {
        this.SKU = sku;
        billingHelper.launchPurchaseFlow(this, this.SKU, RC_REQUEST, this, RC_PAYLOAD);
    }

    protected void consumePurchaseItem(Purchase info){
        billingHelper.consumeAsync(info, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        billingHelper.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        if (result.isFailure()) {
            dealWithIabPuchaseFailed(result);
        } else if (this.SKU.equals(info.getSku()) && info.getDeveloperPayload().equals(RC_PAYLOAD)) {
            dealWithIabPurchaseSuccess(result, info);
        }
    }

    @Override
    protected void onDestroy() {
        disposeBillingHelper();
        super.onDestroy();
    }

    private void disposeBillingHelper() {
        if (billingHelper != null) {
            billingHelper.dispose();
        }
        billingHelper = null;
    }

    public class RandomString {

        private final char[] symbols = new char[36];

        {
            for (int idx = 0; idx < 10; ++idx)
                symbols[idx] = (char) ('0' + idx);
            for (int idx = 10; idx < 36; ++idx)
                symbols[idx] = (char) ('a' + idx - 10);
        }

        private final Random random = new Random();

        private final char[] buf;

        public RandomString(int length) {
            if (length < 1)
                throw new IllegalArgumentException("length < 1: " + length);
            buf = new char[length];
        }

        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

    }
}
