package com.grishberg.rvmenu.rv.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.grishberg.rvmenu.R;

public class WidgetChildVh extends RecyclerView.ViewHolder {
    private final TextView title;

    WidgetChildVh(View v) {
        super(v);
        title = v.findViewById(R.id.title);
    }

    void bind(WidgetIem item) {
        title.setText(item.getTitle());
    }
}
