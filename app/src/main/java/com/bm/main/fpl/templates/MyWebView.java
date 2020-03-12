package com.bm.main.fpl.templates;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by sarifhidayat on 11/27/17.
 */

public class MyWebView extends WebView {
    @NonNull
    String TAG=MyWebView.class.getSimpleName();
    private boolean startedOverScrollingY;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        requestDisallowInterceptTouchEvent(true);

        return super.onTouchEvent(event);
    }

//    @Override
//    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
//        super.onScrollChanged(x, y, oldX, oldY);
//        final int DIRECTION_UP = 1;
//        if (!canScrollVertically(DIRECTION_UP)) {
//            // Trigger listener.
//        }
//    }
//    @Override
//    public void onOverScrolled (int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
//        if (scrollY > 0 && clampedY && !startedOverScrollingY) {
//            startedOverScrollingY = true;
//            // Trigger listener.
//            Log.d("webview", "onOverScrolled: ");
//        }
//        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//    }
//
//    @Override
//    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
//        super.onScrollChanged(x, y, oldX, oldY);
//        if (y < oldY) startedOverScrollingY = false;
//    }
//private OnScrollChangedCallback mOnScrollChangedCallback;
//
//    public MyWebView(final Context context)
//    {
//        super(context);
//    }
//
//    public MyWebView(final Context context, final AttributeSet attrs)
//    {
//        super(context, attrs);
//    }
//
//    public MyWebView(final Context context, final AttributeSet attrs, final int defStyle)
//    {
//        super(context, attrs, defStyle);
//    }
//
//    @Override
//    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt)
//    {
//        super.onScrollChanged(l, t, oldl, oldt);
//        if(mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(l, t,oldl, oldt);
//    }
//
//    public OnScrollChangedCallback getOnScrollChangedCallback()
//    {
//        return mOnScrollChangedCallback;
//    }
//
//    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback)
//    {
//        mOnScrollChangedCallback = onScrollChangedCallback;
//    }
//
//    /**
//     * Impliment in the activity/fragment/view that you want to listen to the webview
//     */
//    public static interface OnScrollChangedCallback
//    {
//        public void onScroll(int l, int t,int oldl, int oldt);
//    }
//    private boolean topReached = false, bottomReached = false;
//
//    @Override
//    protected int computeVerticalScrollRange() {
//
//        int readerViewHeight = getMeasuredHeight();
//
//        int verticalScrollRange = super.computeVerticalScrollRange();
//
//
//        if (readerViewHeight >= verticalScrollRange) {
//
//            topReached = true;
//
//            bottomReached = true;
//
//        }
//
//        return verticalScrollRange;
//    }
//
//    @Override
//    public void onScrollChanged(int newLeft, int newTop, int oldLeft, int oldTop) {
//
//        Log.d(TAG, "onScrollChanged");
//
//        topReached = false;
//
//        bottomReached = false;
//
//        Log.d(TAG, "onScrollChanged newLeft : " + newLeft);
//        Log.d(TAG, "onScrollChanged newTop : " + newTop);
//        Log.d(TAG, "onScrollChanged oldLeft : " + oldLeft);
//        Log.d(TAG, "onScrollChanged oldTop : " + oldTop);
//
//        int readerViewHeight = getMeasuredHeight();
//
//        int contentHeight = getContentHeight();
//
//        if (newTop == 0) {
//
//            Log.d(TAG, "onScrollChanged : Top End");
//
//            topReached = true;
//
//        } else if (newTop + readerViewHeight >= contentHeight) {
//
//            Log.d(TAG, "onScrollChanged : Bottom End");
//
//            bottomReached = true;
//
//        }
//
//        super.onScrollChanged(newLeft, newTop, oldLeft, oldTop);
//
//    }
}
