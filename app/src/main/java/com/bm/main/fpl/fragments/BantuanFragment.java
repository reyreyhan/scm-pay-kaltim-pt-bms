package com.bm.main.fpl.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
//import androidx.core.app.Fragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bm.main.pos.R;
import com.bm.main.fpl.templates.MyWebView;
import com.bm.main.fpl.utils.PreferenceClass;

public class BantuanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
MyWebView webViewLiveChat;
     ProgressBar loadingView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_bantuan, container, false);
        loadingView = rootView.findViewById(R.id.progressBar1);
        webViewLiveChat=rootView.findViewById(R.id.webViewLiveChat);
        webViewLiveChat.setWebViewClient(new myWebClient());

        webViewLiveChat.getSettings().setJavaScriptEnabled(true);
        webViewLiveChat.loadUrl("https://support.fastpay.co.id:4430/phplive/phplive.php?d=0&onpage=livechatimagelink&title=Live+Chat+Image+Link&namachat="+ PreferenceClass.getLoggedUser().getId());
       // ""
        return rootView;
    }
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(@NonNull WebView view, String url) {
            // TODO Auto-generated method stub
            loadingView.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            loadingView.setVisibility(View.GONE);
        }


    }
}
