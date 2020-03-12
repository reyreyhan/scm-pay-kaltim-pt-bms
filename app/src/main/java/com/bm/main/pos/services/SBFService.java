package com.bm.main.pos.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.widget.RemoteViews;

import com.bm.main.pos.R;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.webview.FCMActivity;
import com.bm.main.pos.feature.drawer.DrawerActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.NotificationTarget;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import timber.log.Timber;

public class SBFService extends FirebaseMessagingService {

    //  public static final int ID_BIG_NOTIFICATION =  getRequestCode();//234;
    //  public static final int ID_SMALL_NOTIFICATION = getRequestCode();//235;
    private static final String TAG = SBFService.class.getSimpleName();

    int id = (int) System.currentTimeMillis();
    //    private NotificationTarget notificationTarget;
    public static final String NOTIFICATION_CHANNEL_ID = String.valueOf((int) System.currentTimeMillis());
    RemoteViews contentViewBig, contentViewSmall, contentViewDeposit;
    private NotificationTarget notificationTarget;
    private int layoutId;

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Timber.d("onDeletedMessages: ");
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Timber.d("onMessageSent: ");
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        Timber.d("onSendError: ");
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Timber.e("onNewToken: " + s);
        PreferenceClass.saveDeviceToken(s);
    }

    public SBFService() {
        super();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Timber.e("I'm in!!!%s", remoteMessage.getData().size());
        if (remoteMessage.getData().size() > 0) {

            Timber.e("Data Payload: %s", remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (JSONException e) {
                Timber.e(e);
            }
        }

//        try {
//            RemoteMessage.Notification notif = remoteMessage.getNotification();
//            JSONObject data = new JSONObject(), json = new JSONObject();
//            data.put("title", notif.getTitle());
//            data.put("message", notif.getBody());
//            data.put("image", notif.getImageUrl() == null ? "" : notif.getImageUrl().toString());
//
//            json.put("data", data);
//            sendPushNotification(json);
//            Timber.e("notif data: %s", data.toString());
//        } catch (Exception e1) {
//            Timber.e(e1);
//        }
    }

    private void sendPushNotification(@NonNull JSONObject json) {
        //optionally we can display the json into log
        Timber.e("Notification JSON %s", json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            final String saldo = data.optString("saldo", "");
            final String info_saldo = data.optString("info_saldo", "");
            final String key = data.optString("key", "");
            final String title = data.optString("title", "");
            final String message = data.optString("message", "");
            final String imageUrl = data.optString("image", "");
            final String url = data.optString("url", "");

            contentViewBig = new RemoteViews(getPackageName(), R.layout.custom_notification);
            contentViewBig.setTextViewText(R.id.title, title);
            contentViewBig.setTextViewText(R.id.text, message);

            contentViewSmall = new RemoteViews(getPackageName(), R.layout.custom_notification_small);
            contentViewSmall.setTextViewText(R.id.title, title);
            contentViewSmall.setTextViewText(R.id.text, message);

            contentViewDeposit = new RemoteViews(getPackageName(), R.layout.custom_notification_deposit);
            contentViewDeposit.setTextViewText(R.id.title, title);
            contentViewDeposit.setTextViewText(R.id.text, message);
            contentViewDeposit.setTextViewText(R.id.subtext, info_saldo);

            if (!key.equals("")) {
                Intent intent = new Intent("BROADCAST_KEY");
                // Put the random number to intent to broadcast it
                intent.putExtra("key", key);

                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            } else if (!saldo.equals("")) {
                Timber.d("sendPushNotification: %s", saldo);
                Intent intent = new Intent("BROADCAST_TOPUP");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                showDepositNotification(title, message, info_saldo, intent);
            } else if (imageUrl.equals("") && url.equals("")) {
                //displaying small notificatio
                Intent intent = new Intent(ACTIVITY_SERVICE);
                showSmallNotification(title, message, intent);
            } else {
                //creating an intent for the notification
                Intent intent = new Intent(getApplicationContext(), FCMActivity.class);
                intent.putExtra("url", url);
                Timber.e("notif url: %s", url);
                //intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                if (imageUrl.equals("null") || imageUrl.equals("")) {
                    //displaying small notification
                    showSmallNotification(title, message, intent);
                } else {
                    showBigNotification(title, message, imageUrl, intent);
                }
            }
        } catch (JSONException e) {
            Timber.e(e);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public void showBigNotification(String title, String message, final String imageUrl, Intent intent) {
        int defaults = 0;
        //  Log.d(TAG, "showDepositNotification: showBigNotification "+isForeground(getApplicationContext().getPackageName()));
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;
        defaults = defaults | Notification.PRIORITY_MAX;

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
                .bigPicture(
                        getBitmapFromURL(imageUrl))
                .setBigContentTitle(title)
                .setSummaryText(message);
        Log.d(TAG, "showBigNotification: " + isForeground(getApplicationContext().getPackageName()));
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        id,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification;
        notification = mBuilder
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setContent(contentViewBig)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setSmallIcon(R.drawable.ic_logo_status_bar)
                .setLargeIcon(getBitmapFromURL(imageUrl))
                .setTicker(title)
                .setAutoCancel(true).setWhen(id)
                .setFullScreenIntent(resultPendingIntent, true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setCustomContentView(contentViewBig)
                .setStyle(bigPictureStyle)
                .setContentText(message)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        // set big content view for newer androids
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = contentViewBig;
            notification.largeIcon = getBitmapFromURL(imageUrl);
        }

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, title, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLightColor(R.color.md_red_500);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(id, notification);

        notificationTarget = new NotificationTarget(
                this,
                R.id.image_pic,
                contentViewBig,

                notification,
                id);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Glide
                        .with(SBFService.this) // safer!
                        .asBitmap()
                        .load(imageUrl)
                        .fitCenter()
                        .placeholder(R.drawable.ic_logo_color)
                        .error(R.drawable.ic_logo_color)
                        .encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).
                        diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(notificationTarget);
            }
        });

        playNotificationSound(this);
    }

    public void showSmallNotification(String title, String message, Intent intent) {
        int defaults = 0;
//        Log.d(TAG, "showDepositNotification: showSmallNotification " + isForeground(getApplicationContext().getPackageName()));
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;
        defaults = defaults | Notification.PRIORITY_MAX;
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        id,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification;
        notification = mBuilder
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setContent(contentViewSmall).setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setSmallIcon(R.drawable.ic_logo_status_bar)
                .setTicker(title)
                .setAutoCancel(true).setWhen(id)
                .setFullScreenIntent(resultPendingIntent, true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setCustomContentView(contentViewSmall)
                .setContentText(message)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, title, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.md_red_500);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(id, notification);
        playNotificationSound(this);
    }


    public void showDepositNotification(String title, String message, String subText, Intent intent) {
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;
        defaults = defaults | Notification.PRIORITY_MAX;

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        id,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification;
        notification = mBuilder
                .setChannelId(NOTIFICATION_CHANNEL_ID)

                .setContent(contentViewDeposit).setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setSmallIcon(R.drawable.ic_logo_status_bar)
                .setTicker(title, contentViewDeposit)
                .setAutoCancel(true).setWhen(id)
                .setFullScreenIntent(resultPendingIntent, true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setCustomBigContentView(contentViewDeposit)
                .setContentText(message)
                .setSubText(subText)
                .setPriority(Notification.PRIORITY_MAX)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, title, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.md_red_500);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(id, notification);
        playNotificationSound(this);
    }

    //The method will return Bitmap from an image URL
    @Nullable
    private Bitmap getBitmapFromURL(String strURL) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        try {
            URL url = new URL(strURL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isForeground(String myPackage) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        ComponentName componentInfo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            componentInfo = runningTaskInfo.get(0).topActivity;
        }
        return componentInfo.getPackageName().equals(myPackage);
    }

    public static int getRequestCode() {
        Random rnd = new Random();
        return 100 + rnd.nextInt(900000);
    }

    public void playNotificationSound(Context context) {
        try {
//              Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Uri notification = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.fastpay_notif);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
