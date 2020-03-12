package com.bm.main.pos.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class LinearItemDecoration(val orientation: Int = OrientationHelper.VERTICAL, val space: Int = 10) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) > 0) {
            if (orientation == OrientationHelper.VERTICAL) {
                outRect.top = space
            } else {
                outRect.left = space
            }
        }
    }
}