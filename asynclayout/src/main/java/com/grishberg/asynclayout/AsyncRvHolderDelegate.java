package com.grishberg.asynclayout;

import android.view.View;

public class AsyncRvHolderDelegate {
    private final ViewProvider parentProvider;
    private final ViewProvider childProvider;
    private final DimensionProvider parentDimensions;
    private final DimensionProvider childDimensions;
    private final VhBinder parentBinder;
    private VhBinder childBinder;
    private PrepareTask parentPrepareTask;
    private PrepareTask childrenPrepareTask;
    private RvItemPrepareListener listener = RvItemPrepareListener.STUB;
    private boolean firstChildPrepared;

    public AsyncRvHolderDelegate(
            ViewProvider parentProvider,
            ViewProvider childProvider,
            DimensionProvider parentDimensions,
            DimensionProvider childDimensions,
            VhBinder parentBinder,
            VhBinder childBinder) {
        this.parentProvider = parentProvider;
        this.childProvider = childProvider;
        this.parentDimensions = parentDimensions;
        this.childDimensions = childDimensions;
        this.parentBinder = parentBinder;
        this.childBinder = childBinder;
    }

    /**
     * Sets listener for view prepared event.
     *
     * @param listener
     */
    void setListener(RvItemPrepareListener listener) {
        this.listener = listener;
    }

    void prepareAsync() {
        parentPrepareTask = new PrepareTask(
                parentProvider,
                parentDimensions,
                parentBinder,
                new ParentPrepareListener(),
                parentBinder.itemsCount(), 0);
        parentPrepareTask.execute();
    }

    /**
     * Start preparing child view.
     */
    void prepareChildren() {
        childrenPrepareTask = new PrepareTask(
                childProvider,
                childDimensions,
                childBinder,
                new ChildrenPrepareListener(),
                childBinder.itemsCount(), 0
        );
        childrenPrepareTask.execute();
    }

    private class ParentPrepareListener implements ViewPreparedListener {

        @Override
        public void onViewPrepared(int pos, View v) {
            // on parent prepared
            listener.onRootItemPrepared(v);
        }
    }

    private class ChildrenPrepareListener implements ViewPreparedListener {

        @Override
        public void onViewPrepared(int pos, View v) {
            // on child prepared
            if (!firstChildPrepared) {
                listener.onInitChildRv(v);
                firstChildPrepared = true;
                return;
            }
            listener.onChildPrepared(pos, v);
        }
    }

    interface RvItemPrepareListener {
        void onRootItemPrepared(View v);

        void onInitChildRv(View firstView);

        void onChildPrepared(int pos, View v);

        RvItemPrepareListener STUB = new RvItemPrepareListener() {

            @Override
            public void onRootItemPrepared(View v) {/* stub */}

            @Override
            public void onChildPrepared(int pos, View v) { /* stub */ }

            @Override
            public void onInitChildRv(View firstView) { /* stub */ }
        };
    }
}
