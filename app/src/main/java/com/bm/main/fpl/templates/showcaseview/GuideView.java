package com.bm.main.fpl.templates.showcaseview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.Spannable;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;

import timber.log.Timber;

public class GuideView extends LinearLayout {

    private static final String TAG = "GuideViewActions";
    @NonNull
    private final Context mContext;
    private TextView textViewNext, textViewPrevious;
    private boolean showArrows = false;
    private boolean isCircle = false;
    private int circleRadius = 0;
//    private boolean isClickable = false;
    private int backgroundColor = 0xdd000000;
    private float density;
    private int messageXOffset = 0;
    private int messageYOffset = 0;
    private int showcasePadding = 0;
    private View target;
    private RectF rect;
    private GuideMessageView mMessageView;
    private boolean isTop;
    private Gravity mGravity;
    private DismissType dismissType;
    int marginGuide;
    private boolean mIsShowing;
    private GuideListener mGuideListener;
    private ArrowClickListener arrowClickListener;
    int xMessageView = 0;
    int yMessageView = 0;

    final int ANIMATION_DURATION = 400;
    final Paint emptyPaint = new Paint();
    final Paint paintLine = new Paint();
    final Paint paintCircle = new Paint();
    final Paint paintCircleInner = new Paint();
    final Paint mPaint = new Paint();
    final Paint targetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    final Xfermode XFERMODE_CLEAR = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    private float INDICATOR_HEIGHT;

    public interface GuideListener {
        void onDismiss(View view);
    }

    public interface ArrowClickListener {
        void onArrowClicked(String direction);
    }

    public enum Gravity {
        auto, center
    }

    public enum DismissType {
        outside, anywhere, targetView
    }

