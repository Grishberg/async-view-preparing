package com.grishberg.asynclayout;

import android.view.View;

public interface Binder<T> {
    void bind(int pos, View view);

    void bind(int pos, T vh);

    int itemsCount();

    Binder STUB = new Binder() {
        @Override
        public void bind(int pos, View view) {
            /* stub */
        }

        @Override
        public void bind(int pos, Object vh) {
            /* stub */
        }

        @Override
        public int itemsCount() {
            return 0;
        }
    };
}
