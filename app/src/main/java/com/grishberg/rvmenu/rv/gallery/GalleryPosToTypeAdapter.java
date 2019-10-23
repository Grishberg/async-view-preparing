package com.grishberg.rvmenu.rv.gallery;

import com.grishberg.asynclayout.PosToTypeAdapter;

public class GalleryPosToTypeAdapter implements PosToTypeAdapter {
    @Override
    public int typeByPos(int pos) {
        return pos;
    }
}
