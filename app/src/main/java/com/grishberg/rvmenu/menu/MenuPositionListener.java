package com.grishberg.rvmenu.menu;

public interface MenuPositionListener
{
	void onStartExpanding();
	void onCollapsed();
	void onScrolled(int y);
}
