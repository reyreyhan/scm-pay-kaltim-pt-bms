package com.bm.main.fpl.templates.card_edittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

import com.bm.main.pos.R;
import com.bm.main.materialedittext.MaterialEditText;

import java.util.List;

/**
 * Created by kauserali on 05/05/14.
 */
public class CreditCardEditText extends MaterialEditText implements CreditCardTextWatcher.TextWatcherListener {

    private static final String SEPARATOR = "-";

    /**
     * Default minimum and maximum card length.
     */
    private static final int MINIMUM_CREDIT_CARD_LENGTH = 13;
    private static final int MAXIMUM_CREDIT_CARD_LENGTH = 19;

    /**
     * List of CreditCard objects containing the image to display
     * and the regex for pattern matching.
     */
    private List<CreditCard> mListOfCreditCardChecks;

    /**
     * This drawable is shown by default and when no match is found
     */
    @Nullable
    private Drawable mNoMatchFoundDrawable;
    @Nullable
    private CreditCard mCurrentCreditCardMatch;
    private CreditCartEditTextInterface mCreditCardEditTextInterface;
    private CreditCardTextWatcher mTextWatcher;

    private int mMinimumCreditCardLength, mMaximumCreditCardLength;
    private String mPreviousText;


//    private CharSequence mText;
//    private int mIndex;
//    private long mDelay = 150; // in ms

    public interface CreditCartEditTextInterface {
        List<CreditCard> mapOfRegexStringAndImageResourceForCreditCardEditText(CreditCardEditText creditCardEditText);
    }

    public CreditCardEditText(Context context) {
        super(context);
        init();
    }

    public CreditCardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CreditCardEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Nullable
    public Drawable getNoMatchFoundDrawable() {
        return mNoMatchFoundDrawable;
    }

    public void setNoMatchFoundDrawable(@Nullable Drawable noMatchFoundDrawable) {
        if (noMatchFoundDrawable != null) {
            mNoMatchFoundDrawable = noMatchFoundDrawable;
            mNoMatchFoundDrawable.setBounds( mNoMatchFoundDrawable.getIntrinsicWidth(), 0, mNoMatchFoundDrawable.getIntrinsicWidth()+mNoMatchFoundDrawable.getIntrinsicHeight(), mNoMatchFoundDrawable.getIntrinsicHeight());
            showRightDrawable(null);
        }
    }

    public void setCreditCardEditTextListener(CreditCartEditTextInterface creditCartEditTextInterface) {
        mCreditCardEditTextInterface = creditCartEditTextInterface;
        if (mCreditCardEditTextInterface != null) {
            mListOfCreditCardChecks = mCreditCardEditTextInterface.mapOfRegexStringAndImageResourceForCreditCardEditText(this);
        }
    }

    @Nullable
    public String getTypeOfSelectedCreditCard() {
        if (mCurrentCreditCardMatch != null) {
            return mCurrentCreditCardMatch.getType();
        }
        return null;
    }

    public int getMaximumCreditCardLength() {
        return mMaximumCreditCardLength;
    }

    public void setMaximumCreditCardLength(int maximumCreditCardLength) {
        mMaximumCreditCardLength = maximumCreditCardLength;
    }

    public int getMinimumCreditCardLength() {
        return mMinimumCreditCardLength;
    }

    public void setMinimumCreditCardLength(int minimumCreditCardLength) {
        mMinimumCreditCardLength = minimumCreditCardLength;
    }

    @Nullable
    public String getCreditCardNumber() {
        String creditCardNumber = String.valueOf(getText()).replace(SEPARATOR, "");
        if (creditCardNumber.length() >= mMinimumCreditCardLength && creditCardNumber.length() <= mMaximumCreditCardLength) {
            return creditCardNumber;
        }
        return null;
    }

    @Override
    public void onTextChanged(EditText view, @NonNull String text) {
        matchRegexPatternsWithText(text.replace(SEPARATOR, ""));

        if (mPreviousText != null && text.length() > mPreviousText.length()) {
            String difference = StringUtil.difference(text, mPreviousText);
            if (!difference.equals(SEPARATOR)) {
                addSeparatorToText();
            }
        }
        mPreviousText = text;
    }

    public static class CreditCard {
        @Nullable
        private String mRegexPattern;
        @Nullable
        private Drawable mDrawable;
        @Nullable
        private String mType;

