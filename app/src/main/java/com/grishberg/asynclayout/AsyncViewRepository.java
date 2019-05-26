package com.grishberg.asynclayout;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class AsyncViewRepository {
    private final SparseArray<RvScopeContainer> rvScopeContainers = new SparseArray<>();
    private RvInitializer rvInitializer = RvInitializer.STUB;

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

    public void setRvInitializer(RvInitializer rvInitializer) {
        this.rvInitializer = rvInitializer;
    }

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
        if (scope == null || scope.initOnViewCreated) {
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

    public interface RvInitializer {
        void onInitChildRecyclerView(int type, RecyclerView rv);

        RvInitializer STUB = new RvInitializer() {
            @Override
            public void onInitChildRecyclerView(int type, RecyclerView rv) {
                /* stub */
            }
        };
    }
}
