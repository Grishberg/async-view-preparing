package com.grishberg.rvmenu.common;

import android.support.annotation.Nullable;

import com.grishberg.asynclayout.Provider;

public abstract class LazyProvider<T> implements Provider<T> {
    @Nullable
    private T value;

    @Override
    public T get() {
        if (value == null) {
            value = create();
        }
        return value;
    }

    protected abstract T create();
}
