package com.grishberg.asynclayout;

import android.view.View;

import com.grishberg.rvmenu.rv.widget.WidgetChildVh;

public interface Binder {
    void bind(int pos, View view);

    void bind(int pos, WidgetChildVh vh);

    int itemsCount();

    Binder STUB = new Binder() {
        @Override
        public void bind(int pos, View view) {
            /* stub */
        }

        @Override
        public void bind(int pos, WidgetChildVh vh) {
            /* stub */
        }

        @Override
        public int itemsCount() {
            return 0;
        }
    };
}
