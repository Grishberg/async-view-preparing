package com.grishberg.rvmenu.rv.widget;

import android.view.View;

import com.grishberg.asynclayout.ChildAdapter;
import com.grishberg.asynclayout.PosToTypeAdapter;
import com.grishberg.asynclayout.VhBinder;
import com.grishberg.rvmenu.common.L;

public class WidgetAdapter extends ChildAdapter<WidgetChildVh> {
    private static final String T = "WA";
    private final VhBinder<WidgetChildVh> binder;
    private final L log;

    public WidgetAdapter(PosToTypeAdapter posToTypeAdapter,
                         VhBinder<WidgetChildVh> binder, L l) {
        super(posToTypeAdapter);
        this.binder = binder;
        log = l;
    }

    @Override
    protected WidgetChildVh onCreateViewHolder(View view) {
        return new WidgetChildVh(view);
    }

    @Override
    public void onBindViewHolder(WidgetChildVh vh, int pos) {
        log.d(T, "on bind pos=" + pos);
        binder.bind(pos, vh);
    }
}
