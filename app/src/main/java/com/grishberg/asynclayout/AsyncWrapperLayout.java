package com.grishberg.asynclayout;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.grishberg.rvmenu.common.L;

public class AsyncWrapperLayout extends FrameLayout {
    private final L log = new L();
    @LayoutRes
    private int firstLayoutRes;

    @LayoutRes
    private int secondLayoutRes;
    private final ViewGroup inflateRoot;
    private DimensionProvider dp;
    private final LayoutInflater inflater;
    private OnPrepareListener listener;

    public AsyncWrapperLayout(Context c) {
        this(c, null);
    }

    public AsyncWrapperLayout(Context c, AttributeSet a) {
        this(c, a, 0);
    }

    public AsyncWrapperLayout(Context c, AttributeSet a, int s) {
        super(c, a, s);
        inflater = LayoutInflater.from(c);
        inflateRoot = new FrameLayout(c);

        //addView(inflater.inflate(firstLayoutRes, inflateRoot, false));
    }

    public void setSecondLayoutRes(int secondLayoutRes) {
        this.secondLayoutRes = secondLayoutRes;
    }

    public void setFirstLayoutRes(int firstLayoutRes) {
        this.firstLayoutRes = firstLayoutRes;
    }

    public void setListener(OnPrepareListener listener) {
        this.listener = listener;
    }

    public void setDimensionProvider(DimensionProvider d) {
        dp = d;
    }

    public void prepare() {
        new PrepareTask().execute();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long start = SystemClock.uptimeMillis();

        if (getMeasuredWidth() > 0 & getMeasuredHeight() > 0) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
            long duration = SystemClock.uptimeMillis() - start;
            log.d("AWL", "onMeasure fake d=" + duration);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        long d = SystemClock.uptimeMillis() - start;
        log.d("AWL", "on measure d=" + d + ", t=" + Thread.currentThread());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        long start = SystemClock.uptimeMillis();

        // TODO: Implement this method
        super.onLayout(changed, left, top, right, bottom);
        long duration = SystemClock.uptimeMillis() - start;
        log.d("AWL", "onLayout d=" + duration);
    }

    private class PrepareTask extends AsyncTask<Void, Void, View> {
        @Override
        protected View doInBackground(Void[] p1) {
            View v = inflater.inflate(secondLayoutRes, inflateRoot, false);
            v.measure(
                    MeasureSpec.makeMeasureSpec(dp.getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(dp.getHeight(), MeasureSpec.EXACTLY));
            return v;
        }

        @Override
        protected void onPostExecute(View result) {
            long s = SystemClock.uptimeMillis();
            removeAllViews();
            addView(result);
            long d = SystemClock.uptimeMillis() - s;
            log.d("AWL", "onPrepare d=" + d);
            if (listener != null) {
                listener.onViewPrepared(result);
            }
        }
    }

    public interface OnPrepareListener {
        void onViewPrepared(View v);
    }
}
