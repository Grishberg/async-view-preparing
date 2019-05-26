package com.grishberg.rvmenu.rv;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.grishberg.rvmenu.common.L;

/**
 * View group with cached dimensions.
 */
public class CachedMeasureLayout extends FrameLayout {
    private L log = new L();

    public CachedMeasureLayout(Context c) {
        this(c, null);
    }

    public CachedMeasureLayout(Context c, AttributeSet a) {
        this(c, a, 0);
    }

    public CachedMeasureLayout(Context c, AttributeSet a, int s) {
        super(c, a, s);
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
        long d = SystemClock.uptimeMillis() - s;
        log.d("CML", "on measure d=" + d + ", t=" + Thread.currentThread());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        long s = SystemClock.uptimeMillis();

        // TODO: Implement this method
        super.onLayout(changed, left, top, right, bottom);
        long d = SystemClock.uptimeMillis() - s;
        log.d("CML", "on layout d=" + d);
    }
}
