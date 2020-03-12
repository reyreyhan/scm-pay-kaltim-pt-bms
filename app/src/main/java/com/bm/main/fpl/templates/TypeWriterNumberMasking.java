package com.bm.main.fpl.templates;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.util.AttributeSet;

import com.bm.main.fpl.templates.card_edittext.CreditCardEditText;

/**
 * Created by sarifhidayat on 2/13/18.
 **/

public class TypeWriterNumberMasking extends CreditCardEditText {
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 50; // in ms
    public TypeWriterNumberMasking(Context context) {
        super(context);
    }
    public TypeWriterNumberMasking(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @NonNull
    private Handler mHandler = new Handler();
    @NonNull
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));
            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };
    public void animateText(CharSequence txt) {
        mText = txt;
        mIndex = 0;
        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }
    public void setCharacterDelay(long m) {
        mDelay = m;
    }
}
