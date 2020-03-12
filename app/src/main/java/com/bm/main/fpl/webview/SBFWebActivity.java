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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bm.main.pos.R;

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

//import android.webkit.SslErrorHandler;

public class SBFWebActivity extends AppCompatActivity {
    private static final String TAG = SBFWebActivity.class.getSimpleName();
    private  WebView webView;
      Toolbar toolbar;
    String url;

    String user, pin;
    private ProgressBar loadingView;
    //Intent a;
    private String title;
    private LinearLayout layout_no_connection, layout_data_empty;
    private AppCompatButton button_coba_lagi;
    AlertDialog.Builder builder;
    WebSettings ws;
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // webView=new WebView(this);
        setContentView(R.layout.activity_sbfweb);
//        try {
//            ProviderInstaller.installIfNeeded(getApplicationContext());
//        } catch (GooglePlayServicesRepairableException e) {
//            e.printStackTrace();
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectAll().permitAll().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        //  client.detectFileUriExposure();
        StrictMode.setVmPolicy(builder1.build());
        Intent a = getIntent();
        if (a != null) {
            url = a.getStringExtra("urlValue");
            title = a.getStringExtra("titleValue");
        }

        toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle(title);
//        init(1);
        toolbar.setTitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Title);
        //     toolbar.setSubtitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Subtitle);
        toolbar.setTitleTextColor(Color.WHITE);
        //      toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_cancel);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        loadingView = findViewById(R.id.progressBar1);
        layout_no_connection = findViewById(R.id.layout_no_connection);

        webView = findViewById(R.id.webSBF);

         ws = webView.getSettings();
        webView.setWebViewClient(new myWebClient());
        webView.setWebChromeClient(new myWebChromeClient());
        ws.setUserAgentString(url);
        ws.setAllowFileAccess(true);
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setAllowUniversalAccessFromFileURLs(true);
        ws.setAllowFileAccessFromFileURLs(true);
        ws.setAllowContentAccess(true);
//        ws.setMediaPlaybackRequiresUserGesture(false);
//        WebView.setWebContentsDebuggingEnabled(true);

//        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT > 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else if(Build.VERSION.SDK_INT >=11 && Build.VERSION.SDK_INT < 19) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
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
        builder = new AlertDialog.Builder(SBFWebActivity.this);

       // webView.setBackgroundColor(0);
//        webView.clearFormData();
//        webView.clearCache(true);
//        webView.clearHistory();
//user=preferenceClass.getLoggedUser().getId();

//        pin=preferenceClass.getLoggedUser().getPin();
//        url="http://35.187.250.137/server_side_sbf/?act=login&id_outlet="+user+"&pin="+pin;

        button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (layout_no_connection.getVisibility() == View.VISIBLE) {
                    layout_no_connection.setVisibility(View.GONE);
                }
                if (webView.getVisibility() == View.GONE) {
                    webView.setVisibility(View.VISIBLE);

                }

                finish();
                startActivity(getIntent());
            }
        });


//        if (!DetectConnection.checkInternetConnection(this)) {
////            Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
//            layout_no_connection.setVisibility(View.VISIBLE);
//            webView.setVisibility(View.GONE);
//            loadingView.setVisibility(View.GONE);

//        } else {
            layout_no_connection.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            //loadingView.setVisibility(View.VISIBLE);


          //  ws.setUserAgentString(url);
//            ws.getUserAgentString();
////            ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
////            ws.setAllowFileAccess(true);
//            ws.setJavaScriptEnabled(true);
//            ws.setDomStorageEnabled(true);
//            ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//            ws.setAllowUniversalAccessFromFileURLs(true);
//            ws.setAllowFileAccessFromFileURLs(true);
//            ws.setAllowContentAccess(true);
//            ws.setSupportZoom(true);
//            if (Build.VERSION.SDK_INT >= 21) {
//                ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//                //  ws.setAcceptThirdPartyCookies();
//            }
//            ws.getUserAgentString();
//        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
//        webView.getSettings().setAllowFileAccessFromFileURLs(true);
//              ws.setAllowFileAccess(true);
//            ws.setJavaScriptEnabled(true);
//            ws.setDomStorageEnabled(true);
//              ws.setDefaultFixedFontSize(10);
//        ws.setLoadWithOverviewMode(true);
//        ws.setSafeBrowsingEnabled(true);
//        ws.setSupportMultipleWindows(true);
//        ws.setDatabaseEnabled(true);
//            ws.setRenderPriority(WebSettings.RenderPriority.HIGH);
//            ws.setPluginState(WebSettings.PluginState.ON_DEMAND);
//            if (Build.VERSION.SDK_INT >= 19) {
////                // chromium, enable hardware acceleration
//                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//            } else {
//                // older android version, disable hardware acceleration
//                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            }


            try {
                URL verifiedUrl = verifyUrl(url);
                if (verifiedUrl != null) {
                    trustEveryone();

                    webView.loadUrl(url);

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
                Log.d(TAG, "onCreate: "+ex.toString());
            }

            webView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            webView.setLongClickable(false);
          //  webView.setHapticFeedbackEnabled(false);
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
                        Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                                Toast.LENGTH_LONG).show();
                    }
                    catch(SecurityException e)
                    {
                        Toast.makeText(getApplicationContext(),"Please grant the storage permission !",Toast.LENGTH_LONG).show();
                    } catch (UnsupportedEncodingException e) {
                        Toast.makeText(getApplicationContext(),"Unsupport Encode !",Toast.LENGTH_LONG).show();
                    }

                }
            });