    private GuideView(@NonNull final Context context, View view) {
        super(context);
        mContext=context;
        setWillNotDraw(false);

        this.target = view;

        density = context.getResources().getDisplayMetrics().density;

        int[] locationTarget = new int[2];
        target.getLocationOnScreen(locationTarget);
        rect = new RectF(locationTarget[0], locationTarget[1],
                locationTarget[0] + target.getWidth(),
                locationTarget[1] + target.getHeight());

        mMessageView = new GuideMessageView(getContext());
        final int padding = (int) (5 * density);
        mMessageView.setPadding(padding, padding, padding, padding);
        mMessageView.setColor(Color.WHITE);
        Log.d(TAG, "MessageView added");
        addView(mMessageView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        textViewNext = new TextView(getContext());
        textViewNext.setPadding(5, 5, 5, 5);
        textViewNext.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
        textViewNext.setTextColor(Color.WHITE);
        textViewNext.setText(">");
        Timber.d("TextViewNext added");

        addView(textViewNext, textParam);

        textViewPrevious = new TextView(getContext());
        textViewPrevious.setPadding(5, 5, 5, 5);
        textViewPrevious.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 50);
        textViewPrevious.setTextColor(Color.WHITE);
        textViewPrevious.setText("<");
        Timber.d("TextView Previous added");
        addView(textViewPrevious, textParam);

        textViewNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrowClickListener != null) {
                    arrowClickListener.onArrowClicked("next");
                }
            }
        });

        textViewPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrowClickListener != null) {
                    arrowClickListener.onArrowClicked("prev");
                }
            }
        });


        setMessageLocation(resolveMessageViewLocation());

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setMessageLocation(resolveMessageViewLocation());
                int[] locationTarget = new int[2];
                target.getLocationOnScreen(locationTarget);

                int totalHeight = getHeight();
                int totalWidth = getWidth();

                setTextViewNextPosition(totalWidth, totalHeight);

                if (isCircle) {
                    if (target.getWidth() > target.getHeight()) {
                        circleRadius = target.getWidth();
                    } else if (target.getHeight() > target.getWidth()) {
                        circleRadius = target.getHeight();
                    } else {
                        circleRadius = target.getWidth();
                    }
                }

                rect = new RectF(locationTarget[0] - showcasePadding, locationTarget[1] - showcasePadding,
                        locationTarget[0] + target.getWidth() + showcasePadding, locationTarget[1] + target.getHeight() + showcasePadding);
            }
        });
    }

    private int getNavigationBarSize() {
        Resources resources = getContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    private boolean isLandscape() {
        int display_mode = getResources().getConfiguration().orientation;
        return display_mode != Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (messageYOffset == 0) {
            INDICATOR_HEIGHT = 30;
        } else {
            INDICATOR_HEIGHT = (float) messageYOffset;
        }
        Runtime.getRuntime().gc();
        if (target != null) {
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas tempCanvas = new Canvas(bitmap);

            float lineWidth = 3 * density;
            float strokeCircleWidth = 3 * density;
            float circleSize = 6 * density;
            float circleInnerSize = 5f * density;

            mPaint.setColor(ContextCompat.getColor(mContext, R.color.black_overlay_dark));
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
            tempCanvas.drawRect(canvas.getClipBounds(), mPaint);

            paintLine.setStyle(Paint.Style.FILL);
            paintLine.setColor(Color.WHITE);
            paintLine.setStrokeWidth(lineWidth);
            paintLine.setAntiAlias(true);

            paintCircle.setStyle(Paint.Style.STROKE);
            paintCircle.setColor(Color.WHITE);
            paintCircle.setStrokeCap(Paint.Cap.ROUND);
            paintCircle.setStrokeWidth(strokeCircleWidth);
            paintCircle.setAntiAlias(true);

            paintCircleInner.setStyle(Paint.Style.FILL);
            paintCircleInner.setColor(ContextCompat.getColor(mContext, R.color.md_lime_500));
            //paintCircleInner.setColor(0xffcccccc);
            paintCircleInner.setAntiAlias(true);

            marginGuide = (int) (isTop ? 15 * density : -15 * density);

            float startYLineAndCircle = (isTop ? rect.bottom : rect.top) + marginGuide;

            float x = (rect.left / 2 + rect.right / 2);
            float stopY = (yMessageView + INDICATOR_HEIGHT * density);

            tempCanvas.drawLine(x, startYLineAndCircle, x,
                    stopY
                    , paintLine);

            tempCanvas.drawCircle(x, startYLineAndCircle, circleSize, paintCircle);
            tempCanvas.drawCircle(x, startYLineAndCircle, circleInnerSize, paintCircleInner);


            targetPaint.setXfermode(XFERMODE_CLEAR);
            targetPaint.setAntiAlias(true);

            tempCanvas.drawRoundRect(rect, circleRadius, circleRadius, targetPaint);
            canvas.drawBitmap(bitmap, 0, 0, emptyPaint);
        }
    }

    public boolean isShowing() {
        return mIsShowing;
    }

    public void dismiss() {

        AlphaAnimation startAnimation = new AlphaAnimation(1f, 0f);
        startAnimation.setDuration(ANIMATION_DURATION);
        startAnimation.setFillAfter(true);
        this.startAnimation(startAnimation);
        ((ViewGroup) ((Activity) getContext()).getWindow().getDecorView()).removeView(this);
        mIsShowing = false;
        if (mGuideListener != null) {
            mGuideListener.onDismiss(target);
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        float x = event.getX();
        float y = event.getY();


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (isClickable) {
                switch (dismissType) {

                    case outside:
                        if (!isViewContains(mMessageView, x, y)) {
                            dismiss();
                        }
                        break;

                    case anywhere:
                        dismiss();
                        break;

                    case targetView:
                        if (rect.contains(x, y)) {
                            target.performClick();
                            dismiss();
                        }
                        break;
//                }
            }
            return true;
        }
        return false;

    }

    private boolean isViewContains(@NonNull View view, float rx, float ry) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int w = view.getWidth();
        int h = view.getHeight();

        return !(rx < x || rx > x + w || ry < y || ry > y + h);
    }

    void setTextViewNextPosition(int x, int y) {

        if (showArrows) {
            int xPercentageValue = ((x / 100) * 5);
            float txtNextxPoint = (float) (x - (xPercentageValue + 50));
            float txtNextyPoint = (float) (y - 300);

            textViewNext.setY(txtNextyPoint);
            textViewNext.setX(txtNextxPoint);

            float txtPrevxPoint = (float) xPercentageValue;
            float txtPrevyPoint = (float) (y - 300);

            textViewPrevious.setY(txtPrevyPoint);
            textViewPrevious.setX(txtPrevxPoint);

            Log.d(TAG, "NextXPoint: " + String.valueOf(txtNextxPoint));
        } else {
            textViewNext.setVisibility(GONE);
            textViewPrevious.setVisibility(GONE);
        }
    }

    void setMessageLocation(@NonNull Point p) {
        mMessageView.setX(p.x + messageXOffset);
        mMessageView.setY(p.y + messageYOffset);
        requestLayout();
    }


    @NonNull
    private Point resolveMessageViewLocation() {

        if (mGravity == Gravity.center) {
            xMessageView = (int) (rect.left - mMessageView.getWidth() / 2 + target.getWidth() / 2);
        } else
            xMessageView = (int) (rect.right) - mMessageView.getWidth();

        if (isLandscape()) {
            xMessageView -= getNavigationBarSize();
        }

        if (xMessageView + mMessageView.getWidth() > getWidth())
            xMessageView = getWidth() - mMessageView.getWidth();
        if (xMessageView < 0)
            xMessageView = 0;


        //set message view bottom
        if (rect.top + (INDICATOR_HEIGHT * density) > getHeight() / 2) {
            isTop = false;
            yMessageView = (int) (rect.top - mMessageView.getHeight() - INDICATOR_HEIGHT * density);
        }
        //set message view top
        else {
            isTop = true;
            yMessageView = (int) (rect.top + target.getHeight() + INDICATOR_HEIGHT * density);
        }

        if (yMessageView < 0)
            yMessageView = 0;


        return new Point(xMessageView, yMessageView);
    }


    public void show() {
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        this.setClickable(false);

        ((ViewGroup) ((Activity) getContext()).getWindow().getDecorView()).addView(this);
        AlphaAnimation startAnimation = new AlphaAnimation(0.0f, 1.0f);
        startAnimation.setDuration(ANIMATION_DURATION);
        startAnimation.setFillAfter(true);
        this.startAnimation(startAnimation);
        mIsShowing = true;

    }

    public void setTitle(String str) {
        mMessageView.setTitle(str);
    }

    public void setContentText(String str) {
        mMessageView.setContentText(str);
    }

    public void setArrows(boolean state) {
        showArrows = state;
    }

//    public void setClickable(boolean state) {
//        isClickable = state;
//    }

    public void setContentSpan(Spannable span) {
        mMessageView.setContentSpan(span);
    }

    public void setTitleTypeFace(Typeface typeFace) {
        mMessageView.setTitleTypeFace(typeFace);
    }

    public void setContentTypeFace(Typeface typeFace) {
        mMessageView.setContentTypeFace(typeFace);
    }

    public void setTitleTextSize(int size) {
        mMessageView.setTitleTextSize(size);
    }

    public void setContentTextSize(int size) {
        mMessageView.setContentTextSize(size);
    }

    public void setShowcasePadding(int padding) {
        showcasePadding = padding;
    }

    public void setXOffset(int xOffset) {
        messageXOffset = xOffset;
    }

    public void setYOffset(int yOffset) {
        messageYOffset = yOffset;
    }

    public void setBackGroundColor(int color) {
        backgroundColor = color;
    }

    public void setMessageBoxBackground(int color) {
        mMessageView.setColor(color);
    }

    public void setTitleTextColor(int color) {
        mMessageView.setTitleColor(color);
    }

    public void setDescriptiveTextColor(int color) {
        mMessageView.setDescriptiveColor(color);
    }

    public void setIsCircle(boolean value) {
        isCircle = value;
    }

    public void setCornerRadius(int value) {
        circleRadius = value;
    }

    public static class Builder {
        private View targetView;
        private boolean isCircle;
        private boolean showArrow;
        private boolean isClickable;
        private int messageBoxColor;
        private int titleColor;
        private int descriptionColor;
        private int messageXOffset;
        private int messageYOffset;
        private int showcasePadding;
        private int backgroundColor;
        private int cornerRadius;
        private String title, contentText;
        private Gravity gravity;
        private DismissType dismissType;
        private Context context;
        private int titleTextSize;
        private int contentTextSize;
        private Spannable contentSpan;
        private Typeface titleTypeFace, contentTypeFace;
        private GuideListener guideListener;
        private ArrowClickListener arrowClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        @NonNull
        public Builder setTargetView(View view) {
            this.targetView = view;
            return this;
        }

        @NonNull
        public Builder setArrows(boolean state) {
            this.showArrow = state;
            return this;
        }

        @NonNull
        public Builder setClickable(boolean state) {
            this.isClickable = state;
            return this;
        }

        @NonNull
        public Builder setArrowClickListener(ArrowClickListener arrowClickListener) {
            this.arrowClickListener = arrowClickListener;
            return this;
        }

        @NonNull
        public Builder setCircleView(boolean isCircle) {
            this.isCircle = isCircle;
            return this;
        }

        @NonNull
        public Builder setMessageBoxBackground(int color) {
            this.messageBoxColor = color;
            return this;
        }

        @NonNull
        public Builder setCornerRadius(int radius) {
            this.cornerRadius = radius;
            return this;
        }

        @NonNull
        public Builder setTitleTextColor(int color) {
            this.titleColor = color;
            return this;
        }

        @NonNull
        public Builder setDescriptionTextColor(int color) {
            this.descriptionColor = color;
            return this;
        }

        @NonNull
        public Builder setBackgroundColor(int color) {
            this.backgroundColor = color;
            return this;
        }

        @NonNull
        public Builder setGravity(Gravity gravity) {
            this.gravity = gravity;
            return this;
        }

        @NonNull
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        @NonNull
        public Builder setContentText(String contentText) {
            this.contentText = contentText;
            return this;
        }

        @NonNull
        public Builder setMessageXOffset(int messageXOffset) {
            this.messageXOffset = messageXOffset;
            return this;
        }

        @NonNull
        public Builder setMessageYOffset(int messageYOffset) {
            this.messageYOffset = messageYOffset;
            return this;
        }

        @NonNull
        public Builder setShowcasePadding(int showcasePadding) {
            this.showcasePadding = showcasePadding;
            return this;
        }


        @NonNull
        public Builder setContentSpan(Spannable span) {
            this.contentSpan = span;
            return this;
        }

        @NonNull
        public Builder setContentTypeFace(Typeface typeFace) {
            this.contentTypeFace = typeFace;
            return this;
        }

        @NonNull
        public Builder setGuideListener(GuideListener guideListener) {
            this.guideListener = guideListener;
            return this;
        }

        @NonNull
        public Builder setTitleTypeFace(Typeface typeFace) {
            this.titleTypeFace = typeFace;
            return this;
        }

        /**
         * the defined text size overrides any defined size in the default or provided style
         *
         * @param size title text by sp unit
         * @return client
         */
        @NonNull
        public Builder setContentTextSize(int size) {
            this.contentTextSize = size;
            return this;
        }

        /**
         * the defined text size overrides any defined size in the default or provided style
         *
         * @param size title text by sp unit
         * @return client
         */
        @NonNull
        public Builder setTitleTextSize(int size) {
            this.titleTextSize = size;
            return this;
        }

        @NonNull
        public Builder setDismissType(DismissType dismissType) {
            this.dismissType = dismissType;
            return this;
        }

        @NonNull
        public GuideView build() {
            GuideView guideView = new GuideView(context, targetView);
            guideView.mGravity = gravity != null ? gravity : Gravity.auto;
            guideView.dismissType = dismissType != null ? dismissType : DismissType.targetView;

            guideView.setTitle(title);
            if (contentText != null)
                guideView.setContentText(contentText);
            if (titleTextSize != 0)
                guideView.setTitleTextSize(titleTextSize);
            if (contentTextSize != 0)
                guideView.setContentTextSize(contentTextSize);
            if (contentSpan != null)
                guideView.setContentSpan(contentSpan);
            if (titleTypeFace != null) {
                guideView.setTitleTypeFace(titleTypeFace);
            }
            if (contentTypeFace != null) {
                guideView.setContentTypeFace(contentTypeFace);
            }
            if (guideListener != null) {
                guideView.mGuideListener = guideListener;
            }
            if (arrowClickListener != null) {
                guideView.arrowClickListener = arrowClickListener;
            }
            if (showcasePadding != 0) {
                guideView.setShowcasePadding(showcasePadding);
            }
            if (messageXOffset != 0) {
                guideView.setXOffset(messageXOffset);
            }
            if (messageYOffset != 0) {
                guideView.setYOffset(messageYOffset);
            }
            if (backgroundColor != 0) {
                guideView.setBackGroundColor(backgroundColor);
            }
            if (messageBoxColor != 0) {
                guideView.setMessageBoxBackground(messageBoxColor);
            }
            if (titleColor != 0) {
                guideView.setTitleTextColor(titleColor);
            }
            if (descriptionColor != 0) {
                guideView.setDescriptiveTextColor(descriptionColor);
            }
            if (isCircle) {
                guideView.setIsCircle(isCircle);
            }

            if (showArrow) {
                guideView.setArrows(showArrow);
            }
            if (isClickable) {
                guideView.setClickable(isClickable);
            }
            if (cornerRadius != 0 && !isCircle) {
                guideView.setCornerRadius(cornerRadius);
            }

            return guideView;
        }


    }
}