package com.bm.main.fpl.templates;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sarifhidayat on 9/13/18.
 **/
public class RelativeLayoutTouchListener implements View.OnTouchListener {

    static final String logTag = "ActivitySwipeDetector";
    private AppCompatActivity activity;

    OnSwipeLayout listener;

    static final int MIN_DISTANCE = 100;// TODO change this runtime based on screen resolution. for 1920x1080 is to small the 100 distance
    private float downX, downY, upX, upY;

    // private MainActivity mMainActivity;

    public RelativeLayoutTouchListener(AppCompatActivity mainActivity,OnSwipeLayout listener) {
        activity = mainActivity;
        this.listener=listener;
    }

    public static int RightToLeftSwipe = 0;
    public static int LeftToRightSwipe = 1;
    public static int onTopToBottomSwipe = 2;
    public static int onBottomToTopSwipe = 3;
//
//    /**
//     * This method RelativeLayoutTouchListener.
//     *
//     * @param touch  0= RightToLeftSwipe,
//     *               1= LeftToRightSwipe,
//     *               2= onTopToBottomSwipe,
//     *               3= onBottomToTopSwipe
//     *
//     */

    public void onRightToLeftSwipe() {
      //  Log.i(logTag, "RightToLeftSwipe!");
//        Toast.makeText(activity, "RightToLeftSwipe", Toast.LENGTH_SHORT).show();
        // activity.doSomething();
        listener.OnSwipeLayoutCustom(RightToLeftSwipe);
    }

    public void onLeftToRightSwipe() {
       // Log.i(logTag, "LeftToRightSwipe!");
//        Toast.makeText(activity, "LeftToRightSwipe", Toast.LENGTH_SHORT).show();
        // activity.doSomething();
        listener.OnSwipeLayoutCustom(LeftToRightSwipe);
    }

    public void onTopToBottomSwipe() {
      //  Log.i(logTag, "onTopToBottomSwipe!");
//        Toast.makeText(activity, "onTopToBottomSwipe", Toast.LENGTH_SHORT).show();
        // activity.doSomething();
        listener.OnSwipeLayoutCustom(onTopToBottomSwipe);
    }

    public void onBottomToTopSwipe() {
       // Log.i(logTag, "onBottomToTopSwipe!");
//        Toast.makeText(activity, "onBottomToTopSwipe", Toast.LENGTH_SHORT).show();
        // activity.doSomething();
        listener.OnSwipeLayoutCustom(onBottomToTopSwipe);
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouch(View v, @NonNull MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // swipe horizontal?
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        this.onLeftToRightSwipe();
                        return true;
                    }
                    if (deltaX > 0) {
                        this.onRightToLeftSwipe();
                        return true;
                    }
                } else {
                    Log.d(logTag, "Swipe was only " + Math.abs(deltaX) + " long horizontally, need at least " + MIN_DISTANCE);
                    // return false; // We don't consume the event
                }

                // swipe vertical?
                if (Math.abs(deltaY) > MIN_DISTANCE) {
                    // top or down
                    if (deltaY < 0) {
                        this.onTopToBottomSwipe();
                        return true;
                    }
                    if (deltaY > 0) {
                        this.onBottomToTopSwipe();
                        return true;
                    }
                } else {
                    Log.d(logTag, "Swipe was only " + Math.abs(deltaX) + " long vertically, need at least " + MIN_DISTANCE);
                    // return false; // We don't consume the event
                }

                return false; // no swipe horizontally and no swipe vertically
            }// case MotionEvent.ACTION_UP:
        }
        return false;
    }

    public interface OnSwipeLayout {
         void OnSwipeLayoutCustom(int touch);
    }

}
