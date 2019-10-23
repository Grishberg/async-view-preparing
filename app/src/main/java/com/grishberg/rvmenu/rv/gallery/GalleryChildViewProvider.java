package com.grishberg.rvmenu.rv.gallery;
import android.view.*;

import com.grishberg.asynclayout.ViewProvider;
import com.grishberg.rvmenu.R;

public class GalleryChildViewProvider implements ViewProvider {
    private final LayoutInflater inflater;
    private final ViewGroup inflateParent;

    public GalleryChildViewProvider(LayoutInflater inflater,
                                ViewGroup inflateParent) {
        this.inflater = inflater;
        this.inflateParent = inflateParent;
    }

    @Override
    public View getView(int type) {
        return inflater.inflate(R.layout.gallery_item_layout, inflateParent, false);
    }
}
