package com.grishberg.rvmenu.rv.gallery;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grishberg.asynclayout.VhBinder;
import com.grishberg.rvmenu.R;

import java.util.List;

public class GalleryChildBinder implements VhBinder<GalleryChildViewHolder> {
    private final List<GalleryItem> titles;

    public GalleryChildBinder(List<GalleryItem> titles) {
        this.titles = titles;
    }

    @Override
    public void bind(int pos, View view) {
        TextView title = view.findViewById(R.id.title);
        title.setText(titles.get(pos).title);

        ImageView icon = view.findViewById(R.id.icon);
        icon.setBackgroundColor(titles.get(pos).color);
    }

    @Override
    public void bind(int pos, GalleryChildViewHolder vh) {

    }

    @Override
    public int itemsCount() {
        return titles.size();
    }
}
