package com.grishberg.rvmenu.rv.widget;

import android.view.View;
import android.widget.TextView;

import com.grishberg.asynclayout.Binder;
import com.grishberg.rvmenu.R;

import java.util.List;

public class WidgetChildBinder implements Binder {
    private final List<WidgetIem> titles;

    public WidgetChildBinder(List<WidgetIem> titles) {
        this.titles = titles;
    }

    @Override
    public void bind(int pos, View view) {
        TextView title = view.findViewById(R.id.title);
        title.setText(titles.get(pos).getTitle());
    }

    @Override
    public void bind(int pos, WidgetChildVh vh) {
        vh.bind(titles.get(pos).getTitle());
    }

    @Override
    public int itemsCount() {
        return titles.size();
    }
}
