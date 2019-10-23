package com.grishberg.rvmenu.rv.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grishberg.asynclayout.ViewProvider;
import com.grishberg.rvmenu.R;

public class GalleryRootViewProvider implements ViewProvider {
    private final LayoutInflater inflater;
    private final ViewGroup inflateParent;

    public GalleryRootViewProvider(LayoutInflater inflater,
                               ViewGroup inflateParent) {
        this.inflater = inflater;
        this.inflateParent = inflateParent;
    }

    @Override
    public View getView(int type) {
        return inflater.inflate(R.layout.gallery_layout, inflateParent, false);
    }
}
