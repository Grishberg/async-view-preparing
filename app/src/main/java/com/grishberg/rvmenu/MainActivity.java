package com.grishberg.rvmenu;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.Toast;

import com.github.grishberg.consoleview.LoggerImpl;
import com.grishberg.asynclayout.AsyncRvDelegate;
import com.grishberg.asynclayout.VhBinder;
import com.grishberg.rvmenu.common.L;
import com.grishberg.rvmenu.menu.BarVisibilityListener;
import com.grishberg.rvmenu.menu.MenuVisibility;
import com.grishberg.rvmenu.rv.ItemsAdapter;
import com.grishberg.rvmenu.rv.ItemsRecyclerView;
import com.grishberg.rvmenu.rv.widget.WidgetChildBinder;
import com.grishberg.rvmenu.rv.widget.WidgetChildVh;
import com.grishberg.rvmenu.rv.widget.WidgetDimensions;
import com.grishberg.rvmenu.rv.widget.WidgetIem;
import com.grishberg.rvmenu.rv.widget.WidgetPosToTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private Rect touchHitRect = new Rect();
    private View bar;
    private L log;
    private ItemsAdapter adapter;
    private ItemsRecyclerView rv;
    private WidgetDimensions widgetDimensions;
    private AsyncRvDelegate asyncRvDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        log = new L(new LoggerImpl());
        ViewGroup container = findViewById(R.id.container);
        rv = new ItemsRecyclerView(this);
        rv.setLayoutParams(new ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        container.addView(rv, 0);

        widgetDimensions = new WidgetDimensions(this);

        rv.setDimensionProvider(widgetDimensions);
        VhBinder<WidgetChildVh> binder = new WidgetChildBinder(createWidgetData());

        asyncRvDelegate = new AsyncRvDelegate();

        adapter = new ItemsAdapter(this,
                LayoutInflater.from(this),
                widgetDimensions,
                new WidgetPosToTypeAdapter(),
                binder,
                asyncRvDelegate,
                log);
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
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        items.add(new WidgetIem("Widget item 1",
                "This is description for created in worker-thread for RecyclerView item. It created and measured in worker thread", R.drawable.ic_widget1));
        items.add(new WidgetIem("Widget item 2",
                "I am on mobius, it's very interrsting meetup 2", R.drawable.ic_widget2));
        items.add(new WidgetIem("Widget item 3",
                "I can help you with some problems with running espresso tests.", R.drawable.ic_widget3));

        return items;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asyncRvDelegate.clean();
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
