package com.bm.main.fpl.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.webkit.WebView;

import com.bm.main.pos.R;
import com.bm.main.fpl.models.PromoProdukModel;

public class WebViewActivity extends BaseActivity {

    private WebView webView;
    PromoProdukModel.Response_value promoProdukModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        if(intent!=null) {
            promoProdukModel = intent.getParcelableExtra("promo");
            // setContent(notificationModel);
        }
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Promo "+promoProdukModel.getProduk());
        init(1);
        webView=findViewById(R.id.webView);
        webView.getSettings().getJavaScriptEnabled();
        webView.getSettings().getUserAgentString();
        webView.loadUrl(promoProdukModel.getTarget_link());
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
        //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);


    }
}
