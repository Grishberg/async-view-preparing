package com.grishberg.asynclayout;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Manages creation of child view in worker thread.
 */
public class AsyncRvDelegate {
    private final SparseArray<RvScopeContainer> rvScopeContainers = new SparseArray<>();
    private RvInitializer rvInitializer = RvInitializer.STUB;
    private RootItemsPreparedListener parentItemsPreparedListener = RootItemsPreparedListener.STUB;

    /**
     * Register rv id-res for which child view will be created.
     *
     * @param type             type of item in host RV
     * @param rvId             id-res of child RV
     * @param rvHolderDelegate
     */
    public void registerRvIdForType(int type, @IdRes int rvId,
                                    AsyncRvHolderDelegate rvHolderDelegate,
                                    Provider<ChildAdapter> adapterProvider,
                                    boolean initOnViewCreated) {
        rvScopeContainers.put(type,
                new RvScopeContainer(rvId,
                        rvHolderDelegate,
                        adapterProvider,
                        initOnViewCreated));
    }

    /**
     * Sets listener for items RV child view prepared event.
     */
    public void setRvInitializer(RvInitializer rvInitializer) {
        this.rvInitializer = rvInitializer;
    }

    /**
     * Sets listener for parent items view prepared event.
     */
    public void setParentItemsPreparedListener(RootItemsPreparedListener listener) {
        this.parentItemsPreparedListener = listener;
    }

    /**
     * Is called when onCreateViewHolder is invoked in parent RV adapter.
     */
    public void onCreateViewHolder(int type, View itemView) {
        RvScopeContainer scope = rvScopeContainers.get(type);
        if (scope == null) {
            return;
        }
        @IdRes
        int rvId = scope.rvIdRes;
        RecyclerView rv = itemView.findViewById(rvId);
        rv.setAdapter(scope.adapter.get());
        scope.rvHolderDelegate.setListener(new RvChildPreparedListener(type, rv, scope.adapter));
        if (scope.initOnViewCreated) {
            scope.rvHolderDelegate.prepareChildren();
        }
    }

    /**
     * Is called when activity destroyed.
     */
    public void clean() {
        rvScopeContainers.clear();
    }

    /**
     * Start preparing child view asynchronously.
     *
     * @param type parent RV adapter view type.
     */
    public void prepareAsync(int type) {
        RvScopeContainer scope = rvScopeContainers.get(type);
        if (scope == null /*|| scope.initOnViewCreated*/) {
            return;
        }
        scope.rvHolderDelegate.prepareChildren();
    }

    private class RvChildPreparedListener implements AsyncRvHolderDelegate.RvItemPrepareListener {
        private final int type;
        private final RecyclerView rv;
        private final Provider<ChildAdapter> adapter;

        RvChildPreparedListener(int type, RecyclerView rv, Provider<ChildAdapter> adapter) {
            this.type = type;
            this.rv = rv;
            this.adapter = adapter;
        }

        @Override
        public void onRootItemPrepared(View v) {
            parentItemsPreparedListener.onRootViewPrepared(v);
        }

        @Override
        public void onInitChildRv(View firstView) {
            rvInitializer.onInitChildRecyclerView(type, rv);
            adapter.get().onViewPrepared(0, firstView);
        }

        @Override
        public void onChildPrepared(int pos, View v) {
            adapter.get().onViewPrepared(pos, v);
        }
    }

    /**
     * Helper class for items with RV.
     */
    private static class RvScopeContainer {
        @IdRes
        final int rvIdRes;
        final AsyncRvHolderDelegate rvHolderDelegate;
        final Provider<ChildAdapter> adapter;
        // when true - should start preparing children view when onCreateVh was occurred.
        final boolean initOnViewCreated;

        RvScopeContainer(int rvIdRes,
                         AsyncRvHolderDelegate rvHolderDelegate,
                         Provider<ChildAdapter> adapter,
                         boolean initOnViewCreated) {
            this.rvIdRes = rvIdRes;
            this.rvHolderDelegate = rvHolderDelegate;
            this.adapter = adapter;
            this.initOnViewCreated = initOnViewCreated;
        }
    }

    /**
     * Listener for child view created event.
     */
    public interface RvInitializer {
        /**
         * Is called when need to init child recycler view with LayoutManager and decoration.
         *
         * @param type type of child item in parent RV.
         * @param rv   child RV.
         */
        void onInitChildRecyclerView(int type, RecyclerView rv);

        RvInitializer STUB = new RvInitializer() {
            @Override
            public void onInitChildRecyclerView(int type, RecyclerView rv) {
                /* stub */
            }
        };
    }

    /**
     * Listener for root item view created event.
     */
    public interface RootItemsPreparedListener {

        /**
         * Is called when root RV items view prepared.
         *
         * @param view prepared view.
         */
        void onRootViewPrepared(View view);

        RootItemsPreparedListener STUB = new RootItemsPreparedListener() {
            @Override
            public void onRootViewPrepared(View view) {/* stub */}
        };
    }
}
