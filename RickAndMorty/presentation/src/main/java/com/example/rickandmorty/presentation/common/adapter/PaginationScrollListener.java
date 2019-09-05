package com.example.rickandmorty.presentation.common.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private final LayoutManager mLayoutManager;


    public PaginationScrollListener(final LayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        final int visibleItemCount = mLayoutManager.getChildCount();
        final int totalItemCount = mLayoutManager.getItemCount();
        final int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0) {
                loadNextPage();
            }
        }
    }

    public abstract boolean isLoading();
    public abstract boolean isLastPage();
    public abstract void loadNextPage();
}
