package com.grishberg.rvmenu;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

public class TopSpaceItemDecorator extends RecyclerView.ItemDecoration {
    private final LayoutManager lm;
    private int topOffset = 0;

    public TopSpaceItemDecorator(LayoutManager lm) {
        this.lm = lm;
    }

    @Override
    public void getItemOffsets(
            Rect outRect,
            View view,
            RecyclerView parent,
            RecyclerView.State state) {
        if (lm.getPosition(view) == 0) {
            outRect.set(0, topOffset, 0, 0);
        }
    }

    public void updateTopOffset(int offs) {
        topOffset = offs;
    }
}
