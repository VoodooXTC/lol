package joss.jacobo.lol.lcs.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.purchaseUtils.IabResult;
import joss.jacobo.lol.lcs.purchaseUtils.Purchase;

/**
 * Created by jossayjacobo on 9/14/14
 */
public class PurchaseAdsFreeActivity extends PurchaseActivity{

    public static final String SKU = "sku";

    String sku;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        Bundle b = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
        sku = b.getString(SKU);
    }

    @Override
    protected void dealWithIabSetupSuccess() {
        if(sku != null){
            purchaseItem(sku);
        }else{
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    protected void dealWithIabSetupFailure() {
        Toast.makeText(this, getString(R.string.purchase_error), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void dealWithIabPuchaseFailed(IabResult result) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void dealWithIabPurchaseSuccess(IabResult result, Purchase info) {
        setResult(RESULT_OK);
        finish();
    }
}
