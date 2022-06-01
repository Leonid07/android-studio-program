package com.progect.in_service;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;

public class Podpiska extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private BillingProcessor mBillingProcessor;

    //private Button mSingleTimePaymentButton;
    private Button mConsumabelButton;
    private Button mSubscriptionButton;
    private View mProgress;

    private final static String SUBSCRIPTION = "delaroy_yearly";
    private final static String GPLAY_LICENSE = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAphIn6JsZGq5sakJ0zgo7fgU++oNPR1m2OXi5+B0+PzJ6APkkTt2fj+HxGrT1LndK1fcqR4ANpsT6cMrrLlQFoehAuD2bGc1BaZdd3FB2A2cFPB2BgzvtEUC8fjgectcxrBG3Yq6GgUX4mxDMljBE2v4BgpeVnCcmCbBmfWF0iCoqwRhj/UKzvpJpPtMcJuXVbHUswXdBXu7tddS8mYI1rbyTL9s7rjxnc2b5cjGjFIbQBeWpAx/kxUx7tK17BbCiP4ih5Fc2gfEf2sBBQlDgNOo4PSpux/D6NuVz42vX24hvikBtenWXcGth1uDNjjkQ0NgV+SYL4/gZwI6TJaN+ZwIDAQAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocypki);

        mProgress = findViewById(R.id.progress);

//        mSingleTimePaymentButton = findViewById(R.id.btnSingleTypePayment);
        mConsumabelButton = findViewById(R.id.btnConsume);
        mSubscriptionButton = findViewById(R.id.btnSubscription);

        mBillingProcessor = new BillingProcessor(this, GPLAY_LICENSE, this);
        boolean isAvailable = BillingProcessor.isIabServiceAvailable(this);
        if (!isAvailable) {
            mProgress.setVisibility(View.GONE);
//            showMsg(getString(R.string.billing_not_available));
        } else {
            mBillingProcessor.initialize();
        }

        mSubscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBillingProcessor.subscribe(Podpiska.this, SUBSCRIPTION);
            }
        });
    }
    @Override
    public void onBillingInitialized() {
        /*
         * Called when BillingProcessor was initialized and it's ready to purchase
         */
        showMsg("onBillingInitialized");
        if (mBillingProcessor.loadOwnedPurchasesFromGoogle()) {
            handleLoadedItems();
        }
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        /*
         * Called when requested PRODUCT ID was successfully purchased
         */
        showMsg("onProductPurchased");
        if (checkIfPurchaseIsValid(details.purchaseInfo)) {
            showMsg("purchase: " + productId + " COMPLETED");
            switch (productId) {
//                case ONE_TIME_PAYMENT:
//                    setupConsumableButtons(true);
//                    break;
                case SUBSCRIPTION:
                    setupSubscription(true);
                    break;
            }
        } else {
            showMsg("fakePayment");
        }
    }

    @Override
    public void onPurchaseHistoryRestored() {
        /*
         * Called when purchase history was restored and the list of all owned PRODUCT ID's
         * was loaded from Google Play
         */
        showMsg("onPurchaseHistoryRestored");
        handleLoadedItems();
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    private void handleLoadedItems() {
        mProgress.setVisibility(View.GONE);
        boolean isSubsUpdateSupported = mBillingProcessor.isSubscriptionUpdateSupported();
        if (isSubsUpdateSupported) {
            mSubscriptionButton.setVisibility(View.VISIBLE);
        } else {
//            showMsg(getString(R.string.subscription_not_supported));
        }

        setupSubscription(mBillingProcessor.listOwnedSubscriptions().contains(SUBSCRIPTION));
    }

    private void setupSubscription(boolean isPurchased) {
        mSubscriptionButton.setEnabled(!isPurchased);
        if (isPurchased) {
//            mSubscriptionButton.setText(R.string.already_subscribed);
        } else {
            SkuDetails details = mBillingProcessor.getSubscriptionListingDetails(SUBSCRIPTION);
            //mSubscriptionButton.setText(details.priceText);
        }
    }
    private boolean checkIfPurchaseIsValid(PurchaseInfo purchaseInfo) {
        // TODO as of now we assume that all purchases are valid
        return true;
    }
    private void showMsg(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
