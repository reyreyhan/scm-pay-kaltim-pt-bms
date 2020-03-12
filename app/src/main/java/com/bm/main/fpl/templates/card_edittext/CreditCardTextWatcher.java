package com.bm.main.fpl.templates.card_edittext;

import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by kauserali on 05/05/14.
 */
public class CreditCardTextWatcher implements TextWatcher {

    private final EditText mEditText;
    private final TextWatcherListener mListener;

    public interface TextWatcherListener {
        void onTextChanged(EditText view, String text);
    }

    public CreditCardTextWatcher(EditText editText, TextWatcherListener listener) {
        mEditText = editText;
        mListener = listener;

       // mEditText.setPadding(-25,0,0,0);
    }

    @Override
    public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
        Log.d("CARD", "onTextChanged: "+s.toString());
        mListener.onTextChanged(mEditText, s.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
       // Log.d("CARD", "beforeTextChanged: "+s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
     //   Log.d("CARD", "afterTextChanged: "+s.toString());
      //  mListener.onTextChanged(mEditText, s.toString());
    }
}