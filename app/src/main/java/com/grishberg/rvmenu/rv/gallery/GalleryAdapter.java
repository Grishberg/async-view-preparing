package com.grishberg.rvmenu.rv.gallery;
import android.support.v7.widget.*;
import com.grishberg.asynclayout.*;
import com.grishberg.rvmenu.common.*;
import android.view.*;

public class GalleryAdapter extends ChildAdapter<GalleryChildViewHolder> {
    private static final String T = "GA";
    private final VhBinder<GalleryChildViewHolder> binder;
    private final L log;

    public GalleryAdapter(PosToTypeAdapter posToTypeAdapter,
                         VhBinder<GalleryChildViewHolder> binder, L l) {
        super(posToTypeAdapter);
        this.binder = binder;
        log = l;
    }

    @Override
    protected GalleryChildViewHolder onCreateViewHolder(View view) {
        return new GalleryChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryChildViewHolder vh, int pos) {
        log.d(T, "on bind pos=" + pos);
		//
    }
}
