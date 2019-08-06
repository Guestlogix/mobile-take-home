package com.rickandmorty.adapter.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.rickandmorty.R;
import com.rickandmorty.databinding.ViewItemEpisodeBinding;

public class EpisodeViewHolder extends RecyclerView.ViewHolder {

  public ViewItemEpisodeBinding mBinding;

  public EpisodeViewHolder(View itemView) {
    super(itemView);
    mBinding= DataBindingUtil.bind(itemView);
  }

  public static View getView(ViewGroup parent){
    View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_episode, parent, false);
    return inflate;
  }
}
