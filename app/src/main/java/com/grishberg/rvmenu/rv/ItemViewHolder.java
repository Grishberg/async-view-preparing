package com.grishberg.rvmenu.rv;

import android.view.View;
import android.widget.TextView;

import com.grishberg.rvmenu.Item;
import com.grishberg.rvmenu.R;

public class ItemViewHolder extends MenuViewHolder {
    private final TextView title;

    ItemViewHolder(View v) {
        super(v);
        title = v.findViewById(R.id.title);
    }

    @Override
    public void bind(Item i) {
        title.setText(i.getTitle());
    }
}
