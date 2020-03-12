package com.reciter.lib;

import android.content.Context;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
//import com.reciter.lib.INominalRecitation;

public class PlayListReciter implements INominalRecitation,MediaPlayer.OnCompletionListener
{
  private Context mContext;
  boolean mIsPlaying = false;
  @NonNull
  private MediaPlayer[] mPlayers = new MediaPlayer[0];
  private INominalRecitation.NominalRecitationListener mRecitationListener;
  int mSoundStack;
  
  public PlayListReciter(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void Recite(@NonNull String[] paramArrayOfString)
  {
    Stop();
    this.mIsPlaying = true;
    if (this.mRecitationListener != null) {
      this.mRecitationListener.OnStartRecite(this);
    }
    this.mSoundStack = 0;
    this.mPlayers = new MediaPlayer[paramArrayOfString.length];
    int m = paramArrayOfString.length;
    int i = 0;
    int k;
    for (int j = i;; j = k)
    {
      if (i >= m)
      {
        if (j > 0) {
          this.mPlayers[0].start();
        }
        return;
      }
      String str = paramArrayOfString[i];
      k = j;
      if (!str.equals(""))
      {
        this.mPlayers[j] = MediaPlayer.create(this.mContext, SoundHelper.getRawId(str));
        this.mPlayers[j].setOnCompletionListener(this);
        if (j > 0) {
          this.mPlayers[(j - 1)].setNextMediaPlayer(this.mPlayers[j]);
        }
        k = j + 1;
      }
      i += 1;
    }
  }
  
  public void Stop()
  {
    this.mSoundStack = 0;
    this.mIsPlaying = false;
  }
  
  public void onCompletion(@NonNull MediaPlayer paramMediaPlayer)
  {
    paramMediaPlayer.release();
    this.mSoundStack += 1;
  }
  
  public void setNominalRecitationListener(INominalRecitation.NominalRecitationListener paramNominalRecitationListener)
  {
    this.mRecitationListener = paramNominalRecitationListener;
  }
}