        CreditCard(@Nullable String regexPattern, @Nullable Drawable drawable, @Nullable String type) {
            if (regexPattern == null || drawable == null || type == null) {
                throw new IllegalArgumentException();
            }
            mRegexPattern = regexPattern;
            mDrawable = drawable;
            mType = type;
        }

        @Nullable
        String getRegexPattern() {
            return mRegexPattern;
        }

        @Nullable
        public Drawable getDrawable() {
            return mDrawable;
        }

        @Nullable
        public String getType() {
            return mType;
        }
    }

    private void init() {
        mMinimumCreditCardLength = MINIMUM_CREDIT_CARD_LENGTH;
        mMaximumCreditCardLength = MAXIMUM_CREDIT_CARD_LENGTH;
        int DEFAULT_NO_MATCH_FOUND_DRAWABLE = R.drawable.credit_card;
        mNoMatchFoundDrawable = getResources().getDrawable(DEFAULT_NO_MATCH_FOUND_DRAWABLE);
        mNoMatchFoundDrawable.setBounds(mNoMatchFoundDrawable.getIntrinsicWidth(), 0, mNoMatchFoundDrawable.getIntrinsicWidth()+mNoMatchFoundDrawable.getIntrinsicHeight(), mNoMatchFoundDrawable.getIntrinsicHeight());

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        params.setMargins(0, 0, 0, 0);
//        mNoMatchFoundDrawable.setLayoutParams(params);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 0, 25, 0);
//        mNoMatchFoundDrawable.setLayoutParams(lp);
        setInputType(InputType.TYPE_CLASS_NUMBER);

        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mNoMatchFoundDrawable, getCompoundDrawables()[3]);
      //  setCompoundDrawablePadding(25);
        if (mCreditCardEditTextInterface != null) {
            mListOfCreditCardChecks = mCreditCardEditTextInterface.mapOfRegexStringAndImageResourceForCreditCardEditText(this);
        }
        mTextWatcher = new CreditCardTextWatcher(this, this);
        addTextChangedListener(mTextWatcher);
        setCreditCardEditTextListener(new CreditCardPatterns(getContext()));
    }

    private void addSeparatorToText() {
        String text = String.valueOf(getText());
        text = text.replace(SEPARATOR, "");
        if (text.length() >= 16) {
            return;
        }
        int interval = 4;
        char separator = SEPARATOR.charAt(0);

        StringBuilder stringBuilder = new StringBuilder(text);
        for (int i = 0; i < text.length() / interval; i++) {
            stringBuilder.insert(((i + 1) * interval) + i, separator);
        }
        removeTextChangedListener(mTextWatcher);
        setText(stringBuilder.toString());
        setSelection(String.valueOf(getText()).length());
        addTextChangedListener(mTextWatcher);
    }

    private void matchRegexPatternsWithText(@NonNull String text) {
        if (mListOfCreditCardChecks != null && mListOfCreditCardChecks.size() > 0) {
            Drawable drawable = null;
            for (CreditCard creditCard : mListOfCreditCardChecks) {
                String regex = creditCard.getRegexPattern();
                if (text.matches(regex)) {
                    mCurrentCreditCardMatch = creditCard;
                    drawable = creditCard.getDrawable();
                    break;
                }
            }
            showRightDrawable(drawable);
        }
    }

    private void showRightDrawable(@Nullable Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(drawable.getIntrinsicWidth(), 0, drawable.getIntrinsicWidth()+drawable.getIntrinsicHeight(), drawable.getIntrinsicHeight());
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], drawable, getCompoundDrawables()[3]);
        } else {
            mCurrentCreditCardMatch = null;
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mNoMatchFoundDrawable, getCompoundDrawables()[3]);
        }
    }

//    private Handler mHandler = new Handler();
//    private Runnable characterAdder = new Runnable() {
//        @Override
//        public void run() {
//            setText(mText.subSequence(0, mIndex++));
//            if (mIndex <= mText.length()) {
//                mHandler.postDelayed(characterAdder, mDelay);
//            }
//        }
//    };
//    public void animateText(CharSequence txt) {
//        mText = txt;
//        mIndex = 0;
//        setText("");
//        mHandler.removeCallbacks(characterAdder);
//        mHandler.postDelayed(characterAdder, mDelay);
//    }
//    public void setCharacterDelay(long m) {
//        mDelay = m;
//    }
}
