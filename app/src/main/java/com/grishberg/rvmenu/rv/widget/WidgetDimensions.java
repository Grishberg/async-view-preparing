package com.grishberg.rvmenu.rv.widget;

import android.content.Context;

import com.grishberg.asynclayout.DimensionProvider;
import com.grishberg.rvmenu.R;

/**
 * Dimension for widgets view.
 */
public class WidgetDimensions implements DimensionProvider {
    private int width;
    private final int height;

    public WidgetDimensions(Context c) {
        height = c.getResources().getDimensionPixelSize(R.dimen.widgetHeight);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
