package com.grishberg.rvmenu.rv.gallery;

import android.content.Context;

import com.grishberg.asynclayout.DimensionProvider;
import com.grishberg.rvmenu.R;

public class GalleryDimensions implements DimensionProvider {
    private final int w;
    private final int h;

    public GalleryDimensions(Context c) {
        w = c.getResources().getDimensionPixelSize(R.dimen.galleryChildWidth);
        h = c.getResources().getDimensionPixelSize(R.dimen.galleryChildHeight);
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
