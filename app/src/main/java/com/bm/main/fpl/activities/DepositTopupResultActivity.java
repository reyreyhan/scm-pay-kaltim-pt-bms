package com.bm.main.fpl.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bm.main.fpl.adapters.ListStepperAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.MerchantDescModel;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.pos.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DepositTopupResultActivity extends BaseActivity implements JsonObjectResponseCallback {

    private static final String TAG = DepositTopupResultActivity.class.getSimpleName();
    MerchantDescModel merchantDescModel;
    TextView textViewNotes;
    String produkMerchant;
    private String productBankMerchantDesc = "ListBankMerchantDescDeposit";
    @NonNull
    ArrayList<MerchantDescModel.Response_value> dataMerchantDesc = new ArrayList<>();
    private String nominal;
    private String payment_code;

    ListStepperAdapter listStepperAdapter;
    RecyclerView recyclerViewMerchantDesc;
    private String url_image;
    ImageView imageViewLogoMerchant;
    private String description;
    TextView textViewPaymentNominal;
    TextView textViewPaymentCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_topup_result);

        Intent intent = getIntent();

        produkMerchant = intent.getStringExtra("produkMerchant");
        nominal = intent.getStringExtra("nominal");
        payment_code = intent.getStringExtra("payment_code");
        url_image = intent.getStringExtra("url_image");
        description = intent.getStringExtra("description");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Deposit " + produkMerchant);

        init(1);
        //  new_popup_alert(this, "Informasi",description);
        imageViewLogoMerchant = findViewById(R.id.imageViewLogoMerchant);
        textViewNotes = findViewById(R.id.textViewNotes);
        textViewPaymentNominal = findViewById(R.id.textViewPaymentNominal);
        textViewPaymentCode = findViewById(R.id.textViewPaymentCode);

        textViewPaymentNominal.setText(nominal);
        textViewPaymentCode.setText(payment_code);


        Glide.with(this).asBitmap().load(url_image).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).override(184, 59).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(imageViewLogoMerchant) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                //viewHolder.avi.setVisibility(View.GONE);
                // here you can be sure it's already set
            }

            // +++++ OR +++++
            @Override
            protected void setResource(Bitmap resource) {
                // this.getView().setImageDrawable(resource); is about to be called
                super.setResource(resource);
                //  viewHolder.avi.setVisibility(View.GONE);
                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                //   viewHolder.avi.setVisibility(View.GONE);
                imageViewLogoMerchant.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.mipmap.ic_launcher));
            }
        });

        recyclerViewMerchantDesc = findViewById(R.id.recyclerViewMerchantDesc);
        LinearLayoutManager linearLayoutManagerx = new LinearLayoutManager(this);
        recyclerViewMerchantDesc.setHasFixedSize(false);
        recyclerViewMerchantDesc.setLayoutManager(linearLayoutManagerx);
        listStepperAdapter = new ListStepperAdapter(dataMerchantDesc, this);
        recyclerViewMerchantDesc.setAdapter(listStepperAdapter);

//        if (PreferenceClass.getJSONObject(productBankMerchantDesc + produkMerchant).length() > 0) {
//
//            merchantDescModel = gson.fromJson(PreferenceClass.getJSONObject(productBankMerchantDesc + produkMerchant).toString(), MerchantDescModel.class);
//
//            dataMerchantDesc.clear();
//            dataMerchantDesc.addAll(merchantDescModel.getResponse_value());
//            //  setData(gameModel.getData());
//            listStepperAdapter.notifyDataSetChanged();
//            recyclerViewMerchantDesc.setVisibility(View.VISIBLE);
//
//            textViewNotes.setVisibility(View.VISIBLE);
//            textViewNotes.setText(Html.fromHtml(merchantDescModel.getNote()));
//        }
        requestDescriptionMerchant();
    }


    private void requestDescriptionMerchant() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestDescreptionMerchant(produkMerchant));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.LIST_PRODUK_MERCHANT_DESCRIPTION, this);

    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
//        try {
//            if (response.getString("response_code").equals("03")) {
//                if (actionCode == ActionCode.REQUEST_DEPOSIT || actionCode == ActionCode.REQUEST_DEPOSIT24) {
//                    closeProgressBarDialog();
//                }
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {


        if (actionCode == ActionCode.LIST_PRODUK_MERCHANT_DESCRIPTION) {
            //Log.d(TAG, "onSuccess: ");

            merchantDescModel = gson.fromJson(response.toString(), MerchantDescModel.class);
            if (merchantDescModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                //  Log.d(TAG, "onSuccess: "+merchantDescModel.getNote());

                //JSONObject obj = PreferenceClass.getJSONObject(productBankMerchantDesc + produkMerchant);
//                        JSONArray array = new JSONArray();
//                        try {
//                            array = obj.getJSONArray("response_value");
//                        } catch (JSONException e) {
//                            // e.printStackTrace();
//                        }

//                        if (array.length() != merchantDescModel.getResponse_value().size()) {
                //  PreferenceClass.putJSONObject(productBankMerchantDesc + produkMerchant, response);
                dataMerchantDesc.clear();
                dataMerchantDesc.addAll(merchantDescModel.getResponse_value());


                listStepperAdapter.notifyDataSetChanged();
                textViewNotes.setText(Html.fromHtml(merchantDescModel.getNote()));
//                        }

            }
        }
//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        //  Log.d(TAG, "onFailure: "+responseCode+" "+responseDescription+" "+throwable.toString());
//        if (responseCode.equals(NETWORK_ERROR)) {

        if (PreferenceClass.getJSONObject(productBankMerchantDesc + produkMerchant).length() > 0) {
            recyclerViewMerchantDesc.setVisibility(View.VISIBLE);
        } else {
            new_popup_alert_failure(DepositTopupResultActivity.this, responseDescription);
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        //  overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }


}
