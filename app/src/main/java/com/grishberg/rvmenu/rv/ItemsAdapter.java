package com.grishberg.rvmenu.rv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.grishberg.asynclayout.AsyncRvHolderDelegate;
import com.grishberg.asynclayout.Binder;
import com.grishberg.asynclayout.DimensionProvider;
import com.grishberg.asynclayout.PosToTypeAdapter;
import com.grishberg.asynclayout.ViewProvider;
import com.grishberg.rvmenu.Item;
import com.grishberg.rvmenu.R;
import com.grishberg.rvmenu.common.L;
import com.grishberg.rvmenu.rv.widget.WidgetAdapter;
import com.grishberg.rvmenu.rv.widget.WidgetChildDimension;
import com.grishberg.rvmenu.rv.widget.WidgetViewHolder;
import com.grishberg.rvmenu.rv.widget.WidgetsChildProvider;
import com.grishberg.rvmenu.rv.widget.WidgetsRootProvider;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<MenuViewHolder>
        implements AsyncRvHolderDelegate.RvItemPrepareListener {
    private static final String T = "[adapter]";

    private static final int TYPE_WIDGETS = 0;
    private static final int TYPE_ITEMS = 1;

    private final L log = new L();
    private final ArrayList<Item> items = new ArrayList<>();
    private final LayoutInflater inflater;
    private boolean initiated;
    private WidgetViewHolder widgets;
    private final AsyncRvHolderDelegate widgetsDelegate;
    private final WidgetAdapter widgetsAdapter;


    public ItemsAdapter(Context c, LayoutInflater inflater,
                        DimensionProvider dimensionProvider,
                        PosToTypeAdapter widgetsPosToTypeAdapter,
                        Binder widgetsBinder) {
        this.inflater = inflater;
        DimensionProvider widgetChildDimension = new WidgetChildDimension(c);
        FrameLayout inflateRoot = new FrameLayout(c);
        ViewProvider widgetRootProvider = new WidgetsRootProvider(inflater, inflateRoot);
        ViewProvider childrenProvider = new WidgetsChildProvider(inflater, inflateRoot);
        widgetsDelegate = new AsyncRvHolderDelegate(widgetRootProvider,
                childrenProvider,
                widgetsPosToTypeAdapter,
                R.id.widgetRv,
                dimensionProvider,
                widgetChildDimension,
                widgetsBinder,
                log);
        widgetsDelegate.setListener(this);
        widgetsAdapter = new WidgetAdapter(widgetsPosToTypeAdapter, widgetsBinder, log);
    }

    public void populate(List<Item> i) {
        items.clear();
        items.addAll(i);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 1);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_WIDGETS;
        }
        return TYPE_ITEMS;
    }

    public void showWidget() {
        if (initiated || widgets == null) {
            return;
        }
        log.d(T, "show widgets");
        initiated = true;
        widgetsDelegate.prepareChildren();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        if (type == TYPE_WIDGETS) {
            return createWidgetVH(parent);
        }
        return createItemVh(parent);
    }

    private MenuViewHolder createWidgetVH(ViewGroup parent) {
        int layout = R.layout.widget_layout;
        log.d(T, "create widget vh");
        View v = inflater.inflate(layout, parent, false);
        WidgetViewHolder vh = new WidgetViewHolder(v, widgetsAdapter, log);
        widgets = vh;
        return vh;
    }

    private MenuViewHolder createItemVh(ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder vh, int pos) {
        vh.bind(items.get(pos));
    }

    @Override
    public void onRootItemPrepared(View v) {
        // TODO: replace stub with widget view.
    }

    @Override
    public void onChildPrepared(int pos, View v) {
        log.d(T, "onChildPrepared p=" + pos);
        widgets.addWidget(pos, v);
    }

    @Override
    public void onInitChildRv(View firstView) {
        // create widgets adapter and set to  widgets rv
        log.d(T, "onInitChildRv v=" + firstView);

        widgets.initWidgets(firstView);
    }
}
