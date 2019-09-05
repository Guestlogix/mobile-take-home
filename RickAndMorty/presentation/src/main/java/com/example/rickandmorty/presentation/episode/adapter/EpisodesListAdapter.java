package com.example.rickandmorty.presentation.episode.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rickandmorty.R;
import com.example.rickandmorty.presentation.episode.callback.EpisodeClickListener;
import com.example.rickandmorty.presentation.episode.model.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodesListAdapter extends RecyclerView.Adapter<EpisodesListAdapter.EpisodeViewHolder> {

    private List<Episode> mEpisodeList;
    private LayoutInflater mLayoutInflater;
    private EpisodeClickListener mEpisodeClickListener;

    public EpisodesListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mEpisodeList = new ArrayList<>();
    }

    public void setEpisodeListViewModel(final List<Episode> episodeList){
        mEpisodeList.addAll(episodeList);
        notifyDataSetChanged();
    }

    public void setEpisodeClickListener(EpisodeClickListener episodeClickListener) {
        mEpisodeClickListener = episodeClickListener;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.episode_list_item, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder episodeViewHolder, int position) {
        episodeViewHolder.mEpisodeName.setText(mEpisodeList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mEpisodeList.size();
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mEpisodeName;
        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            mEpisodeName = itemView.findViewById(R.id.episodeName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            mEpisodeClickListener.onClick(v, position, mEpisodeList.get(position).getCharacters());
        }
    }
}
