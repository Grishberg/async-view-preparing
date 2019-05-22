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
    private final WidgetAdapter adapter;
    private final L log;

    public WidgetViewHolder(View v, WidgetAdapter a, L l) {
        super(v);
        rv = v.findViewById(R.id.widgetRv);
        adapter = a;
        log = l;
    }

    @Override
    public void bind(Item i) {
        // TODO: Implement this method
    }

    @Override
    public void initWidgets(View firstItem) {
        log.d(T, "init widgets");
        rv.setVisibility(View.VISIBLE);
        if (rv.getAdapter() == null) {
            rv.setAdapter(adapter);
        }
        if (rv.getLayoutManager() == null) {
            rv.setLayoutManager(new LinearLayoutManager(rv.getContext(),
                    RecyclerView.HORIZONTAL, false));
        }
        DividerItemDecoration itemDecoration = new DividerItemDecoration(rv.getContext(), RecyclerView.HORIZONTAL);
        rv.addItemDecoration(itemDecoration);

        adapter.addViewForPos(0, firstItem);
    }

    @Override
    public void addWidget(int pos, View v) {
        adapter.addViewForPos(pos, v);
    }
}
