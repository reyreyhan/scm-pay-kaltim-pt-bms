package com.bm.main.single.ftl.utils;


import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.bm.main.fpl.constants.FCMConstants;
import com.bm.main.fpl.constants.RConfig;
import com.bm.main.fpl.handlers.BodyObjectResponseHandler;
import com.bm.main.fpl.handlers.JsonObjectResponseHandler;
import com.bm.main.fpl.handlers.JsonObjectResponsePaymentHandler;
import com.bm.main.fpl.handlers.ProgressResponseBody;
import com.bm.main.fpl.handlers.ProgressResponseHandler;
import com.bm.main.fpl.interfaces.BodyObjectResponseCallback;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.interfaces.ProgressListener;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.BuildConfig;
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestUtilsTravel
{
    @NonNull
    static String TAG= RequestUtilsTravel.class.getSimpleName();
    static  OkHttpClient client;// = new OkHttpClient();
//    static OkHttpClient.Builder builder = new OkHttpClient.Builder();
    @Nullable
    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//    public static final String url = "http://dev.apimobilesbf.fastpay.co.id/simulator/";
 //  public static  String url = PreferenceClass.getString(RConfig.API_URL_FT,"https://api.fastravel.co.id/");
   @Nullable
   public static  String url = PreferenceClass.getString(RConfig.API_URL_FT,"https://api.fastravel.co.id/");


    public static void transportWithJSONObjectResponse(@NonNull AppCompatActivity context, String uriPath, @NonNull JSONObject requestObject, int actionCode, JsonObjectResponseCallback callback)
    {

//        try {
//            URL verifiedUrl = verifyUrl(url);
//            if (verifiedUrl != null) {
//                trustEveryone();

                try
                {
                    FCMConstants.isStillRunningRequest=true;
                 //   Log.d(TAG, "transportWithJSONObjectResponse: "+FCMConstants.isStillRunningRequest);
                    RequestBody body = RequestBody.create(JSON, requestObject.toString());

                    Request request = new Request.Builder()
                            .header("Accept-Encoding", "identity")
                            .header("Content-Type", "application/json")
                            .url(url +uriPath)
                            .post(body).tag("travel")
                            .build();


                     Log.d("req", "transportWithJSONObjectResponse: "+request+" "+requestObject.toString());
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder
                            .connectTimeout(1,TimeUnit.MINUTES).
                                    readTimeout(1,TimeUnit.MINUTES).
                                    writeTimeout(1,TimeUnit.MINUTES).
                                    retryOnConnectionFailure(true);

                    if (BuildConfig.DEBUG) {
                        builder.addInterceptor(new OkHttpProfilerInterceptor());
                    }
                    client = builder.build();
                    client.newCall(request).enqueue(new JsonObjectResponseHandler(context, callback, actionCode));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

//            }
//        } catch (Exception ex) {
//            Log.e(TAG, "verify: "+ex.toString());
//        }


    }
    public static void transportWithJSONObjectResponsePayment(@NonNull AppCompatActivity context, String uriPath, @NonNull JSONObject requestObject, int actionCode, JsonObjectResponseCallback callback)
    {

//        try {
//            URL verifiedUrl = verifyUrl(url);
//            if (verifiedUrl != null) {
//                trustEveryone();

                try
                {
                    FCMConstants.isStillRunningRequest=true;
                //    Log.d(TAG, "transportWithJSONObjectResponsePayment: "+FCMConstants.isStillRunningRequest);
                    RequestBody body = RequestBody.create(JSON, requestObject.toString());

                    Request request = new Request.Builder()
                            .header("Accept-Encoding", "identity")
                            .header("Content-Type", "application/json")
                            .url(url +uriPath)
                            .post(body).tag("travel")
                            .build();


                     Log.d("req", "transportWithJSONObjectResponsePayment: "+request+" "+requestObject.toString());
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder
                            .connectTimeout(5,TimeUnit.MINUTES).
                                    readTimeout(5,TimeUnit.MINUTES).
                                    writeTimeout(5,TimeUnit.MINUTES).
                                    retryOnConnectionFailure(false);
                    if (BuildConfig.DEBUG) {
                        builder.addInterceptor(new OkHttpProfilerInterceptor());
                    }
                       client = builder.build();
                    client.newCall(request).enqueue(new JsonObjectResponsePaymentHandler(context, callback, actionCode));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

//            }
//        } catch (Exception ex) {
//            Log.e(TAG, "verify: "+ex.toString());
//        }

    }


    public static void transportWithProgressResponse(@NonNull AppCompatActivity context, String uriPath, @NonNull JSONObject requestObject, final int actionCode, @NonNull final ProgressResponseCallback callback)
    {
//        List<CipherSuite> cipherSuites = new ArrayList<>();
//        cipherSuites.addAll(ConnectionSpec.MODERN_TLS.cipherSuites());
//        cipherSuites.add(CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384);
//        cipherSuites.add(CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256);
//
//        ConnectionSpec legacyTls = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                .cipherSuites(cipherSuites.toArray(new CipherSuite[0]))
//                .build();

//        Log.d(TAG, "transportWithProgressResponse: " + requestObject.toString());
//        try {
//            URL verifiedUrl = verifyUrl(url);
//            if (verifiedUrl != null) {
//                trustEveryone();
        FCMConstants.isStillRunningRequest=true;
       // Log.d(TAG, "transportWithProgressResponse: "+FCMConstants.isStillRunningRequest);
                RequestBody body = RequestBody.create(JSON, requestObject.toString());



                Request request = new Request.Builder()
                        .header("Accept-Encoding", "identity")
                        .header("Content-Type", "application/json")
                        .url(url +uriPath)

                        .post(body)
                        .tag("travel")
                        .build();
//                final ProgressListener progressListener = new ProgressListener() {
//                    //     final ProgressResponseCallback progressListener = new ProgressResponseCallback() {
//                    @Override
//                    public void onUpdate(long bytesRead,long contentLength,boolean done) {
//              //  System.out.println(bytesRead);
//                //System.out.println(contentLength);
////                System.out.println(done);
////                System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);
////                        Log.d(TAG, "onUpdate: bytesRead "+bytesRead);
////                        Log.d(TAG, "onUpdate: contentLength "+contentLength);
////                        Log.d(TAG, "onUpdate: bytesRead done %d%% done\n" + (100 * bytesRead) / contentLength);
//                        callback.onUpdate(actionCode,bytesRead,contentLength,done);
//                    }
//                };



                Log.d("req", "transportWithProgressResponse: " + request + " " + requestObject.toString());
        OkHttpClient.Builder builder =new OkHttpClient.Builder();
                builder
//                        .connectionSpecs(Arrays.asList(legacyTls, ConnectionSpec.CLEARTEXT))
                        .addNetworkInterceptor(new Interceptor() {
                            @Override public Response intercept(Chain chain) throws IOException {
                                Response originalResponse = chain.proceed(chain.request());
                                return originalResponse.newBuilder()
                                       // .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                        .body(originalResponse.body())
                                        .build();
                            }
                        })


                        .connectTimeout(3, TimeUnit.MINUTES).
                                readTimeout(3,TimeUnit.MINUTES).
                                writeTimeout(3,TimeUnit.MINUTES).
                                retryOnConnectionFailure(true);

//        client.newCall(request).enqueue(callback);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new OkHttpProfilerInterceptor());
        }
         client = builder.build();
                client.newCall(request).enqueue(new ProgressResponseHandler(context, callback, actionCode));

//            }
//        } catch (Exception ex) {
//            Log.e(TAG, "verify: "+ex.toString());
//        }


    }
    public static void transportWithProgressResponseBook_Pay(@NonNull AppCompatActivity context, String uriPath, @NonNull JSONObject requestObject, final int actionCode, @NonNull final ProgressResponseCallback callback)
    {

//        try {
//            URL verifiedUrl = verifyUrl(url);
//            if (verifiedUrl != null) {
//                trustEveryone();
        FCMConstants.isStillRunningRequest=true;
      //  Log.d(TAG, "transportWithProgressResponseBook_Pay: "+FCMConstants.isStillRunningRequest);
        RequestBody body = RequestBody.create(JSON, requestObject.toString());

        Request request = new Request.Builder()
                .header("Accept-Encoding", "identity")
                .header("Content-Type", "application/json")

                .url(url +uriPath)
                .post(body)
                .tag("travel")
                .build();
        final ProgressListener progressListener = new ProgressListener() {
            //     final ProgressResponseCallback progressListener = new ProgressResponseCallback() {
            @Override
            public void onUpdate(long bytesRead,long contentLength,boolean done) {
//                System.out.println(bytesRead);
//                System.out.println(contentLength);
//                System.out.println(done);
//                System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);
                callback.onUpdate(actionCode,bytesRead,contentLength,done);
            }
        };
        Log.d("req", "transportWithProgressResponseBook_Pay: "+request+" "+requestObject.toString());
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder
                .addNetworkInterceptor(new Interceptor() {
                    @Override public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })


                .connectTimeout(3, TimeUnit.MINUTES).
                        readTimeout(3,TimeUnit.MINUTES).
                        writeTimeout(3,TimeUnit.MINUTES).
                        retryOnConnectionFailure(false);

//        client.newCall(request).enqueue(callback);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new OkHttpProfilerInterceptor());
        }
         client = builder.build();
        client.newCall(request).enqueue(new ProgressResponseHandler(context, callback, actionCode));

//            }
//        } catch (Exception ex) {
//            Log.e(TAG, "verify: "+ex.toString());
//        }


    }
    public static void transportWithJSONObjectResponseGet(@NonNull AppCompatActivity context, @NonNull String uriPath, int actionCode, final BodyObjectResponseCallback callback) {
        Request request = new Request.Builder()
                .header("Accept-Encoding", "identity")
                .header("Content-Type", "application/json")

                .url(uriPath)
                .build();
      //  client = new OkHttpClient.Builder()
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .addNetworkInterceptor(new Interceptor() {
                    @Override public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(originalResponse.body())
                                .build();
                    }
                })
       // builder = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new OkHttpProfilerInterceptor());
        }
         client = builder.build();
        client.newCall(request).enqueue(new BodyObjectResponseHandler(context, callback, actionCode));
    }

    public static void cancelTravel(){
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        OkHttpClient client=builder.build();
//        for(Call call : client.dispatcher().queuedCalls()) {
//            if(call.request().tag().equals("travel"))
//                call.cancel();
//
//        }
//        for (Call call : client.dispatcher().runningCalls()) {
//            if (call.request().tag().equals("travel"))
//                call.cancel();
//        }
        for (Call call : client.dispatcher().queuedCalls()) {
            if (call.request().tag().equals("travel"))
                call.cancel();

        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (call.request().tag().equals("travel"))
                call.cancel();
        }
    }


    private static URL verifyUrl(String url) {
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

    private static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                public boolean verify(String hostname, SSLSession session) {
                    return hostname.equalsIgnoreCase(url);
                   // return true;
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

    @Nullable
    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        RequestUtilsTravel.url = url;
    }
}
