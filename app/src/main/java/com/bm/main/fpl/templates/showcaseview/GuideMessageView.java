package com.bm.main.fpl.templates.showcaseview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import android.text.Spannable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;

/**
 * Created by Mohammad Reza Eram  on 20/01/2018.
 */

class GuideMessageView extends LinearLayout {

    private Paint mPaint;
    private RectF mRect;
    private final Typeface tfHalter;

    private TextView mTitleTextView;
    private TextView mContentTextView;
    private int boxBackgroundColor = Color.WHITE;
    private int titleColor = Color.BLACK;
    private int descriptiveColor = Color.BLACK;
    float density;
    int screenWidth;


    GuideMessageView(@NonNull Context context) {
        super(context);

        density = context.getResources().getDisplayMetrics().density;
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        tfHalter = ResourcesCompat.getFont(context, R.font.poppins_light);
        setWillNotDraw(false);

        mRect = new RectF();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        final int padding = (int) (10 * density);
        final int paddingBetween = (int) (3 * density);


        int newCalculatedWidth = (int)((double)screenWidth / 1.5);
        mTitleTextView = new TextView(context);
        mTitleTextView.setPadding(padding, padding, padding, paddingBetween);
        mTitleTextView.setGravity(Gravity.CENTER);
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
       // mTitleTextView.setTextSize(R.dimen.textNormalCaption);
        mTitleTextView.setTextColor(titleColor);
        mTitleTextView.setTypeface(tfHalter);
        addView(mTitleTextView, new LayoutParams(newCalculatedWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

        mContentTextView = new TextView(context);
        mContentTextView.setTextColor(descriptiveColor);
        mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
//        mContentTextView.setTextSize(R.dimen.textNormal);
        mContentTextView.setPadding(padding, paddingBetween, padding, padding);
        mContentTextView.setGravity(Gravity.CENTER);
        mContentTextView.setTypeface(tfHalter);
        addView(mContentTextView, new LayoutParams(newCalculatedWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    public void setTitle(@Nullable String title) {
        if (title == null|| title.isEmpty()) {
            removeView(mTitleTextView);
            return;
        }
        mTitleTextView.setText(title);
    }

    public void setTitleColor(int color){
        titleColor = color;
    }

    public void setDescriptiveColor(int color){
        descriptiveColor = color;
    }
    public void setContentText(String content) {
        mContentTextView.setText(content);
    }

    public void setContentSpan(Spannable content) {
        mContentTextView.setText(content);
    }

    public void setContentTypeFace(Typeface typeFace) {
        mContentTextView.setTypeface(typeFace);
    }

    public void setTitleTypeFace(Typeface typeFace) {
        mTitleTextView.setTypeface(typeFace);
    }

    public void setTitleTextSize(int size) {
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setContentTextSize(int size) {
        mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setColor(int color) {

        mPaint.setAlpha(255);
        mPaint.setColor(color);

        invalidate();
    }

    @NonNull
    int location[] = new int[2];

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        this.getLocationOnScreen(location);
        mRect.set(getPaddingLeft(),
                getPaddingTop(),
                getWidth() - getPaddingRight(),
                getHeight() - getPaddingBottom());


        canvas.drawRoundRect(mRect, 15, 15, mPaint);
    }
}
