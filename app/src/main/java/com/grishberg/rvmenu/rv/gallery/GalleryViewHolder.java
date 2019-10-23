package com.grishberg.rvmenu.rv.gallery;
import android.support.v7.widget.*;
import android.view.*;
import com.grishberg.rvmenu.*;
import android.widget.*;
import com.grishberg.rvmenu.common.*;
import com.grishberg.rvmenu.rv.*;

public class GalleryViewHolder extends MenuViewHolder {
	private static final String T = "WVH";
    private final RecyclerView rv;
    private final L log;

    public GalleryViewHolder(View v, L l) {
        super(v);
        rv = v.findViewById(R.id.widgetRv);
        log = l;
    }

    public void bind(Item i) {
        // TODO: Implement this method
    }
}
