package com.grishberg.rvmenu.menu;
import com.github.grishberg.consoleview.*;
import java.util.*;
import android.view.ActionProvider.*;

public class MenuScrollDelegate
{
	private final static String T = "MSD";
	private final Logger log;
	private final ArrayList<MenuPositionListener> positionListener 
		= new ArrayList<>();
	private final ArrayList<MenuVisibility> visibilityListeners
		= new ArrayList<>();
	private final ArrayList<BarVisibilityListener> barVisibilityListeners 
		= new ArrayList<>();
	private int offset;
	private int crossFadePos;
	private float alpha;

	public MenuScrollDelegate(Logger log,int crossFadePos)
	{
		this.log = log;
		this.crossFadePos = crossFadePos;
	}
	
	public void addPositionListener(MenuPositionListener l){
		positionListener.add(l);
	}
	
	public void addVisibilityListener(MenuVisibility l){
		visibilityListeners.add(l);
	}
	
	public void addBarVisibilityListener(BarVisibilityListener l){
		barVisibilityListeners.add(l);
	}
	public void onScrolled(int dy){
		if(offset == 0){
			notifyStartExpanding();
		}
		offset += dy;
		float a =(float) offset / (float)crossFadePos;
		if(a > 1){
			a = 1.f;
		}
		// menu becomes visible
		if(Float.compare(alpha, 0f)==0 && a>0f) {
			notifyVisible();
		}
		// bar becomes visible
		if(a < 1f && Float.compare(alpha, 1f)==0){
			notifyBarVisible();
		}
		if(a != alpha) {
			notifyAlphaChanged(a);
		}
		//menu becomes invisible
		if(Float.compare(a, 0f)==0 && alpha > 0f) {
			notifyInvisible();
		}
		// bar becomes invisible
		if(alpha < 1f && Float.compare(a, 1f)==0){
			notifyBarInvisible();
		}
		alpha = a;
		notifyScrolled(offset);
		if(offset == 0){
			notifyCollapsed();
		}
	}
	
	private void notifyStartExpanding(){
		for(int i= 0; i < positionListener.size(); i++){
			positionListener.get(i).onStartExpanding();
		}
	}
	private void notifyCollapsed(){
		for(int i= 0; i < positionListener.size(); i++){
			positionListener.get(i).onCollapsed();
		}
	}
	private void notifyScrolled(int pos){
		for(int i= 0; i < positionListener.size(); i++){
			positionListener.get(i).onScrolled(pos);
		}
	}
	
	private void notifyVisible(){
		for(int i= 0; i < visibilityListeners.size(); i++){
			visibilityListeners.get(i).onVisible();
		}
	}
	
	private void notifyInvisible(){
		for(int i = 0; i < visibilityListeners.size(); i++){
			visibilityListeners.get(i).onInvisible();
		}
	}
	
	private void notifyAlphaChanged(float alpha){
		for(int i = 0; i < visibilityListeners.size(); i++){
			visibilityListeners.get(i).onAlphaChanged(alpha);
		}
		for(int i = 0; i < barVisibilityListeners.size(); i++){
			barVisibilityListeners.get(i).onAlphaChanged(alpha);
		}
	}
	
	private void notifyBarVisible(){
		for(int i = 0; i < barVisibilityListeners.size(); i++){
			barVisibilityListeners.get(i).onVisible();
		}
	}
	
	private void notifyBarInvisible(){
		for(int i = 0; i < barVisibilityListeners.size(); i++){
			barVisibilityListeners.get(i).onInvisible();
		}
	}
}
