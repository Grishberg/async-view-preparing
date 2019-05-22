package com.grishberg.rvmenu.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.grishberg.rvmenu.Item;

public abstract class MenuViewHolder extends RecyclerView.ViewHolder {
    public MenuViewHolder(View v) {
        super(v);
    }

    public abstract void bind(Item i);
}
