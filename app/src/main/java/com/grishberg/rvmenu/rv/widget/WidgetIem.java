package com.grishberg.rvmenu.rv.widget;

public class WidgetIem {
    private final String title;
	private final String description;
	private final int iconRes;

	public WidgetIem(String title, String description, int iconRes) {
		this.title = title;
		this.description = description;
		this.iconRes = iconRes;
	}
	
	public String getDescription() {
		return description;
	}

	public int getIconRes() {
		return iconRes;
	}

    public String getTitle() {
        return title;
    }
}
