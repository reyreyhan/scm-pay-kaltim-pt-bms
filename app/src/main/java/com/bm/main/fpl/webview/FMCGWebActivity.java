package com.bm.main.fpl.webview;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioButton;

import com.bm.main.fpl.utils.UrlUtils;
import com.bm.main.scm.R;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.utils.PreferenceClass;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class FMCGWebActivity extends BaseActivity {
    private static final String TAG = FMCGWebActivity.class.getSimpleName();
    private WebView webView;
    RadioButton linItemTabPpob;
    RadioButton linItemTabGrosir;
    WebSettings ws;
    AlertDialog.Builder builder;
    @Nullable
    String url;

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface", "ObsoleteSdkInt", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmcgweb);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        url = PreferenceClass.getUser().getGrosir() + PreferenceClass.getAuth();

        linItemTabPpob = findViewById(R.id.linItemTabPpob);
        linItemTabPpob.setChecked(false);
        linItemTabPpob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        linItemTabGrosir = findViewById(R.id.linItemTabGrosir);
        linItemTabGrosir.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_tab_btn_group_home));
        linItemTabGrosir.setChecked(true);

        webView = findViewById(R.id.webViewFMCG);

        ws = webView.getSettings();
        webView.setWebViewClient(new myWebClient());
        webView.setWebChromeClient(new myWebChromeClient());

        ws.setAllowFileAccess(true);
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setAllowUniversalAccessFromFileURLs(true);
        ws.setAllowFileAccessFromFileURLs(true);

        // Enable plugins
        ws.setPluginState(WebSettings.PluginState.ON);

        // Increase the priority of the rendering thread to high
        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);

        // Enable application caching
        ws.setAppCacheEnabled(true);

        ws.setAllowContentAccess(true);

//        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT > 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            //   ws.setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");
        } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 19) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            //   ws.setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; Nexus 5 Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode( ws.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        try {
            // Log.d(TAG, "Enabling HTML5-Features");
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
        webView.addJavascriptInterface(this, "Android");
//        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        String unencodedHtml =
                "&lt;html&gt;&lt;body&gt;'%23' is the percent code for ‘#‘ &lt;/body&gt;&lt;/html&gt;";
        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                Base64.NO_PADDING);
        webView.loadData(encodedHtml, "text/html", "base64");
        builder = new AlertDialog.Builder(FMCGWebActivity.this);
        try {
            URL verifiedUrl = UrlUtils.verifyUrl(url);
            if (verifiedUrl != null) {
                UrlUtils.trustEveryone();
                //    webView.loadDataWithBaseURL("","https://fonts.googleapis.com/css?family=Oxygen:400,300,700","text/html", "utf-8",null);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Code for WebView goes here
                        webView.loadData("file:///android_asset/fonts/",
                                "text/*", "utf-8");
                        webView.loadUrl(url);
                    }
                });


            } else {

                builder.setMessage(R.string.notification_error_ssl_cert_invalid);
                builder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//
                        finish();
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();

            }
        } catch (Exception ex) {
            Log.d(TAG, "onCreate: " + ex.toString());
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            // PreferenceClass.setLocked();
            //  finishAffinity();
            finish();

        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)

        return super.onKeyDown(keyCode, event);
    }

    public class myWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, @NonNull JsResult result) {
            Log.d(TAG, "onJsAlert: " + url + " " + message + " " + result.toString());
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onConsoleMessage(@NonNull ConsoleMessage consoleMessage) {
            Log.d(TAG, "onConsoleMessage: " + consoleMessage.message());
            //loadToWebView(url);
            return super.onConsoleMessage(consoleMessage);
        }

    }

    public class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            Log.d(TAG, "onPageStarted: " + url);
            // loadToWebView(url);
//            loadingView.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(@NonNull final WebView view, final String url) {
            try {
                URL verifiedUrl = UrlUtils.verifyUrl(url);
                if (verifiedUrl != null) {
                    UrlUtils.trustEveryone();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.loadUrl(url);
                        }
                    });

                } else {
                    builder.setMessage(R.string.notification_error_ssl_cert_invalid);
                    builder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//
                            finish();
                        }
                    });

                    final AlertDialog dialog = builder.create();
                    dialog.show();

                }
            } catch (Exception ex) {
                Log.d(TAG, "shouldOverrideUrlLoading: " + ex.toString());
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d(TAG, "onPageFinished: ");
            super.onPageFinished(view, url);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(FMCGWebActivity.this);
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

//    private URL verifyUrl(String url) {
////        if (!url.toLowerCase().startsWith("http://")) {
////            return null;
////        }
//        URL verifiedUrl;
//        try {
//            verifiedUrl = new URL(url);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        return verifiedUrl;
//    }

//    private void trustEveryone() {
//        try {
//            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//
//                public boolean verify(String hostname, SSLSession session) {
//                    return hostname.equalsIgnoreCase("mpdesktop.fastpay.co.id") ||
//                            hostname.equalsIgnoreCase("www.fastpay.co.id") ||
//                            hostname.equalsIgnoreCase("www.fpkita.net") ||
//                            hostname.equalsIgnoreCase("tomosbf.fastpay.co.id") ||
//                            hostname.equalsIgnoreCase("tomofastpay.id") ||
//                            hostname.equalsIgnoreCase("pinjemdoku.id") ||
//                            hostname.equalsIgnoreCase("dashboard.fastpay.co.id");
////                    return true;
//                }
//            });
//            SSLContext context = SSLContext.getInstance("TLSv1.2");
//            context.init(null, new X509TrustManager[]{new X509TrustManager() {
//                @SuppressLint("TrustAllX509TrustManager")
//                public void checkClientTrusted(X509Certificate[] chain,
//                                               String authType) {
//                }
//
//                @SuppressLint("TrustAllX509TrustManager")
//                public void checkServerTrusted(X509Certificate[] chain,
//                                               String authType) {
//                }
//
//                public X509Certificate[] getAcceptedIssuers() {
//                    return new X509Certificate[0];
//                }
//            }}, new SecureRandom());
//            //SSLEngine engine = context.createSSLEngine();
////            HttpsURLConnection.setDefaultHostnameVerifier();
//            HttpsURLConnection.setDefaultSSLSocketFactory(
//                    context.getSocketFactory());
//            // engine.setEnabledProtocols();
//        } catch (Exception e) { // should never happen
//            e.printStackTrace();
//        }
//    }

    private void loadToWebView(@NonNull String url) {
        AssetManager assetManager = getAssets();
        try {
            InputStream input = assetManager.open(url);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
            // webView.loadDataWithBaseURL("file:///android_asset/fonts/Roboto-Regular.ttf",
            webView.loadData("file:///android_asset/fonts/",
                    "text/*", "utf-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d(TAG, "loadToWebView: " + e);
        }
    }
}
