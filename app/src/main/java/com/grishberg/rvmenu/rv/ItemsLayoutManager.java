package com.grishberg.rvmenu.rv;
import android.support.v7.widget.*;
import android.content.*;
import android.support.v7.widget.RecyclerView.*;
import com.github.grishberg.consoleview.*;

public class ItemsLayoutManager extends LinearLayoutManager
{
	private static final String T ="LM";
	private final Logger log = new LoggerImpl();
	
	public ItemsLayoutManager(Context c){
		super(c);
	}
	
}
