package com.grishberg.rvmenu.rv.gallery;
import android.widget.*;
import android.view.*;
import android.support.v7.widget.*;
import com.grishberg.rvmenu.*;

public class GalleryChildViewHolder extends RecyclerView.ViewHolder
{
	private final TextView titleView;
	GalleryChildViewHolder(View v){
		super(v);
		titleView = v.findViewById(R.id.title);
	}

	public void setTitle(String title){
		titleView.setText(title);
	}
	
}
