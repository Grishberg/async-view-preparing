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
     * @param type type of item in host RV
     * @param rvId id-res of child RV
     * @param rvHolderDelegate
     */
    public void registerRvIdForType(int type, @IdRes int rvId,
                                    AsyncRvHolderDelegate rvHolderDelegate,
                                    Provider<ChildAdapter> adapterProvider) {
        rvScopeContainers.put(type,
                new RvScopeContainer(rvId,
                        rvHolderDelegate,
                        adapterProvider));
    }

    public void setRvInitializer(RvInitializer rvInitializer) {
        this.rvInitializer = rvInitializer;
    }

    public void onCreateViewHolder(int type, View itemView) {
        RvScopeContainer scope = rvScopeContainers.get(type);
        @IdRes
        int rvId = scope.rvIdRes;
        RecyclerView rv = itemView.findViewById(rvId);
        rv.setAdapter(scope.adapter);
        scope.rvHolderDelegate.setListener(new RvChildPreparedListener(scope.adapter));
        scope.rvHolderDelegate.prepareChildren();
    }

    public void clean() {
        rvScopeContainers.clear();
    }

    private static class RvScopeContainer {
        @IdRes
        final int rvIdRes;
        final AsyncRvHolderDelegate rvHolderDelegate;
        final Provider<ChildAdapter> adapter;

        RvScopeContainer(int rvIdRes,
                         AsyncRvHolderDelegate rvHolderDelegate,
                         Provider<ChildAdapter> adapter) {
            this.rvIdRes = rvIdRes;
            this.rvHolderDelegate = rvHolderDelegate;
            this.adapter = adapter;
        }
    }

    private class RvChildPreparedListener implements AsyncRvHolderDelegate.RvItemPrepareListener {
        private final int type
        private final ChildAdapter adapter;

        RvChildPreparedListener(ChildAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onRootItemPrepared(View v) {

        }

        @Override
        public void onInitChildRv(View firstView) {
            rvInitializer.onInitChildRecyclerView();
            adapter.onViewPrepared(0, firstView);
        }

        @Override
        public void onChildPrepared(int pos, View v) {
            adapter.onViewPrepared(pos, v);
        }
    }

    public interface RvInitializer {
        void onInitChildRecyclerView(int type, RecyclerView rv);

        RvInitializer STUB = new RvInitializer() {
            @Override
            public void onInitChildRecyclerView(int type, RecyclerView rv) {
                /* stub */
            }
        }
    }
}
