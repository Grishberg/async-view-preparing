package com.grishberg.rvmenu.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.grishberg.consoleview.Logger;
import com.github.grishberg.consoleview.LoggerImpl;
import com.grishberg.rvmenu.R;
import com.grishberg.rvmenu.TopSpaceItemDecorator;
import com.grishberg.rvmenu.menu.BarVisibilityListener;
import com.grishberg.rvmenu.menu.MenuPositionListener;
import com.grishberg.rvmenu.menu.MenuScrollDelegate;
import com.grishberg.rvmenu.menu.MenuVisibility;
import com.grishberg.rvmenu.rv.widget.WidgetDimensions;

public class ItemsRecyclerView extends RecyclerView {
    private final String T = "RV";
    private final Logger log = new LoggerImpl();
    private final TopSpaceItemDecorator decorator;
    private final int firstItemVisibleOffset;
    private int topOffset;
    private int scrollOffset;
    private int height;
    private WidgetDimensions dp;
    private final MenuScrollDelegate scrollDelegate = new MenuScrollDelegate(log, 600);

    public ItemsRecyclerView(Context c) {
        this(c, null);
    }

    public ItemsRecyclerView(Context c, AttributeSet a) {
        this(c, a, 0);
    }

    public ItemsRecyclerView(Context c, AttributeSet a, int s) {
        super(c, a, s);
        LayoutManager lm = new ItemsLayoutManager(c);
        decorator = new TopSpaceItemDecorator(lm);
        setLayoutManager(lm);
        addItemDecoration(decorator, 0);
        firstItemVisibleOffset = c.getResources().getDimensionPixelSize(R.dimen.topItemOffset);
    }

    public void setDimensionProvider(WidgetDimensions d) {
        dp = d;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dp.setWidth(w);
        height = h;
        topOffset = h - firstItemVisibleOffset;
        decorator.updateTopOffset(topOffset);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        scrollOffset += dy;
        scrollDelegate.onScrolled(dy);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int y = (int) e.getY();
        if (e.getAction() == MotionEvent.ACTION_DOWN && y < topOffset - scrollOffset) {
            return false;
        }

        return super.onTouchEvent(e);
    }

    public void addPositionListener(MenuPositionListener l) {
        scrollDelegate.addPositionListener(l);
    }

    public void addBarVisibilityListener(BarVisibilityListener l) {
        scrollDelegate.addBarVisibilityListener(l);
    }

    public void addMenuVisibilityListener(MenuVisibility l) {
        scrollDelegate.addVisibilityListener(l);
    }
}
