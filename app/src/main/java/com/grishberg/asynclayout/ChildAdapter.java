package com.grishberg.asynclayout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class ChildAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> implements ViewPreparedListener {
    private final ArrayList<View> viewCache = new ArrayList<>();
    private final PosToTypeAdapter posToTypeAdapter;

    public ChildAdapter(PosToTypeAdapter posToTypeAdapter) {
        this.posToTypeAdapter = posToTypeAdapter;
    }


    @Override
    public void onViewPrepared(int pos, View v) {
        viewCache.add(v);
        notifyItemInserted(pos);
    }

    @Override
    public int getItemViewType(int position) {
        return posToTypeAdapter.typeByPos(position);
    }

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateViewHolder(viewCache.get(viewType));
    }

    protected abstract T onCreateViewHolder(View view);

    View getViewFromCache(int pos) {
        return viewCache.get(pos);
    }

    View popViewFromCache(int pos) {
        View view = viewCache.get(pos);
        viewCache.remove(pos);
        return view;
    }

    @Override
    public int getItemCount() {
        return viewCache.size();
    }
}
