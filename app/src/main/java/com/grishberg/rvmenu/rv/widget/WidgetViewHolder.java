package com.grishberg.rvmenu.rv.widget;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.grishberg.rvmenu.Item;
import com.grishberg.rvmenu.R;
import com.grishberg.rvmenu.common.L;
import com.grishberg.rvmenu.rv.MenuViewHolder;

public class WidgetViewHolder extends MenuViewHolder implements Widget {

    private static final String T = "WVH";
    private final RecyclerView rv;
    private final L log;

    public WidgetViewHolder(View v, L l) {
        super(v);
        rv = v.findViewById(R.id.widgetRv);
        log = l;
    }

    @Override
    public void bind(Item i) {
        // TODO: Implement this method
    }

    @Override
    public void initWidgets(View firstItem) {
        log.d(T, "init widgets");
    }

    @Override
    public void addWidget(int pos, View v) {
    }
}
