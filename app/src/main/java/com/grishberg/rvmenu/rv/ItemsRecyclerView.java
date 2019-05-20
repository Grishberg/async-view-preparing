package com.grishberg.rvmenu.rv;
import android.support.v7.widget.*;
import android.content.*;
import android.util.*;
import com.grishberg.rvmenu.*;
import com.grishberg.rvmenu.R;

import com.github.grishberg.consoleview.*;
import android.view.*;
import com.grishberg.rvmenu.menu.*;
import com.grishberg.rvmenu.rv.widget.WidgetDimensions;

public class ItemsRecyclerView extends RecyclerView
{
	private final String T = "RV";
	private final Logger log = new LoggerImpl();
	private final TopSpaceItemDecorator decorator;
	private final int firstItemVisibleOffset;
	private int topOffset;
	private int scrollOffset;
	private int height;
	private WidgetDimensions dp;
	private final MenuScrollDelegate scrollDelegate = new MenuScrollDelegate(log, 600);
	
	public ItemsRecyclerView(Context c){
		this(c, null);
	}
	
	public ItemsRecyclerView(Context c, AttributeSet a){
		this(c, a, 0);
	}
	
	public ItemsRecyclerView(Context c, AttributeSet a, int s){
		super(c, a, s);
		LayoutManager lm = new ItemsLayoutManager(c);
		decorator = new TopSpaceItemDecorator(lm);
		setLayoutManager(lm);
		addItemDecoration(decorator, 0);
		firstItemVisibleOffset = c.getResources().getDimensionPixelSize(R.dimen.topItemOffset);
	}
	
	public void setDimensionProvider(WidgetDimensions d){
		dp = d;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		dp.setWidth(w);
		height = h;
		topOffset = h - firstItemVisibleOffset;
		decorator.updateTopOffset(topOffset);
	}

	@Override
	public void onScrolled(int dx, int dy)
	{
		super.onScrolled(dx, dy);
		scrollOffset += dy;
		scrollDelegate.onScrolled(dy);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		int y = (int) e.getY();
		if(e.getAction() == MotionEvent.ACTION_DOWN && y  < topOffset - scrollOffset){
			return false;
		}
		
		return super.onTouchEvent(e);
	}
	
	public void addPositionListener(MenuPositionListener l){
		scrollDelegate.addPositionListener(l);
	}
	
	
	public void addBarVisibilityListener(BarVisibilityListener l){
		scrollDelegate.addBarVisibilityListener(l);
	}
	
	public void addMenuVisibilityListener(MenuVisibility l){
		scrollDelegate.addVisibilityListener(l);
	}
}
