package com.rickandmorty.adapter.holder;

import android.view.View;
import android.widget.ProgressBar;
import androidx.recyclerview.widget.RecyclerView;
import com.rickandmorty.R;

public class LoadingMoreViewHolder extends RecyclerView.ViewHolder {
  public ProgressBar mProgressBar;

  public LoadingMoreViewHolder(View itemView) {
    super(itemView);
    mProgressBar= (ProgressBar) itemView.findViewById(R.id.progressBar);
  }

}
