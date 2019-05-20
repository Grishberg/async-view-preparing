package com.grishberg.rvmenu.rv.widget;
import com.grishberg.asynclayout.*;
import android.view.*;
import com.grishberg.rvmenu.R;

public class WidgetsRootProvider implements ViewProvider
{
	private final LayoutInflater inflater;
	private final ViewGroup inflateParent;
	
	public WidgetsRootProvider(LayoutInflater inflater,
		ViewGroup inflateParent) {
		this.inflater = inflater;
		this.inflateParent = inflateParent;
	}

	@Override
	public View getView(int type) {
		return inflater.inflate(R.layout.widget_layout, inflateParent, false); 
	}
}
