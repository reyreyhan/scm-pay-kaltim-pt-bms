package com.reciter.lib;

import android.content.Context;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;

public class StandardReciter
  implements INominalRecitation, MediaPlayer.OnCompletionListener
{
  private Context mContext;
  private boolean mIsPlaying = false;
  @NonNull
  private MediaPlayer[] mPlayers = new MediaPlayer[0];
  private INominalRecitation.NominalRecitationListener mRecitationListener;
  private int mSoundStack;
  
  public StandardReciter(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void Recite(String[] paramArrayOfString) {}
  
  public void Stop() {}
  
  public void onCompletion(MediaPlayer paramMediaPlayer) {}
  
  public void setNominalRecitationListener(INominalRecitation.NominalRecitationListener paramNominalRecitationListener)
  {
    this.mRecitationListener = paramNominalRecitationListener;
  }
}
