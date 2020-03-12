package com.bm.main.fpl.templates.stepperview.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @hide
 */
public class ClipOvalFrameLayout extends FrameLayout {

	@NonNull
    private Path path = new Path();

	public ClipOvalFrameLayout(@NonNull Context context) {
		super(context);
		init();
	}

	public ClipOvalFrameLayout(@NonNull Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ClipOvalFrameLayout(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		if (!isPreLollipop()) {
			setClipToOutline(true);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		if (isPreLollipop()) {
			float halfWidth = w / 2f;
			float halfHeight = h / 2f;
			path.reset();
			path.addCircle(halfWidth, halfHeight, Math.min(halfWidth, halfHeight), Path.Direction.CW);
			path.close();
		}
	}

	@Override
	protected void dispatchDraw(@NonNull Canvas canvas) {
		if (isPreLollipop()) {
			int save = canvas.save();
			canvas.clipPath(path);
			super.dispatchDraw(canvas);
			canvas.restoreToCount(save);
		} else {
			super.dispatchDraw(canvas);
		}
	}

	private static boolean isPreLollipop() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
	}

}
