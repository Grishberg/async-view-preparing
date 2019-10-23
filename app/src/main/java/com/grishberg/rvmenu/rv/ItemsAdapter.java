package com.grishberg.rvmenu.rv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.grishberg.asynclayout.AsyncRvDelegate;
import com.grishberg.asynclayout.AsyncRvHolderDelegate;
import com.grishberg.asynclayout.ChildAdapter;
import com.grishberg.asynclayout.DimensionProvider;
import com.grishberg.asynclayout.PosToTypeAdapter;
import com.grishberg.asynclayout.VhBinder;
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
import com.grishberg.rvmenu.rv.gallery.*;

public class ItemsAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private static final String T = "[adapter]";

    private static final int TYPE_WIDGETS = 0;
    private static final int TYPE_GALLERY = 1;
	private static final int TYPE_ITEMS = 2;
	

    private final ArrayList<Item> items = new ArrayList<>();
    private final LayoutInflater inflater;
    private boolean initiated;
    private final AsyncRvDelegate asyncViewRepository;
    private final L log;

    public ItemsAdapter(Context c, LayoutInflater inflater,
                        DimensionProvider dimensionProvider,
						DimensionProvider galleryDimensuonProvider,
                        final PosToTypeAdapter widgetsPosToTypeAdapter,
						final PosToTypeAdapter galleryPosToTypeAdapter,
                        final VhBinder<WidgetChildVh> widgetsBinder,
						final VhBinder<GalleryChildViewHolder> galleryBinder,
                        AsyncRvDelegate asyncViewRepository,
                        final L log) {
        this.inflater = inflater;
        this.asyncViewRepository = asyncViewRepository;
        this.log = log;
        DimensionProvider widgetChildDimension = new WidgetChildDimension(c);
		DimensionProvider galleryChildDimension = new GalleryDimensions(c);
        FrameLayout inflateRoot = new FrameLayout(c);
        ViewProvider widgetRootProvider = new WidgetsRootProvider(inflater, inflateRoot);
        ViewProvider childrenProvider = new WidgetsChildProvider(inflater, inflateRoot);
		
		ViewProvider galleryRootProvider = new GalleryRootViewProvider(inflater, inflateRoot);
        ViewProvider galleryChildProvider = new GalleryChildViewProvider(inflater, inflateRoot);
        
        AsyncRvHolderDelegate widgetsDelegate = new AsyncRvHolderDelegate(
                widgetRootProvider,
                childrenProvider,
                dimensionProvider,
                widgetChildDimension,
                VhBinder.STUB,
                widgetsBinder);
		AsyncRvHolderDelegate galleryDelegate = new AsyncRvHolderDelegate(
			galleryRootProvider,
			galleryChildProvider,
			galleryDimensuonProvider,
			galleryChildDimension,
			VhBinder.STUB,
			galleryBinder);

        asyncViewRepository.registerRvIdForType(TYPE_WIDGETS, R.id.widgetRv, widgetsDelegate,
                new LazyProvider<ChildAdapter>() {
                    @Override
                    protected ChildAdapter create() {
                        return new WidgetAdapter(widgetsPosToTypeAdapter, widgetsBinder, log);
                    }
                },
                false);
				
		asyncViewRepository.registerRvIdForType(TYPE_GALLERY, R.id.galleryRv, galleryDelegate,
			new LazyProvider<ChildAdapter>() {
				@Override
				protected ChildAdapter create() {
					return new GalleryAdapter(galleryPosToTypeAdapter, galleryBinder, log);
				}
			},
			true);
			
        asyncViewRepository.setRvInitializer(new AsyncRvDelegate.RvInitializer() {
            @Override
            public void onInitChildRecyclerView(int type, RecyclerView rv) {
                if (type == TYPE_WIDGETS) {
                    rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), RecyclerView.HORIZONTAL, false));
                    DividerItemDecoration itemDecoration = new DividerItemDecoration(rv.getContext(), RecyclerView.HORIZONTAL);
                    rv.addItemDecoration(itemDecoration);
                } else if(type == TYPE_GALLERY) {
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
		if(position == 1) {
			return TYPE_GALLERY;
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

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        MenuViewHolder vh;
        if (type == TYPE_WIDGETS) {
            vh = createWidgetVH(parent);
        } else if(type == TYPE_GALLERY){
			vh = createGalleryVH(parent);
		} else {
            vh = createItemVh(parent);
        }
        asyncViewRepository.onCreateViewHolder(type, vh.itemView);
        return vh;
    }

    private MenuViewHolder createWidgetVH(ViewGroup parent) {
        log.d(T, "create widget vh");
        return new WidgetViewHolder(inflater.inflate(R.layout.widget_layout, parent, false), log);
    }

	private MenuViewHolder createGalleryVH(ViewGroup parent) {
        log.d(T, "create gallery vh");
		
        return new GalleryViewHolder(inflater.inflate(R.layout.gallery_layout, parent, false), log);
    }
	
    private MenuViewHolder createItemVh(ViewGroup parent) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuViewHolder vh, int pos) {
        vh.bind(items.get(pos));
    }
}
