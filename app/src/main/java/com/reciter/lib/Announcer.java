package com.reciter.lib;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioAttributes.Builder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

public class Announcer
  implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
  //  private static final String RES_PREFIX = "android.resource://com.bm.main.fpl/";
  private static final String TAG = "mandiri";
  private Context mContext;
  private int mCounter;
  private MediaPlayer mMediaPlayer;
  private int mStack = -1;
  private String[] mWords;

  public Announcer(Context paramContext) {
    this(paramContext, null);
  }

  public Announcer(Context paramContext, @Nullable String paramString) {
    this.mContext = paramContext;
    if ((paramString != null) && (paramString.length() > 0)) {
      this.mWords = SoundHelper.setSoundStack(paramString);
      this.mStack = this.mWords.length;
    }
  }

  private void announceWord(int paramInt) {
    paramInt = SoundHelper.getRawId(this.mWords[paramInt]);
    try {
      MediaPlayer localMediaPlayer = this.mMediaPlayer;
      Context localContext = this.mContext;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("android.resource://com.bm.main.fpl/");
//      localStringBuilder.append("android.resource://" + mContext.getPackageName());
      localStringBuilder.append(paramInt);
      localMediaPlayer.setDataSource(localContext, Uri.parse(localStringBuilder.toString()));
      this.mMediaPlayer.prepare();
      //return;
    } catch (@NonNull IOException | IllegalArgumentException | SecurityException | IllegalStateException localIOException) {
      Log.e(TAG, "announceWord: ",localIOException );
    }
  }

  public void onCompletion(@NonNull MediaPlayer paramMediaPlayer) {
    paramMediaPlayer.reset();
    if (this.mCounter < this.mStack - 1) {
      this.mCounter += 1;
      announceWord(this.mCounter);
      return;
    }
    paramMediaPlayer.release();
  }

  public void onPrepared(@NonNull MediaPlayer paramMediaPlayer) {
    paramMediaPlayer.start();
  }

  public void setNominal(@Nullable String paramString) {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.mWords = SoundHelper.setSoundStack(paramString);
      this.mStack = this.mWords.length;
    }
  }

  public void startAnnounce() {
//    if (this.mStack < 0) {
//      return;
//    }
//    this.mMediaPlayer = new MediaPlayer();
//    if (Build.VERSION.SDK_INT >= 21)
//    {
//      AudioAttributes localAudioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build();
//      this.mMediaPlayer.setAudioAttributes(localAudioAttributes);
//    }
//    this.mMediaPlayer.setOnPreparedListener(this);
//    this.mMediaPlayer.setOnCompletionListener(this);
//    announceWord(this.mCounter);
//  }
    if (this.mStack >= 0) {
      this.mMediaPlayer = new MediaPlayer();
      if (VERSION.SDK_INT >= 21) {
        this.mMediaPlayer.setAudioAttributes(new Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build());
      }
      this.mMediaPlayer.setOnPreparedListener(this);
      this.mMediaPlayer.setOnCompletionListener(this);
      announceWord(this.mCounter);
    }
  }
}
