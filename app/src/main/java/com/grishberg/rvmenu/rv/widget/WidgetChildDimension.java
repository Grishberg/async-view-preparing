package com.grishberg.rvmenu.rv.widget;

import android.content.Context;

import com.grishberg.asynclayout.DimensionProvider;

/**
 * Dimensions for widgets children.
 */
public class WidgetChildDimension implements DimensionProvider {
    private final int w;
    private final int h;

    public WidgetChildDimension(Context c) {
        w = c.getResources().getDimensionPixelSize(R.dimen.childWidth);
        h = c.getResources().getDimensionPixelSize(R.dimen.childHeight);
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }
}
