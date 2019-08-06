package com.rickandmorty.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rickandmorty.R;
import com.rickandmorty.adapter.holder.EpisodeViewHolder;
import com.rickandmorty.adapter.holder.LoadingMoreViewHolder;
import com.rickandmorty.databinding.ViewItemEpisodeBinding;
import com.rickandmorty.model.EpisodeModel;
import java.util.List;

public class EpisodeItemsAdapter extends BaseAdapter {

  private List episodesList;

  public EpisodeItemsAdapter setEpisodesList(List episodes) {
    episodesList = episodes;
    notifyDataSetChanged();
    return this;
  }

  private List getEpisodesList() {
    return episodesList;
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == TYPE_ITEM) {
      View view = EpisodeViewHolder.getView(parent);
      return new EpisodeViewHolder(view);
    } else {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_loading_more, parent, false);
      return new LoadingMoreViewHolder(view);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
    if (holder instanceof EpisodeViewHolder) {
      final EpisodeViewHolder viewHolder = (EpisodeViewHolder) holder;
      ViewItemEpisodeBinding viewItemEpisodeBinding = viewHolder.mBinding;
      final EpisodeModel episodeModel = (EpisodeModel) getEpisodesList().get(position);

      //viewItemEpisodeBinding.imgItem.setImageURI(episodeModel.getUrl());
      viewItemEpisodeBinding.tvEpisode.setText(episodeModel.getEpisode());
      viewItemEpisodeBinding.tvEpisodeName.setText(episodeModel.getName());
      viewItemEpisodeBinding.tvAirDate.setText(episodeModel.getAir_date());

      viewItemEpisodeBinding.getRoot().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mItemOnClick != null) {
            mItemOnClick.onClickItem(v, viewHolder, TAG_ITEM_CLICK, episodeModel, position);
          }
        }
      });
    } else {
      LoadingMoreViewHolder loadingMoreViewHolder = (LoadingMoreViewHolder) holder;
      if (isStillLoadingMore) {
        loadingMoreViewHolder.mProgressBar.setVisibility(View.VISIBLE);
      } else {
        loadingMoreViewHolder.mProgressBar.setVisibility(View.GONE);
      }
    }
  }

  @Override
  public int getItemCount() {
    return episodesList.size() + (isHasFooter ? 1 : 0);
  }

  private boolean isPositionFooter(int position) {
    return (position >= episodesList.size()) && isHasFooter;
  }

  @Override
  public int getItemViewType(int position) {
    if (isPositionFooter(position)) {
      return TYPE_FOOTER;
    } else {
      return TYPE_ITEM;
    }
  }
}
