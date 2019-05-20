package com.grishberg.rvmenu.rv.widget;
import android.view.*;

public interface Widget
{
	void initWidgets(View firstItem);
	void addWidget(int pos, View v);
}
