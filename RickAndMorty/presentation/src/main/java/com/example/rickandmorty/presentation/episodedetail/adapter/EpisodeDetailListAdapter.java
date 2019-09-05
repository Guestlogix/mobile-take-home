package com.example.rickandmorty.presentation.episodedetail.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rickandmorty.R;
import com.example.rickandmorty.presentation.episodedetail.callback.EpisodeDetailClickListener;
import com.example.rickandmorty.presentation.episodedetail.model.Character;

import java.util.ArrayList;
import java.util.List;

public class EpisodeDetailListAdapter extends RecyclerView.Adapter<EpisodeDetailListAdapter.EpisodeDetailViewHolder> {

    private List<Character> mCharacterList;
    private LayoutInflater mLayoutInflator;
    private EpisodeDetailClickListener mEpisodeDetailClickListener;

    public EpisodeDetailListAdapter(final Context context) {
        mLayoutInflator = LayoutInflater.from(context);
        mCharacterList = new ArrayList<>();
        mEpisodeDetailClickListener = null;
    }

    public void setOnCharacterSelectedClickListener(EpisodeDetailClickListener episodeDetailClickListener) {
        mEpisodeDetailClickListener = episodeDetailClickListener;
    }

    public void showCharactersListViewModel(List<Character> charactersList) {
        mCharacterList.clear();
        mCharacterList.addAll(charactersList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EpisodeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflator.inflate(R.layout.episodedetail_list_item, viewGroup, false);
        return new EpisodeDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeDetailViewHolder viewHolder, int position) {
        viewHolder.characterName.setText(mCharacterList.get(position).getName());
        viewHolder.lifeStatus.setText(mCharacterList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    public class EpisodeDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView characterName;
        private TextView lifeStatus;

        public EpisodeDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            characterName = itemView.findViewById(R.id.character_name);
            lifeStatus = itemView.findViewById(R.id.character_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            if (mEpisodeDetailClickListener != null) {
                mEpisodeDetailClickListener.onClick(v, position, mCharacterList.get(position));
            }
        }
    }
}
