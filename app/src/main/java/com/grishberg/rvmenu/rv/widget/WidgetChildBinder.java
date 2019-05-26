package com.grishberg.rvmenu.rv.widget;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grishberg.asynclayout.VhBinder;
import com.grishberg.rvmenu.R;

import java.util.List;

public class WidgetChildBinder implements VhBinder<WidgetChildVh> {
    private final List<WidgetIem> titles;

    public WidgetChildBinder(List<WidgetIem> titles) {
        this.titles = titles;
    }

    @Override
    public void bind(int pos, View view) {
        TextView title = view.findViewById(R.id.title);
        title.setText(titles.get(pos).getTitle());
        TextView descr = view.findViewById(R.id.description);
        descr.setText(titles.get(pos).getDescription());

        ImageView icon = view.findViewById(R.id.icon);
        icon.setImageResource(titles.get(pos).getIconRes());
    }

    @Override
    public void bind(int pos, WidgetChildVh vh) {
        vh.bind(titles.get(pos));
    }

    @Override
    public int itemsCount() {
        return titles.size();
    }
}
