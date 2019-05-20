package com.grishberg.rvmenu.rv.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.grishberg.asynclayout.PosToTypeAdapter;
import com.grishberg.rvmenu.common.L;

import java.util.ArrayList;
import java.util.List;

public class WidgetAdapter extends RecyclerView.Adapter<WidgetChildVh> {
    private static final String T = "WA";
    private ArrayList<View> viewCache = new ArrayList<>();
    private List<String> items;
    private final PosToTypeAdapter posToTypeAdapter;
    private final L log;

    public WidgetAdapter(List<String> items,
                         PosToTypeAdapter posToTypeAdapter,
                         L l) {
        this.items = items;
        this.posToTypeAdapter = posToTypeAdapter;
        log = l;
    }

    void addViewForPos(int pos, View v) {
        viewCache.add(v);
        log.d(T, "add view for pos=" + pos);
        if (pos == 0) {
            notifyDataSetChanged();

            return;
        }
        notifyItemInserted(pos);
    }

    @Override
    public int getItemViewType(int position) {
        return posToTypeAdapter.typeByPos(position);
    }

    @Override
    public WidgetChildVh onCreateViewHolder(ViewGroup parent, int type) {
        log.d(T, "on create type=" + type);
        return new WidgetChildVh(viewCache.get(type));
    }

    @Override
    public void onBindViewHolder(WidgetChildVh vh, int pos) {
        log.d(T, "on bind pos=" + pos);
        vh.bind(items.get(pos));
    }

    @Override
    public int getItemCount() {
        log.d(T, "count = " + viewCache.size());
        return viewCache.size();
    }
}
