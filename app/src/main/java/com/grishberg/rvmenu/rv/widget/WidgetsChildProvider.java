package com.grishberg.rvmenu.rv.widget;
import com.grishberg.asynclayout.*;
import com.grishberg.rvmenu.R;
import android.view.*;

public class WidgetsChildProvider implements ViewProvider
{
	private final LayoutInflater inflater;
	private final ViewGroup inflateParent;

	public WidgetsChildProvider(LayoutInflater inflater,
							   ViewGroup inflateParent) {
		this.inflater = inflater;
		this.inflateParent = inflateParent;
	}

	@Override
	public View getView(int type) {
		return inflater.inflate(R.layout.widget_item_layout, inflateParent, false); 
	}
}
