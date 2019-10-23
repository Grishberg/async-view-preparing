package com.grishberg.rvmenu.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.grishberg.consoleview.LoggerImpl;
import com.grishberg.rvmenu.common.L;
import com.grishberg.rvmenu.*;

public class CustomConstraintLayout extends LinearLayout {
    private L log;

    public CustomConstraintLayout(Context c) {
        this(c, null);
    }

    public CustomConstraintLayout(Context c, AttributeSet a) {
        this(c, a, 0);
    }

    public CustomConstraintLayout(Context c, AttributeSet a, int s) {
        super(c, a, s);
		if(c instanceof LoggerProvider){
			log = ((LoggerProvider) c).getLogger();
		}
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long s = SystemClock.uptimeMillis();
        if (getMeasuredWidth() > 0 & getMeasuredHeight() > 0) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
            long d = SystemClock.uptimeMillis() - s;
            log.d("CML", "on measure fake d=" + d + ", t=" + Thread.currentThread());
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		try
		{
			Thread.sleep(800);
		} catch (InterruptedException e)
		{}
        long d = SystemClock.uptimeMillis() - s;
        log.d("CML", "on measure d=" + d + ", t=" + Thread.currentThread());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        long s = SystemClock.uptimeMillis();

        super.onLayout(changed, left, top, right, bottom);
        long d = SystemClock.uptimeMillis() - s;
        log.d("CML", "on layout d=" + d);
    }
}
