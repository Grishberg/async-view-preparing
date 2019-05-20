package com.grishberg.asynclayout;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.util.*;
import com.grishberg.rvmenu.common.*;

public class PrepareTask extends AsyncTask<Void, Pair<Integer, View>, Void>
{
	private final ViewProvider provider;
	private final DimensionProvider dp;
	private final ViewPreparedListener listener;
	private final int count;
	private final int startPos;
	private final L log;

	public PrepareTask(ViewProvider provider, 
			DimensionProvider dp, 
			ViewPreparedListener listener, 
			int count, int startPos,
			L l) {
		this.provider = provider;
		this.dp = dp;
		this.listener = listener;
		this.count = count;
		this.startPos = startPos;
		log = l;
	}
	
	@Override
	protected Void doInBackground(Void[] p1)
	{
		log.d("PT", "do in background: s="+startPos + ", "+count);
		for(int i = startPos; i < count; i++){
			View v = provider.getView(0);
			v.measure(
				MeasureSpec.makeMeasureSpec(dp.getWidth(), MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(dp.getHeight(), MeasureSpec.EXACTLY));
			this.publishProgress(new Pair<>(i,v));
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Pair<Integer, View>[] values) {
		log.d("PT", "on progress update");
		if(isCancelled() || values.length == 0) {
			return;
		}
		
		listener.onViewPrepared(values[0].first, values[0].second);
	}
}
