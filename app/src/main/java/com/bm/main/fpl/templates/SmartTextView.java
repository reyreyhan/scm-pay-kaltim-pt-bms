package com.bm.main.fpl.templates;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

import com.bm.main.pos.R;

import java.util.HashMap;

//import android.support.v7.widget.AppCompatTextView;

/**
 * Created by sarifhidayat on 2/21/19.
 **/

//@SuppressLint("AppCompatCustomView")
public class SmartTextView extends androidx.appcompat.widget.AppCompatTextView {

    private static final String TAG = SmartTextView.class.getSimpleName();

    private static final HashMap<FontType, Typeface> mRegisteredFonts = new HashMap<>();

    public static void registerFont(FontType fType, Typeface fTypeface) {
        if (mRegisteredFonts.containsKey(fType)) mRegisteredFonts.remove(fType);
        mRegisteredFonts.put(fType, fTypeface);
    }
    @Nullable
    public static Typeface getFontTypeface(FontType fType) {
        return mRegisteredFonts.get(fType);
    }

    private boolean mJustified;
    private boolean mAllCaps;
    private FontType mFontType;

    private int mFirstLineTextHeight = 0;
    @NonNull
    private Rect mLineBounds = new Rect();

    public SmartTextView(@NonNull Context context) {
        super(context);
        initialize(null);
    }

    public SmartTextView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public SmartTextView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(@Nullable AttributeSet attrs) {
        int styleIndex = 0;

        if (attrs != null) {
            TypedArray attrArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.SmartTextView, 0, 0);
            if (attrArray != null) {
                mJustified = attrArray.getBoolean(R.styleable.SmartTextView_justified, false);
                mFontType = FontType.values()[attrArray.getInt(R.styleable.SmartTextView_font__, 1)];
                mAllCaps = attrArray.getBoolean(R.styleable.SmartTextView_android_textAllCaps, false);
                styleIndex = attrArray.getInt(R.styleable.SmartTextView_android_textStyle, 0);
            }
        }

        setTypeface(getFontTypeface(), styleIndex);
    }

    @Nullable
    private Typeface getFontTypeface() {
        return !mRegisteredFonts.containsKey(mFontType) ? getTypeface() : mRegisteredFonts.get(mFontType);
    }

    public void setJustified(boolean justified) {
        mJustified = justified;
    }

    public void setFontType(FontType fType) {
        mFontType = fType;
        setTypeface(getFontTypeface());
    }
    public void setFontType(FontType fType, int style) {
        mFontType = fType;
        setTypeface(getFontTypeface(), style);
    }

    @Override
    public void setAllCaps(boolean allCaps) {
        mAllCaps = allCaps;
        super.setAllCaps(allCaps);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf, int style) {
        if (tf == null) tf = getFontTypeface();
        super.setTypeface(tf, style);
    }

    public void switchText(int resId) {
        switchText(getResources().getString(resId));
    }
    public void switchText(final String newText) {
        animate().alpha(0).setDuration(150).withEndAction(new Runnable() {
            @Override public void run() {
                setText(newText);
                animate().alpha(1).setDuration(150).start();
            }
        }).start();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if (!mJustified) {
            super.onDraw(canvas);
            return;
        }

        // Manipulations to mTextPaint found in super.onDraw()...
        getPaint().setColor(getCurrentTextColor());
        getPaint().drawableState = getDrawableState();

        String fullText = mAllCaps ? getText().toString().toUpperCase() : getText().toString();

        float lineSpacing = getLineHeight();
        float drawableWidth = getDrawableWidth();

        int lineNum = 1, lineStartIndex = 0;
        int lastWordEnd, currWordEnd = 0;

        if (fullText.indexOf(' ', 0) == -1) flushWord(canvas, getPaddingTop() + lineSpacing, fullText);
        else {
            while (currWordEnd >= 0) {
                lastWordEnd = currWordEnd + 1;
                currWordEnd = fullText.indexOf(' ', lastWordEnd);

                if (currWordEnd != -1) {
                    getPaint().getTextBounds(fullText, lineStartIndex, currWordEnd, mLineBounds);

                    if (mLineBounds.width() >= drawableWidth) {
                        flushLine(canvas, lineNum, fullText.substring(lineStartIndex, lastWordEnd));
                        lineStartIndex = lastWordEnd;
                        lineNum++;
                    }

                } else {
                    getPaint().getTextBounds(fullText, lineStartIndex, fullText.length(), mLineBounds);

                    if (mLineBounds.width() >= drawableWidth) {
                        flushLine(canvas, lineNum, fullText.substring(lineStartIndex, lastWordEnd));
                        rawFlushLine(canvas, ++lineNum, fullText.substring(lastWordEnd));
                    } else {
                        if (lineNum == 1) {
                            rawFlushLine(canvas, lineNum, fullText);
                        }
                        else {
                            rawFlushLine(canvas, lineNum, fullText.substring(lineStartIndex));
                        }
                    }
                }

            }
        }
    }

    private float getDrawableWidth() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private void setFirstLineTextHeight(@NonNull String firstLine) {
        getPaint().getTextBounds(firstLine, 0, firstLine.length(), mLineBounds);
        mFirstLineTextHeight = mLineBounds.height();
    }

    private void rawFlushLine(@NonNull Canvas canvas, int lineNum, @NonNull String line) {
        if (lineNum == 1) setFirstLineTextHeight(line);

        float yLine = getPaddingTop() + mFirstLineTextHeight + (lineNum - 1) * getLineHeight();
        canvas.drawText(line, getPaddingLeft(), yLine, getPaint());
    }

    private void flushLine(@NonNull Canvas canvas, int lineNum, @NonNull String line) {
        if (lineNum == 1) setFirstLineTextHeight(line);

        float yLine = getPaddingTop() + mFirstLineTextHeight + (lineNum - 1) * getLineHeight();

        String[] words = line.split("\\s+");
        StringBuilder lineBuilder = new StringBuilder();

        for (String word : words) {
            lineBuilder.append(word);
        }

        float xStart = getPaddingLeft();
        float wordWidth = getPaint().measureText(lineBuilder.toString());
        float spacingWidth = (getDrawableWidth() - wordWidth) / (words.length - 1);

        for (String word : words) {
            canvas.drawText(word, xStart, yLine, getPaint());
            xStart += getPaint().measureText(word) + spacingWidth;
        }
    }

    private void flushWord(@NonNull Canvas canvas, float yLine, @NonNull String word) {
        float xStart = getPaddingLeft();
        float wordWidth = getPaint().measureText(word);
        float spacingWidth = (getDrawableWidth() - wordWidth) / (word.length() - 1);

        for (int i = 0; i < word.length(); i++) {
            canvas.drawText(word, i, i + 1, xStart, yLine, getPaint());
            xStart += getPaint().measureText(word, i, i + 1) + spacingWidth;
        }
    }

    public enum FontType {
        Thin, Reg, Thick
    }
}
