package com.grishberg.rvmenu.rv.widget;
import com.grishberg.asynclayout.*;
import android.content.*;
import android.support.v4.print.*;
import com.grishberg.rvmenu.*;

public class WidgetChildDimension implements DimensionProvider
{
	private final int w;
	private final int h;

	public WidgetChildDimension(Context c) {
		w = c.getResources().getDimensionPixelSize(R.dimen.childWidth);
		h = c.getResources().getDimensionPixelSize(R.dimen.childHeight);
	}

	@Override
	public int getWidth() {
		// TODO: Implement this method
		return w;
	}

	@Override
	public int getHeight() {
		// TODO: Implement this method
		return h;
	}
}
