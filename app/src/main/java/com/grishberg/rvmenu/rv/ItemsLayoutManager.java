package com.grishberg.rvmenu.rv;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.github.grishberg.consoleview.Logger;
import com.github.grishberg.consoleview.LoggerImpl;

public class ItemsLayoutManager extends LinearLayoutManager {
    private static final String T = "LM";
    private final Logger log = new LoggerImpl();

    public ItemsLayoutManager(Context c) {
        super(c);
    }
}
