package com.grishberg.rvmenu.rv.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grishberg.asynclayout.ViewProvider;
import com.grishberg.rvmenu.R;

public class WidgetsRootProvider implements ViewProvider {
    private final LayoutInflater inflater;
    private final ViewGroup inflateParent;

    public WidgetsRootProvider(LayoutInflater inflater,
                               ViewGroup inflateParent) {
        this.inflater = inflater;
        this.inflateParent = inflateParent;
    }

    @Override
    public View getView(int type) {
        return inflater.inflate(R.layout.widget_layout, inflateParent, false);
    }
}