//        }

    }

    //    public boolean onTouchEvent(MotionEvent, "ACTION_DOWN") {
//        try {
//            KeyEvent shiftPressEvent = new KeyEvent(0, 0, KeyEvent.ACTION_DOWN,
//                    KeyEvent.KEYCODE_SHIFT_LEFT,0,0);
//            shiftPressEvent.dispatch(webView);
//        } catch (Exception e) {
//            throw new AssertionError(e);
//        }
//        return false;
//    }
    public class myWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, @NonNull JsResult result) {
            Log.d(TAG, "onJsAlert: "+url+" "+message+" "+result.toString());
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onConsoleMessage(@NonNull ConsoleMessage consoleMessage) {
            Log.d(TAG, "onConsoleMessage: "+consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }

    }

    public class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            Log.d(TAG, "onPageStarted: ");
            loadingView.setVisibility(View.VISIBLE);
        }

//        @Override
//        public void onLoadResource(WebView view, String url) {
//            super.onLoadResource(view, url);
//            Log.d(TAG, "onLoadResource: ");
//        }
//
//        @Override
//        public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
//            Log.d(TAG, "onRenderProcessGone: ");
//            return super.onRenderProcessGone(view, detail);
//
//        }

        @Override
        public boolean shouldOverrideUrlLoading(@NonNull WebView view, String url) {
            // TODO Auto-generated method stub
            //   Log.d("UrlLoading", "shouldOverrideUrlLoading: "+url);

//            if (!DetectConnection.checkInternetConnection(SBFWebActivity.this)) {
//                // Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
//                layout_no_connection.setVisibility(View.VISIBLE);
//                webView.setVisibility(View.GONE);
//                if (loadingView.getVisibility() == View.VISIBLE) {
//                    loadingView.setVisibility(View.GONE);
//                }
//
//            } else {
                loadingView.setVisibility(View.VISIBLE);

                try {
                    URL verifiedUrl = verifyUrl(url);
                    if (verifiedUrl != null) {
                        trustEveryone();

                        view.loadUrl(url);

                    } else {
//                        final AlertDialog.Builder client = new AlertDialog.Builder(SBFWebActivity.this);
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
                    Log.d(TAG, "shouldOverrideUrlLoading: "+ex.toString());
                }

//            }
            return true;
           // return super.shouldOverrideUrlLoading(view, url);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d(TAG, "onPageFinished: ");
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            loadingView.setVisibility(View.GONE);
        }

//        @Override
//        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
//            String message = "SSL Certificate error.";
//            switch (error.getPrimaryError()) {
//                case SslError.SSL_UNTRUSTED:
//                    message = "The certificate authority is not trusted.";
//                    break;
//                case SslError.SSL_EXPIRED:
//                    message = "The certificate has expired.";
//                    break;
//                case SslError.SSL_IDMISMATCH:
//                    message = "The certificate Hostname mismatch.";
//                    break;
//                case SslError.SSL_NOTYETVALID:
//                    message = "The certificate is not yet valid.";
//                    break;
//            }
//            message += "\"SSL Certificate Error\" Do you want to continue anyway?.. YES";
//
//            handler.proceed();
//        }

//        @SuppressWarnings("deprecation")
//        @Override
//        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            // Handle the error
//            Log.d(TAG, "onReceivedError: "+errorCode+" "+description);
//        }
//
//        @TargetApi(android.os.Build.VERSION_CODES.M)
//        @Override
//        public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
//            // Redirect to deprecated method, so you can use it in all SDK versions
//            onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
//        }

//        @Override public void onReceivedError(WebView view, WebResourceRequest request,
//                                              WebResourceError error) {
//
//            // Do something
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                Log.d(TAG, "onReceivedError: "+error.getErrorCode());
//            }
//            if (DetectConnection.checkInternetConnection(SBFWebActivity.this)) {
//                final AlertDialog.Builder client = new AlertDialog.Builder(SBFWebActivity.this);
//                client.setMessage(R.string.notification_error_ssl_cert_invalid);
//                client.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////
//                        finish();
//                    }
//                });
//
//                final AlertDialog dialog = client.create();
//                dialog.show();
//            }
//            super.onReceivedError(view, request, error);
//
//        }

        @Override
        public void onReceivedSslError(WebView view, @NonNull final SslErrorHandler handler, @NonNull SslError error) {
//            String message = "SSL Certificate error.";
////            switch (error.getPrimaryError()) {
////                case SslError.SSL_UNTRUSTED:
////                    message = "The certificate authority is not trusted.";
////                    break;
////                case SslError.SSL_EXPIRED:
////                    message = "The certificate has expired.";
////                    break;
////                case SslError.SSL_IDMISMATCH:
////                    message = "The certificate Hostname mismatch.";
////                    break;
////                case SslError.SSL_NOTYETVALID:
////                    message = "The certificate is not yet valid.";
////                    break;
////            }
            Log.d(TAG, "onReceivedSslError: "+error.getPrimaryError());
//            final AlertDialog.Builder client = new AlertDialog.Builder(SBFWebActivity.this);
//            client.setMessage(R.string.notification_error_ssl_cert_invalid);
//            client.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
////                    handler.proceed();
//                    handler.cancel();
//                    finish();
//                }
//            });
////            client.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    handler.cancel();
////                    finish();
////                }
////            });
//            final AlertDialog dialog = client.create();
//            dialog.show();
//

            try {

                //Get the X509 trust manager from your ssl certificate
//                X509TrustManager trustManager = mySslCertificate.getX509TrustManager();
                X509TrustManager trustManager = new X509TrustManager() {
                    @SuppressLint("TrustAllX509TrustManager")
                    public void checkClientTrusted(X509Certificate[] chain,
                                                   String authType) {
                        Log.d(TAG, "checkClientTrusted: " + authType);
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) {
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(SBFWebActivity.this);
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


//        public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
//
//            System.out.println("error");
//            try {
//                webView.stopLoading();
//            } catch (Exception e) {
//            }
//
//            if (webView.canGoBack()) {
//                webView.goBack();
//            }
//
//            webView.loadUrl("about:blank");
//            AlertDialog alertDialog = new AlertDialog.Builder(SBFWebActivity.this).create();
//            alertDialog.setTitle("Error");
//            alertDialog.setMessage("Cek koneksi internet Anda dan Coba lagi");
//            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Coba lagi", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                    startActivity(getIntent());
//                }
//            });
//
//            alertDialog.show();
//            super.onReceivedError(webView, errorCode, description, failingUrl);
//        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();

        }
        return true;
    }

    @Override
    public void onBackPressed() {
//        if (result != null && result.isDrawerOpen())
//        {
//            result.closeDrawer();
//        }
//        else
        // {
        //  super.onBackPressed();
        // }
        //  finish();
//webView.destroy();
//        webView.clearSslPreferences();
//        webView.clearHistory();
//        webView.clearFocus();


        finish();

    }

//    @JavascriptInterface
//    public void getPin(final String stringFromWebView) {
//        Toast.makeText(this, stringFromWebView, Toast.LENGTH_SHORT).show();
//        webView.loadUrl("javascript:getAndroidToast(\"" + preferenceClass.getLoggedUser().getPin() + "\")");
//    }

    @Nullable
    private URL verifyUrl(String url) {
//        if (!url.toLowerCase().startsWith("http://")) {
//            return null;
//        }
        URL verifiedUrl;
        try {
            verifiedUrl = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return verifiedUrl;
    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                public boolean verify(@NonNull String hostname, SSLSession session) {
                    return hostname.equalsIgnoreCase("mpdesktop.fastpay.co.id") ||
                            hostname.equalsIgnoreCase("www.fastpay.co.id") ||
                            hostname.equalsIgnoreCase("www.fpkita.net") ||
                            hostname.equalsIgnoreCase("tomosbf.fastpay.co.id") ||
                            hostname.equalsIgnoreCase("tomofastpay.id") ||
                            hostname.equalsIgnoreCase("pinjemdoku.id") ||
                            hostname.equalsIgnoreCase("dashboard.fastpay.co.id");
//                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLSv1.2");
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
            //SSLEngine engine = context.createSSLEngine();
//            HttpsURLConnection.setDefaultHostnameVerifier();
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
           // engine.setEnabledProtocols();
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }

}
