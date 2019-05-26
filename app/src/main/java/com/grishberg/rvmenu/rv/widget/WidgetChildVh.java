package com.grishberg.rvmenu.rv.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grishberg.rvmenu.R;

public class WidgetChildVh extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView description;
    private final ImageView icon;

    WidgetChildVh(View v) {
        super(v);
        title = v.findViewById(R.id.title);
        description = v.findViewById(R.id.description);
        icon = v.findViewById(R.id.icon);
    }

    void bind(WidgetIem item) {
        title.setText(item.getTitle());
        description.setText(item.getDescription());
        icon.setImageResource(item.getIconRes());
    }
}
