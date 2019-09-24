package com.guestlogix.takehome.views;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.guestlogix.takehome.network.NetworkState;

public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

    private View progressView;

    public NetworkStateItemViewHolder(View pb) {
        super(pb);
        this.progressView = pb;
    }

    public void bindView(NetworkState networkState) {
        progressView.setVisibility(networkState == NetworkState.LOADING ? View.VISIBLE : View.GONE);
    }
}