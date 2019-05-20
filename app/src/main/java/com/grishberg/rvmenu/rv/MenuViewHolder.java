package com.grishberg.rvmenu.rv;
import android.support.v7.widget.*;
import android.view.*;
import com.grishberg.rvmenu.*;

public abstract class MenuViewHolder extends RecyclerView.ViewHolder
{
	public MenuViewHolder(View v){
		super(v);
	}
	
	public abstract void bind(Item i);
}
