/*
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016 Douglas Nassif Roma Junior
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bm.main.fpl.templates.tooltip;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * SimpleTooltipUtils
 * Created by douglas on 09/05/16.
 */
@SuppressWarnings({"SameParameterValue", "unused"})
public final class SimpleTooltipUtils {

    private SimpleTooltipUtils() {

    }

    @NonNull
    public static RectF calculeRectOnScreen(@NonNull View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getMeasuredWidth(), location[1] + view.getMeasuredHeight());
    }

    @NonNull
    public static RectF calculeRectInWindow(@NonNull View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return new RectF(location[0], location[1], location[0] + view.getMeasuredWidth(), location[1] + view.getMeasuredHeight());
    }

    public static float dpFromPx(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }

    public static float pxFromDp(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static void setWidth(@NonNull View view, float width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams((int) width, view.getHeight());
        } else {
            params.width = (int) width;
        }
        view.setLayoutParams(params);
    }

    public static int tooltipGravityToArrowDirection(int tooltipGravity) {
        switch (tooltipGravity) {
            case Gravity.START:
                return ArrowDrawable.RIGHT;
            case Gravity.END:
                return ArrowDrawable.LEFT;
            case Gravity.TOP:
                return ArrowDrawable.BOTTOM;
            case Gravity.BOTTOM:
                return ArrowDrawable.TOP;
            case Gravity.CENTER:
                return ArrowDrawable.TOP;
            default:
                throw new IllegalArgumentException("Gravity must have be CENTER, START, END, TOP or BOTTOM.");
        }
    }

    public static void setX(@NonNull View view, int x) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setX(x);
        } else {
            ViewGroup.MarginLayoutParams marginParams = getOrCreateMarginLayoutParams(view);
            marginParams.leftMargin = x - view.getLeft();
            view.setLayoutParams(marginParams);
        }
    }

    public static void setY(@NonNull View view, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setY(y);
        } else {
            ViewGroup.MarginLayoutParams marginParams = getOrCreateMarginLayoutParams(view);
            marginParams.topMargin = y - view.getTop();
            view.setLayoutParams(marginParams);
        }
    }

    @NonNull
    private static ViewGroup.MarginLayoutParams getOrCreateMarginLayoutParams(@NonNull View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null) {
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                return (ViewGroup.MarginLayoutParams) lp;
            } else {
                return new ViewGroup.MarginLayoutParams(lp);
            }
        } else {
            return new ViewGroup.MarginLayoutParams(view.getWidth(), view.getHeight());
        }
    }

    public static void removeOnGlobalLayoutListener(@NonNull View view, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            //noinspection deprecation
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    public static void setTextAppearance(@NonNull TextView tv, @StyleRes int textAppearanceRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setTextAppearance(textAppearanceRes);
        } else {
            //noinspection deprecation
            tv.setTextAppearance(tv.getContext(), textAppearanceRes);
        }
    }

    public static int getColor(@NonNull Context context, @ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(colorRes);
        } else {
            //noinspection deprecation
            return context.getResources().getColor(colorRes);
        }
    }

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int drawableRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getDrawable(drawableRes);
        } else {
            //noinspection deprecation
            return context.getResources().getDrawable(drawableRes);
        }
    }

    /**
     * Verify if the first child of the rootView is a FrameLayout.
     * Used for cases where the Tooltip is created inside a Dialog or DialogFragment.
     *
     * @param anchorView
     * @return FrameLayout or anchorView.getRootView()
     */
    @NonNull
    public static ViewGroup findFrameLayout(@NonNull View anchorView) {
        ViewGroup rootView = (ViewGroup) anchorView.getRootView();
        if (rootView.getChildCount() == 1 && rootView.getChildAt(0) instanceof FrameLayout) {
            rootView = (ViewGroup) rootView.getChildAt(0);
        }
        return rootView;
    }
}
