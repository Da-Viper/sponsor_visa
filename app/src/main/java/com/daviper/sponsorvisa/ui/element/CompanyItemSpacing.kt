package com.daviper.sponsorvisa.ui.element

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CompanyItemSpacing(private val verticalSpacing: Int = 20) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = verticalSpacing
        outRect.bottom = verticalSpacing
    }
}
