package com.bm.main.fpl.webview;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.scm.SplashScreenActivity;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.FCMConstants;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class FCMActivity extends AppCompatActivity {
    private static final String TAG = FCMActivity.class.getSimpleName();
    String title, url;
    WebView webView;
    Toolbar toolbar;
    private ProgressBar loadingView;
    private Tracker mTracker;
    private FirebaseAnalytics mFirebaseAnalytics;
    @NonNull
    String ua = "Mozilla/5.0 (Linux; Android 4.4; Nexus 4 Build/KRT16H) AppleWebKit/537.36\n" +
            "(KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36";

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = this.getIntent();
        if (intent != null)
            url = intent.getStringExtra("url");

        toolbar = findViewById(R.id.toolbar);
        loadingView = findViewById(R.id.progressBar1);
        toolbar.setTitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Title);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_cancel);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Promo");

        webView = findViewById(R.id.webPromo);
        webView.setWebViewClient(new myWebClient());
        webView.setWebChromeClient(new myWebChromeClient());

        WebSettings ws = webView.getSettings();
        ws.setUserAgentString(url);
        ws.setAllowFileAccess(true);
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setAllowUniversalAccessFromFileURLs(true);
        ws.setAllowFileAccessFromFileURLs(true);
        ws.setAllowContentAccess(true);
        ws.setUseWideViewPort(true);
        ws.setSupportMultipleWindows(true);

        CookieManager.getInstance().setAcceptCookie(true);
        webView.addJavascriptInterface(this, "Android");

        SBFApplication application = (SBFApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        logEventFireBase("FCM", "Promo", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);

        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        try {
            Log.d(TAG, "Enabling HTML5-Features");
            Method m1 = WebSettings.class.getMethod("setDomStorageEnabled", Boolean.TYPE);
            m1.invoke(ws, Boolean.TRUE);

            Method m2 = WebSettings.class.getMethod("setDatabaseEnabled", Boolean.TYPE);
            m2.invoke(ws, Boolean.TRUE);

            Method m3 = WebSettings.class.getMethod("setDatabasePath", String.class);
            m3.invoke(ws, getApplicationContext().getFilesDir().getPath() + getPackageName() + "/databases/");

            Method m4 = WebSettings.class.getMethod("setAppCacheMaxSize", Long.TYPE);
            m4.invoke(ws, 1024 * 1024 * 8);

            Method m5 = WebSettings.class.getMethod("setAppCachePath", String.class);
            m5.invoke(ws, getApplicationContext().getFilesDir().getPath() + getPackageName() + "/cache/");

            Method m6 = WebSettings.class.getMethod("setAppCacheEnabled", Boolean.TYPE);
            m6.invoke(ws, Boolean.TRUE);

            Log.d(TAG, "Enabled HTML5-Features");
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "Reflection fail", e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "Reflection fail", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Reflection fail", e);
        }

        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                try {
                    String result = java.net.URLDecoder.decode(url, "UTF-8");
                    //DownloadManager.Request request = new DownloadManager.Request(
                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(result.trim()));

                    String cookies = CookieManager.getInstance().getCookie(url);
                    request.addRequestHeader("cookie", cookies);
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                    final String filename = URLUtil.guessFileName(url, contentDisposition, mimetype);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                    //  request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Download PDF");
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Mengunduh File", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                    Toast.makeText(getApplicationContext(), "Tidak memiliki ijin membuat file", Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(getApplicationContext(), "File tidak didukung", Toast.LENGTH_LONG).show();
                }
            }
        });

        webView.loadUrl(url);
    }

    public class myWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    }

    public class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            loadingView.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(@NonNull WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
            //   Log.d("UrlLoading", "shouldOverrideUrlLoading: "+url);
//            loadingView.setVisibility(View.VISIBLE);
//            view.loadUrl(url);
//            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            loadingView.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, @NonNull final SslErrorHandler handler, @NonNull SslError error) {
            Log.d(TAG, "onReceivedSslError: " + error.getPrimaryError());

            try {
                X509TrustManager trustManager = new X509TrustManager() {
                    @SuppressLint("TrustAllX509TrustManager")
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        Log.d(TAG, "checkClientTrusted: " + authType);
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        Log.d(TAG, "checkServerTrusted: " + authType);
                    }

                    @NonNull
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                };

                //Get the certificate from error object
                Bundle bundle = SslCertificate.saveState(error.getCertificate());
                X509Certificate x509Certificate;
                byte[] bytes = bundle.getByteArray("x509-certificate");
                if (bytes == null) {
                    x509Certificate = null;
                } else {
                    CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                    Certificate cert = certFactory.generateCertificate(new ByteArrayInputStream(bytes));
                    x509Certificate = (X509Certificate) cert;
                }
                X509Certificate[] x509Certificates = new X509Certificate[1];
                x509Certificates[0] = x509Certificate;

                // check weather the certificate is trusted
                trustManager.checkServerTrusted(x509Certificates, "ECDH_RSA");

                Log.d(TAG, "Certificate from " + error.getUrl() + " is trusted.");
                handler.proceed();
            } catch (CertificateException e) {
                Log.d(TAG, "Failed to access " + error.getUrl() + ". Error: " + error.getPrimaryError());
                final AlertDialog.Builder builder = new AlertDialog.Builder(FCMActivity.this);
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Apakah Anda akan tetep melanjutkan?";

                builder.setTitle("SSL Certificate Error");
                builder.setMessage(message);
                builder.setPositiveButton("lanjutkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                        finish();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (FCMConstants.isActivityRunning) {
                finish();
            } else {
                Intent intent = new Intent(FCMActivity.this, SplashScreenActivity.class);
                startActivity(intent);
                ActivityCompat.finishAffinity(FCMActivity.this);
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (FCMConstants.isActivityRunning) {
            finish();
        } else {
            Intent intent = new Intent(FCMActivity.this, SplashScreenActivity.class);
            startActivity(intent);
            ActivityCompat.finishAffinity(FCMActivity.this);
        }
    }

    public void sendToAnalylic(String itemCategory, String itemName, String action, String tag) {
        mTracker.setScreenName(tag);

        mTracker.send(new HitBuilders.EventBuilder()
                .setLabel(itemName)
                .setCategory(itemCategory)
                .setAction(action)
                .build());
    }

    /**
     * //     * This method logEventFireBase.
     * //     *
     * //     * @param itemCategory    FirebaseAnalytics.Param.ITEM_CATEGORY
     * //     * @param itemName    FirebaseAnalytics.Param.ITEM_NAME
     * //     * @param action    FirebaseAnalytics.Param.CONTENT_TYPE
     * //     * @param success    FirebaseAnalytics.Param.SUCCESS
     * //     * @param tag    ClassName
     * <p>
     * <p>
     * //
     */
    public void logEventFireBase(String itemCategory, String itemName, String action, String success, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "FCM");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, itemCategory);
        bundle.putString(FirebaseAnalytics.Param.SOURCE, tag);
        bundle.putString(FirebaseAnalytics.Param.SUCCESS, success);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, action);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        // Log.d(TAG, "logEventFireBase: "+mFirebaseAnalytics.getFirebaseInstanceId()+" "+mFirebaseAnalytics.getAppInstanceId());
        sendToAnalylic(itemCategory, itemName, action, tag);
    }

    @Nullable
    public URL verifyUrl(String url) {
        URL verifiedUrl;
        try {
            verifiedUrl = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return verifiedUrl;
    }

    public void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                public boolean verify(@NonNull String hostname, SSLSession session) {
                    return hostname.equalsIgnoreCase("mpdesktop.fastpay.co.id") ||
                            hostname.equalsIgnoreCase("www.fastpay.co.id") ||
                            hostname.equalsIgnoreCase("www.fpkita.net") ||
                            hostname.equalsIgnoreCase("tomofastpay.id") ||
                            hostname.equalsIgnoreCase("dashboard.fastpay.co.id");
//                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) {
                }

                @NonNull
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}
