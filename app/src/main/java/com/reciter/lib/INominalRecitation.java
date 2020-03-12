package com.reciter.lib;

public abstract interface INominalRecitation
{
  public abstract void Recite(String[] paramArrayOfString);
  
  public abstract void Stop();
  
  public abstract void setNominalRecitationListener(NominalRecitationListener paramNominalRecitationListener);
  
  public static abstract interface NominalRecitationListener
  {
    public abstract void OnStartRecite(INominalRecitation paramINominalRecitation);
    
    public abstract void OnStopRecitation(INominalRecitation paramINominalRecitation);
  }
}

