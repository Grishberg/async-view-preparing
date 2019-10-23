package com.grishberg.asynclayout;

import android.os.AsyncTask;
import android.util.Pair;
import android.view.View;
import android.view.View.MeasureSpec;
import android.util.*;

class PrepareTask extends AsyncTask<Void, Pair<Integer, View>, Void> {
    private final ViewProvider provider;
    private final DimensionProvider dp;
    private final ViewPreparedListener listener;
    private VhBinder binder;
    private final int count;
    private final int startPos;

    PrepareTask(ViewProvider provider,
                DimensionProvider dp,
                VhBinder binder,
                ViewPreparedListener listener,
                int count, int startPos) {
        this.provider = provider;
        this.dp = dp;
        this.listener = listener;
        this.binder = binder;
        this.count = count;
        this.startPos = startPos;
    }

    @SuppressWarnings("WrongThread")
    @Override
    protected Void doInBackground(Void[] p1) {
		Log.d("PrapareTask", "prepare "+provider);
        for (int i = startPos; i < count; i++) {
            View v = provider.getView(0);
            binder.bind(i, v);
            v.measure(
                    MeasureSpec.makeMeasureSpec(dp.getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(dp.getHeight(), MeasureSpec.EXACTLY));
            publishProgress(new Pair<>(i, v));
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Pair<Integer, View>[] values) {
        if (isCancelled() || values.length == 0) {
            return;
        }

        listener.onViewPrepared(values[0].first, values[0].second);
    }
}
