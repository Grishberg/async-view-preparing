package com.grishberg.rvmenu.rv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.grishberg.asynclayout.AsyncRvHolderDelegate;
import com.grishberg.asynclayout.AsyncViewRepository;
import com.grishberg.asynclayout.Binder;
import com.grishberg.asynclayout.ChildAdapter;
import com.grishberg.asynclayout.DimensionProvider;
import com.grishberg.asynclayout.PosToTypeAdapter;
import com.grishberg.asynclayout.ViewProvider;
import com.grishberg.rvmenu.Item;
import com.grishberg.rvmenu.R;
import com.grishberg.rvmenu.common.L;
import com.grishberg.rvmenu.common.LazyProvider;
import com.grishberg.rvmenu.rv.widget.WidgetAdapter;
import com.grishberg.rvmenu.rv.widget.WidgetChildDimension;
import com.grishberg.rvmenu.rv.widget.WidgetChildVh;
import com.grishberg.rvmenu.rv.widget.WidgetViewHolder;
import com.grishberg.rvmenu.rv.widget.WidgetsChildProvider;
import com.grishberg.rvmenu.rv.widget.WidgetsRootProvider;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private static final String T = "[adapter]";

    private static final int TYPE_WIDGETS = 0;
    private static final int TYPE_ITEMS = 1;

    private final L log = new L();
    private final ArrayList<Item> items = new ArrayList<>();
    private final LayoutInflater inflater;
    private boolean initiated;
    private WidgetViewHolder widgets;
    private final AsyncViewRepository asyncViewRepository;

    public ItemsAdapter(Context c, LayoutInflater inflater,
                        DimensionProvider dimensionProvider,
                        final PosToTypeAdapter widgetsPosToTypeAdapter,
                        final Binder<WidgetChildVh> widgetsBinder,
                        AsyncViewRepository asyncViewRepository) {
        this.inflater = inflater;
        this.asyncViewRepository = asyncViewRepository;
        DimensionProvider widgetChildDimension = new WidgetChildDimension(c);
        FrameLayout inflateRoot = new FrameLayout(c);
        ViewProvider widgetRootProvider = new WidgetsRootProvider(inflater, inflateRoot);
        ViewProvider childrenProvider = new WidgetsChildProvider(inflater, inflateRoot);
        AsyncRvHolderDelegate widgetsDelegate = new AsyncRvHolderDelegate(widgetRootProvider,
                childrenProvider,
                widgetsPosToTypeAdapter,
                dimensionProvider,
                widgetChildDimension,
                widgetsBinder,
                log);

        asyncViewRepository.registerRvIdForType(TYPE_WIDGETS, R.id.widgetRv, widgetsDelegate,
                new LazyProvider<ChildAdapter>() {
                    @Override
                    protected ChildAdapter create() {
                        return new WidgetAdapter(widgetsPosToTypeAdapter, widgetsBinder, log);
                    }
                },
                false);
        asyncViewRepository.setRvInitializer(new AsyncViewRepository.RvInitializer() {
            @Override
            public void onInitChildRecyclerView(int type, RecyclerView rv) {
                if (type == TYPE_WIDGETS) {
                    rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), RecyclerView.HORIZONTAL, false));
                    DividerItemDecoration itemDecoration = new DividerItemDecoration(rv.getContext(), RecyclerView.HORIZONTAL);
                    rv.addItemDecoration(itemDecoration);
                }
            }
        });
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
        if (initiated) {
            return;
        }
        initiated = true;
        log.d(T, "show widgets");
        asyncViewRepository.prepareAsync(TYPE_WIDGETS);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        MenuViewHolder vh;
        if (type == TYPE_WIDGETS) {
            vh = createWidgetVH(parent);
        } else {
            vh = createItemVh(parent);
        }
        asyncViewRepository.onCreateViewHolder(type, vh.itemView);
        return vh;
    }

    private MenuViewHolder createWidgetVH(ViewGroup parent) {
        int layout = R.layout.widget_layout;
        log.d(T, "create widget vh");
        return new WidgetViewHolder(inflater.inflate(layout, parent, false), log);
    }

    private MenuViewHolder createItemVh(ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder vh, int pos) {
        vh.bind(items.get(pos));
    }
}
