package com.bm.main.fpl.templates.topsheet;

/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain da copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import android.support.design.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.os.ParcelableCompat;
import androidx.core.os.ParcelableCompatCreatorCallbacks;
//import androidx.core.view.AbsSavedState;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.VelocityTrackerCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
//import androidx.core.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.bm.main.scm.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;


/**
 * An interaction behavior plugin for da child view of {@link CoordinatorLayout} to make it work as
 * da bottom sheet.
 */
public class TopSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    /**
     * Callback for monitoring events about bottom sheets.
     */
    public abstract static class TopSheetCallback {

        /**
         * Called when the bottom sheet changes its state.
         *
         * @param bottomSheet The bottom sheet view.
         * @param newState    The new state. This will be one of {@link #STATE_DRAGGING},
         *                    {@link #STATE_SETTLING}, {@link #STATE_EXPANDED},
         *                    {@link #STATE_COLLAPSED}, or {@link #STATE_HIDDEN}.
         */
        public abstract void onStateChanged(@NonNull View bottomSheet, @State int newState);

        /**
         * Called when the bottom sheet is being dragged.
         *
         * @param bottomSheet The bottom sheet view.
         * @param slideOffset The new offset of this bottom sheet within its range, from 0 to 1
         *                    when it is moving upward, and from 0 to -1 when it moving downward.
         */
        public abstract void onSlide(@NonNull View bottomSheet, float slideOffset);
    }

    /**
     * The bottom sheet is dragging.
     */
    public static final int STATE_DRAGGING = 1;

    /**
     * The bottom sheet is settling.
     */
    public static final int STATE_SETTLING = 2;

    /**
     * The bottom sheet is expanded.
     */
    public static final int STATE_EXPANDED = 3;

    /**
     * The bottom sheet is collapsed.
     */
    public static final int STATE_COLLAPSED = 4;

    /**
     * The bottom sheet is hidden.
     */
    public static final int STATE_HIDDEN = 5;

    /**
     * @hide
     */
    @IntDef({STATE_EXPANDED, STATE_COLLAPSED, STATE_DRAGGING, STATE_SETTLING, STATE_HIDDEN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    private static final float HIDE_THRESHOLD = 0.5f;

    private static final float HIDE_FRICTION = 0.1f;

    private float mMaximumVelocity;

    private int mPeekHeight;

    private int mMinOffset;

    private int mMaxOffset;

    private boolean mHideable;

    private boolean mSkipCollapsed;

    @State
    private int mState = STATE_COLLAPSED;

    private ViewDragHelper mViewDragHelper;

    private boolean mIgnoreEvents;

    private int mLastNestedScrollDy;

    private boolean mNestedScrolled;

    private int mParentHeight;

    private WeakReference<V> mViewRef;

    @Nullable
    private WeakReference<View> mNestedScrollingChildRef;

    private TopSheetCallback mCallback;

    @Nullable
    private VelocityTracker mVelocityTracker;

    private int mActivePointerId;

    private int mInitialY;

    private boolean mTouchingScrollingChild;

    /**
     * Default constructor for instantiating TopSheetBehaviors.
     */
    public TopSheetBehavior() {
    }

    /**
     * Default constructor for inflating TopSheetBehaviors from layout.
     *
     * @param context The {@link Context}.
     * @param attrs   The {@link AttributeSet}.
     */
    public TopSheetBehavior(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.BottomSheetBehavior_Layout);
        setPeekHeight(a.getDimensionPixelSize(
                R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, 0));
        setHideable(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setSkipCollapsed(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed,
                false));
        a.recycle();
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    @Override
    public Parcelable onSaveInstanceState(@NonNull CoordinatorLayout parent, @NonNull V child) {
        return new SavedState(super.onSaveInstanceState(parent, child), mState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull CoordinatorLayout parent, @NonNull V child, Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        // Intermediate states are restored as collapsed state
        if (ss.state == STATE_DRAGGING || ss.state == STATE_SETTLING) {
            mState = STATE_COLLAPSED;
        } else {
            mState = ss.state;
        }
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull V child, int layoutDirection) {
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            ViewCompat.setFitsSystemWindows(child, true);
        }
        int savedTop = child.getTop();
        // First let the parent lay it out
        parent.onLayoutChild(child, layoutDirection);
        // Offset the bottom sheet
        mParentHeight = parent.getHeight();
        mMinOffset = Math.max(-child.getHeight(), -(child.getHeight() - mPeekHeight));
        mMaxOffset = 0;
        if (mState == STATE_EXPANDED) {
            ViewCompat.offsetTopAndBottom(child, mMaxOffset);
        } else if (mHideable && mState == STATE_HIDDEN) {
            ViewCompat.offsetTopAndBottom(child, -child.getHeight());
        } else if (mState == STATE_COLLAPSED) {
            ViewCompat.offsetTopAndBottom(child, mMinOffset);
        } else if (mState == STATE_DRAGGING || mState == STATE_SETTLING) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }
        if (mViewDragHelper == null) {
            mViewDragHelper = ViewDragHelper.create(parent, mDragCallback);
        }
        mViewRef = new WeakReference<>(child);
        mNestedScrollingChildRef = new WeakReference<>(findScrollingChild(child));
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent event) {
        if (!child.isShown()) {
            return false;
        }
        int action = MotionEventCompat.getActionMasked(event);
        // Record the velocity
        if (action == MotionEvent.ACTION_DOWN) {
            reset();
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mTouchingScrollingChild = false;
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                // Reset the ignore flag
                if (mIgnoreEvents) {
                    mIgnoreEvents = false;
                    return false;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                int initialX = (int) event.getX();
                mInitialY = (int) event.getY();
                View scroll = mNestedScrollingChildRef.get();
                if (scroll != null && parent.isPointInChildBounds(scroll, initialX, mInitialY)) {
                    mActivePointerId = event.getPointerId(event.getActionIndex());
                    mTouchingScrollingChild = true;
                }
                mIgnoreEvents = mActivePointerId == MotionEvent.INVALID_POINTER_ID &&
                        !parent.isPointInChildBounds(child, initialX, mInitialY);
                break;
        }
        if (!mIgnoreEvents && mViewDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        }
        // We have to handle cases that the ViewDragHelper does not capture the bottom sheet because
        // it is not the top most view of its parent. This is not necessary when the touch event is
        // happening over the scrolling content as nested scrolling logic handles that case.
        View scroll = mNestedScrollingChildRef.get();
        return action == MotionEvent.ACTION_MOVE && scroll != null &&
                !mIgnoreEvents && mState != STATE_DRAGGING &&
                !parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY()) &&
                Math.abs(mInitialY - event.getY()) > mViewDragHelper.getTouchSlop();
    }

//    @Override
//    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
    public boolean onTouchEvent(View parent, @NonNull V child, @NonNull MotionEvent event) {
        if (!child.isShown()) {
            return false;
        }
        int action = MotionEventCompat.getActionMasked(event);
        if (mState == STATE_DRAGGING && action == MotionEvent.ACTION_DOWN) {
            return true;
        }
        mViewDragHelper.processTouchEvent(event);
        // Record the velocity
        if (action == MotionEvent.ACTION_DOWN) {
            reset();
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        // The ViewDragHelper tries to capture only the top-most View. We have to explicitly tell it
        // to capture the bottom sheet in case it is not captured and the touch slop is passed.
        if (action == MotionEvent.ACTION_MOVE && !mIgnoreEvents) {
            if (Math.abs(mInitialY - event.getY()) > mViewDragHelper.getTouchSlop()) {
                mViewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
            }
        }
        return !mIgnoreEvents;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        mLastNestedScrollDy = 0;
        mNestedScrolled = false;
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx,
                                  int dy, int[] consumed) {
        View scrollingChild = mNestedScrollingChildRef.get();
        if (target != scrollingChild) {
            return;
        }
        int currentTop = child.getTop();
        int newTop = currentTop - dy;
        if (dy > 0) { // Upward
            if (!ViewCompat.canScrollVertically(target, 1)) {
                if (newTop >= mMinOffset || mHideable) {
                    consumed[1] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    setStateInternal(STATE_DRAGGING);
                } else {
                    consumed[1] = currentTop - mMinOffset;
                    ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                    setStateInternal(STATE_COLLAPSED);
                }
            }
        } else if (dy < 0) { // Downward
            // Negative to check scrolling up, positive to check scrolling down
            if (newTop < mMaxOffset) {
                consumed[1] = dy;
                ViewCompat.offsetTopAndBottom(child, -dy);
                setStateInternal(STATE_DRAGGING);
            } else {
                consumed[1] = currentTop - mMaxOffset;
                ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                setStateInternal(STATE_EXPANDED);
            }
        }
        dispatchOnSlide(child.getTop());
        mLastNestedScrollDy = dy;
        mNestedScrolled = true;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull V child, View target) {
        if (child.getTop() == mMaxOffset) {
            setStateInternal(STATE_EXPANDED);
            return;
        }
        if (target != mNestedScrollingChildRef.get() || !mNestedScrolled) {
            return;
        }
        int top;
        int targetState;
        if (mLastNestedScrollDy < 0) {
            top = mMaxOffset;
            targetState = STATE_EXPANDED;
        } else if (mHideable && shouldHide(child, getYVelocity())) {
            top = -child.getHeight();
            targetState = STATE_HIDDEN;
        } else if (mLastNestedScrollDy == 0) {
            int currentTop = child.getTop();
            if (Math.abs(currentTop - mMinOffset) > Math.abs(currentTop - mMaxOffset)) {
                top = mMaxOffset;
                targetState = STATE_EXPANDED;
            } else {
                top = mMinOffset;
                targetState = STATE_COLLAPSED;
            }
        } else {
            top = mMinOffset;
            targetState = STATE_COLLAPSED;
        }
        if (mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
            setStateInternal(STATE_SETTLING);
            ViewCompat.postOnAnimation(child, new SettleRunnable(child, targetState));
        } else {
            setStateInternal(targetState);
        }
        mNestedScrolled = false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target,
                                    float velocityX, float velocityY) {
        return target == mNestedScrollingChildRef.get() &&
                (mState != STATE_EXPANDED ||
                        super.onNestedPreFling(coordinatorLayout, child, target,
                                velocityX, velocityY));
    }

    /**
     * Sets the height of the bottom sheet when it is collapsed.
     *
     * @param peekHeight The height of the collapsed bottom sheet in pixels.
     * @attr ref android.support.design.R.styleable#TopSheetBehavior_Params_behavior_peekHeight
     */
    public final void setPeekHeight(int peekHeight) {
        mPeekHeight = Math.max(0, peekHeight);
//        mMaxOffset = mParentHeight - peekHeight;
        if (mViewRef != null && mViewRef.get() != null) {
            mMinOffset = Math.max(-mViewRef.get().getHeight(), -(mViewRef.get().getHeight() - mPeekHeight));
        }
    }

    /**
     * Gets the height of the bottom sheet when it is collapsed.
     *
     * @return The height of the collapsed bottom sheet.
     * @attr ref android.support.design.R.styleable#BottomSheetBehavior_Layout_behavior_peekHeight
     */
    public final int getPeekHeight() {
        return mPeekHeight;
    }

    /**
     * Sets whether this bottom sheet can hide when it is swiped down.
     *
     * @param hideable {@code true} to make this bottom sheet hideable.
     * @attr ref android.support.design.R.styleable#BottomSheetBehavior_Layout_behavior_hideable
     */
    public void setHideable(boolean hideable) {
        mHideable = hideable;
    }

    /**
     * Gets whether this bottom sheet can hide when it is swiped down.
     *
     * @return {@code true} if this bottom sheet can hide.
     * @attr ref android.support.design.R.styleable#BottomSheetBehavior_Layout_behavior_hideable
     */
    public boolean isHideable() {
        return mHideable;
    }

    /**
     * Sets whether this bottom sheet should skip the collapsed state when it is being hidden
     * after it is expanded once. Setting this to true has no effect unless the sheet is hideable.
     *
     * @param skipCollapsed True if the bottom sheet should skip the collapsed state.
     * @attr ref android.support.design.R.styleable#BottomSheetBehavior_Layout_behavior_skipCollapsed
     */
    public void setSkipCollapsed(boolean skipCollapsed) {
        mSkipCollapsed = skipCollapsed;
    }

    /**
     * Sets whether this bottom sheet should skip the collapsed state when it is being hidden
     * after it is expanded once.
     *
     * @return Whether the bottom sheet should skip the collapsed state.
     * @attr ref android.support.design.R.styleable#BottomSheetBehavior_Layout_behavior_skipCollapsed
     */
    public boolean getSkipCollapsed() {
        return mSkipCollapsed;
    }

    /**
     * Sets da callback to be notified of bottom sheet events.
     *
     * @param callback The callback to notify when bottom sheet events occur.
     */
    public void setTopSheetCallback(TopSheetCallback callback) {
        mCallback = callback;
    }

    /**
     * Sets the state of the bottom sheet. The bottom sheet will transition to that state with
     * animation.
     *
     * @param state One of {@link #STATE_COLLAPSED}, {@link #STATE_EXPANDED}, or
     *              {@link #STATE_HIDDEN}.
     */
    public final void setState(@State int state) {
        if (state == mState) {
            return;
        }
        if (mViewRef == null) {
            // The view is not laid out yet; modify mState and let onLayoutChild handle it later
            if (state == STATE_COLLAPSED || state == STATE_EXPANDED ||
                    (mHideable && state == STATE_HIDDEN)) {
                mState = state;
            }
            return;
        }
        V child = mViewRef.get();
        if (child == null) {
            return;
        }
        int top;
        if (state == STATE_COLLAPSED) {
            top = mMinOffset;
        } else if (state == STATE_EXPANDED) {
            top = mMaxOffset;
        } else if (mHideable && state == STATE_HIDDEN) {
            top = -child.getHeight();
        } else {
            throw new IllegalArgumentException("Illegal state argument: " + state);
        }
        setStateInternal(STATE_SETTLING);
        if (mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
            ViewCompat.postOnAnimation(child, new SettleRunnable(child, state));
        }
    }

    /**
     * Gets the current state of the bottom sheet.
     *
     * @return One of {@link #STATE_EXPANDED}, {@link #STATE_COLLAPSED}, {@link #STATE_DRAGGING},
     * and {@link #STATE_SETTLING}.
     */
    @State
    public final int getState() {
        return mState;
    }

    private void setStateInternal(@State int state) {
        if (mState == state) {
            return;
        }
        mState = state;
        View bottomSheet = mViewRef.get();
        if (bottomSheet != null && mCallback != null) {
            mCallback.onStateChanged(bottomSheet, state);
        }
    }

    private void reset() {
        mActivePointerId = ViewDragHelper.INVALID_POINTER;
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private boolean shouldHide(@NonNull View child, float yvel) {
        if (child.getTop() > mMinOffset) {
            // It should not hide, but collapse.
            return false;
        }
        final float newTop = child.getTop() + yvel * HIDE_FRICTION;
        return Math.abs(newTop - mMinOffset) / (float) mPeekHeight > HIDE_THRESHOLD;
    }

    @Nullable
    private View findScrollingChild(View view) {
        if (view instanceof NestedScrollingChild) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                View scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }

    private float getYVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
        return VelocityTrackerCompat.getYVelocity(mVelocityTracker, mActivePointerId);
    }

    private final ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (mState == STATE_DRAGGING) {
                return false;
            }
            if (mTouchingScrollingChild) {
                return false;
            }
            if (mState == STATE_EXPANDED && mActivePointerId == pointerId) {
                View scroll = mNestedScrollingChildRef.get();
                if (scroll != null && ViewCompat.canScrollVertically(scroll, -1)) {
                    // Let the content scroll up
                    return false;
                }
            }
            return mViewRef != null && mViewRef.get() == child;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            dispatchOnSlide(top);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_DRAGGING) {
                setStateInternal(STATE_DRAGGING);
            }
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            int top;
            @State int targetState;
            if (yvel > 0) { // Moving up
                top = mMaxOffset;
                targetState = STATE_EXPANDED;
            } else if (mHideable && shouldHide(releasedChild, yvel)) {
                top = -mViewRef.get().getHeight();
                targetState = STATE_HIDDEN;
            } else if (yvel == 0.f) {
                int currentTop = releasedChild.getTop();
                if (Math.abs(currentTop - mMinOffset) > Math.abs(currentTop - mMaxOffset)) {
                    top = mMaxOffset;
                    targetState = STATE_EXPANDED;
                } else {
                    top = mMinOffset;
                    targetState = STATE_COLLAPSED;
                }
            } else {
                top = mMinOffset;
                targetState = STATE_COLLAPSED;
            }
            if (mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top)) {
                setStateInternal(STATE_SETTLING);
                ViewCompat.postOnAnimation(releasedChild,
                        new SettleRunnable(releasedChild, targetState));
            } else {
                setStateInternal(targetState);
            }
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return constrain(top, mHideable ? -child.getHeight() : mMinOffset, mMaxOffset);
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return child.getLeft();
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            if (mHideable) {
                return child.getHeight();
            } else {
                return mMaxOffset - mMinOffset;
            }
        }
    };

    private void dispatchOnSlide(int top) {
        View bottomSheet = mViewRef.get();
        if (bottomSheet != null && mCallback != null) {
            if (top < mMinOffset) {
                mCallback.onSlide(bottomSheet, (float) (top - mMinOffset) / mPeekHeight);
            } else {
                mCallback.onSlide(bottomSheet,
                        (float) (top - mMinOffset) / ((mMaxOffset - mMinOffset)));
            }
        }
    }

    private class SettleRunnable implements Runnable {

        private final View mView;

        @State
        private final int mTargetState;

        SettleRunnable(View view, @State int targetState) {
            mView = view;
            mTargetState = targetState;
        }

        @Override
        public void run() {
            if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(mView, this);
            } else {
                setStateInternal(mTargetState);
            }
        }
    }

    protected static class SavedState extends AbsSavedState {
        @State
        final int state;

        public SavedState(@NonNull Parcel source) {
            this(source, null);
        }

        public SavedState(@NonNull Parcel source, ClassLoader loader) {
            super(source, loader);
            //noinspection ResourceType
            state = source.readInt();
        }

        public SavedState(@NonNull Parcelable superState, @State int state) {
            super(superState);
            this.state = state;
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(state);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(
                new ParcelableCompatCreatorCallbacks<SavedState>() {
                    @NonNull
                    @Override
                    public SavedState createFromParcel(@NonNull Parcel in, ClassLoader loader) {
                        return new SavedState(in, loader);
                    }

                    @NonNull
                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                });
    }

    /**
     * A utility function to get the {@link TopSheetBehavior} associated with the {@code view}.
     *
     * @param view The {@link View} with {@link TopSheetBehavior}.
     * @return The {@link TopSheetBehavior} associated with the {@code view}.
     */
    @NonNull
    @SuppressWarnings("unchecked")
    public static <V extends View> TopSheetBehavior<V> from(@NonNull V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not da child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof TopSheetBehavior)) {
            throw new IllegalArgumentException(
                    "The view is not associated with TopSheetBehavior");
        }
        return (TopSheetBehavior<V>) behavior;
    }

    static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
}
