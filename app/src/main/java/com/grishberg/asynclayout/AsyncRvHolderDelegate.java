package com.grishberg.asynclayout;
import android.support.annotation.*;
import android.view.*;
import com.grishberg.rvmenu.common.*;

public class AsyncRvHolderDelegate
{
	private static final String T ="[AsyncDelegate]";
	private final ViewProvider parentProvider;
	private final ViewProvider childProvider;
	private final PosToTypeAdapter typeAdapter;
	@IdRes
	private final int rvId;
	//private final LayoutInflater inflater;
	private final DimensionProvider parentDimensions;
	private final DimensionProvider childDimensions;
	private final L log;
	private PrepareTask parentPrepareTask;
	private PrepareTask childrenPrepareTask;
	private RvItemPrepareListener listener = RvItemPrepareListener.STUB;
	private boolean firstChildPrepared;

	public AsyncRvHolderDelegate(
			ViewProvider parentProvider, 
			ViewProvider childProvider, 
			PosToTypeAdapter typeAdapter, 
			int rvId, 
			DimensionProvider parentDimensions, 
			DimensionProvider childDimensions, 
			L log) {
		this.parentProvider = parentProvider;
		this.childProvider = childProvider;
		this.typeAdapter = typeAdapter;
		this.rvId = rvId;
		//this.inflater = inflater;
		this.parentDimensions = parentDimensions;
		this.childDimensions = childDimensions;
		this.log = log;
	}

	public void setListener(RvItemPrepareListener listener) {
		this.listener = listener;
	}

	public void prepareAsync() {
		parentPrepareTask = new PrepareTask (
			parentProvider,
			parentDimensions, 
			new ParentPrepareListener(),
			1, 0, log);
		parentPrepareTask.execute();
	}
	
	public void prepareChildren(int count) {
		log.d(T, "start prepare children");
		childrenPrepareTask = new PrepareTask(
			childProvider,
			childDimensions,
			new ChildrenPrepareListener(),
			count,0, log
		);
		childrenPrepareTask.execute();
	}
	
	private class ParentPrepareListener implements ViewPreparedListener {

		@Override
		public void onViewPrepared(int pos, View v) {
			// on parent prepared
			log.d(T,"on parent prepared");
			listener.onRootItemPrepared(v);
		}
		
	}
	
	private class ChildrenPrepareListener implements ViewPreparedListener {

		@Override
		public void onViewPrepared(int pos, View v) {
			log.d(T,"on view prepared pos="+pos);
			// on child prepared
			if(!firstChildPrepared){
				listener.onInitChildRv(v);
				firstChildPrepared = true;
				return;
			}
			listener.onChildPrepared(pos, v);
		}
	}
	
	public interface RvItemPrepareListener {
		void onRootItemPrepared(View v);
		void onInitChildRv(View firstView);
		void onChildPrepared(int pos, View v);
		
		public static RvItemPrepareListener STUB = new RvItemPrepareListener(){

			@Override
			public void onRootItemPrepared(View v) {/* stub */}

			@Override
			public void onChildPrepared(int pos, View v) { /* stub */ }

			@Override
			public void onInitChildRv(View firstView) { /* stub */ }
		};
	}
	
}
