package com.grishberg.rvmenu;

import android.app.*;
import android.os.*;
import android.support.v7.widget.*;

import com.grishberg.asynclayout.Binder;
import com.grishberg.rvmenu.rv.*;

import android.view.*;

import java.util.*;

import android.graphics.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AbsListView.*;

import com.grishberg.rvmenu.menu.*;
import com.github.grishberg.consoleview.*;
import com.grishberg.rvmenu.rv.widget.Widget;
import com.grishberg.rvmenu.rv.widget.WidgetChildBinder;
import com.grishberg.rvmenu.rv.widget.WidgetDimensions;
import com.grishberg.rvmenu.rv.widget.WidgetIem;
import com.grishberg.rvmenu.rv.widget.WidgetPosToTypeAdapter;

public class MainActivity extends Activity {
    private Rect touchHitRect = new Rect();
    private View bar;
    private Logger log;
    private ItemsAdapter adapter;
    private ItemsRecyclerView rv;
    private WidgetDimensions dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        log = new LoggerImpl();
        ViewGroup container = findViewById(R.id.container);
        rv = new ItemsRecyclerView(this);
        rv.setLayoutParams(new ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        container.addView(rv, 0);

        dp = new WidgetDimensions(this);

        rv.setDimensionProvider(dp);
        Binder binder = new WidgetChildBinder(createWidgetData());

        adapter = new ItemsAdapter(this, LayoutInflater.from(this),
                dp, new WidgetPosToTypeAdapter(), binder);
        rv.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        rv.setAdapter(adapter);
        adapter.populate(createData());
        bar = findViewById(R.id.bar);
        bar.post(new Runnable() {
            public void run() {
                setTouchDelegate(bar, rv);
            }
        });
        View button = findViewById(R.id.testButton);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                touchHitRect.offset(dx, dy);
                bar.setTranslationY(bar.getTranslationY() - dy);
            }
        });
        rv.addBarVisibilityListener(new BarVisibility());
        rv.addMenuVisibilityListener(new MenuVisibilityImpl());
    }

    private void setTouchDelegate(View v, View delegate) {
        v.getHitRect(touchHitRect);
        v.setTouchDelegate(new TouchDelegate(touchHitRect, delegate));
    }

    private List<Item> createData() {
        ArrayList<Item> res = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            res.add(new Item("Menu item " + i));
        }
        return res;
    }

    private List<WidgetIem> createWidgetData() {
        ArrayList<WidgetIem> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items.add(new WidgetIem("Widget item " + i));
        }
        return items;
    }

    private class BarVisibility implements BarVisibilityListener {
        @Override
        public void onVisible() {
            log.d("MA", "bar becomes visible");
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onInvisible() {
            log.d("MA", "bar becomes invisible");
            bar.setVisibility(View.GONE);
        }

        @Override
        public void onAlphaChanged(float alpha) {
            bar.setAlpha(1.0f - alpha);
        }
    }

    private class MenuVisibilityImpl implements MenuVisibility {
        @Override
        public void onVisible() {
            log.d("MA", "menu becomes visible");
            adapter.showWidget();
        }

        @Override
        public void onInvisible() {
            log.d("MA", "menu becomes invisible");
        }

        @Override
        public void onAlphaChanged(float alpha) {
            rv.setAlpha(alpha);
        }
    }
}
