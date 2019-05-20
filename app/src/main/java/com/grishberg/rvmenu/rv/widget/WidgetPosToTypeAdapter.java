package com.grishberg.rvmenu.rv.widget;

import com.grishberg.asynclayout.PosToTypeAdapter;

public class WidgetPosToTypeAdapter implements PosToTypeAdapter {

    @Override
    public int typeByPos(int pos) {
        return pos;
    }
}
