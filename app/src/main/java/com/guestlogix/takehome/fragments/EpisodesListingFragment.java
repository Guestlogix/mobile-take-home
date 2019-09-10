package com.guestlogix.takehome.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.Utils.Optional;
import com.guestlogix.takehome.models.Episode;
import com.guestlogix.takehome.network.NetworkState;
import com.guestlogix.takehome.viewmodels.EpisodesListingViewModel;

public class EpisodesListingFragment extends Fragment {

    public EpisodesListingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView rvEpisodes = (RecyclerView) inflater.inflate(R.layout.fragment_main, container, false);

        Optional.ofNullable(getContext()).ifPresent(context -> {
            rvEpisodes.setLayoutManager(new LinearLayoutManager(getContext()));
            rvEpisodes.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            rvEpisodes.setAdapter(new EpisodesListAdapter(context));
        });

        EpisodesListingViewModel viewModel = ViewModelProviders.of(this).get(EpisodesListingViewModel.class);
        viewModel.getEpisodes().observe(this, episodes ->
                Optional.ofNullable(rvEpisodes.getAdapter())
                        .map(adapter -> (EpisodesListAdapter) adapter)
                        .ifPresent(adapter -> adapter.submitList(episodes))
        );

        viewModel.getNetworkState().observe(this, networkState ->
                Optional.ofNullable(rvEpisodes.getAdapter())
                        .map(adapter -> (EpisodesListAdapter) adapter)
                        .ifPresent(adapter -> adapter.setNetworkState(networkState))
        );


        return rvEpisodes;
    }

    public class EpisodesListAdapter extends PagedListAdapter<Episode, RecyclerView.ViewHolder> {

        private static final int TYPE_PROGRESS = 0;
        private static final int TYPE_ITEM = 1;

        private Context context;
        private NetworkState networkState;

        /*
         * The DiffUtil is defined in the constructor
         */
        EpisodesListAdapter(Context context) {
            super(Episode.DIFF_CALLBACK);
            this.context = context;
        }


        /*
         * Default method of RecyclerView.Adapter
         */
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType == TYPE_PROGRESS) {
                ProgressBar pb = new ProgressBar(context);
                return new NetworkStateItemViewHolder(pb);

            } else {
                TextView tv = new TextView(context);
                tv.setHeight(context.getResources().getDimensionPixelSize(R.dimen.row_height));
                return new ArticleItemViewHolder(tv);
            }
        }


        /*
         * Default method of RecyclerView.Adapter
         */
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ArticleItemViewHolder) {
                ((ArticleItemViewHolder)holder).bindTo(getItem(position));
            } else {
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
            }
        }


        /*
         * Default method of RecyclerView.Adapter
         */
        @Override
        public int getItemViewType(int position) {
            if (hasExtraRow() && position == getItemCount() - 1) {
                return TYPE_PROGRESS;
            } else {
                return TYPE_ITEM;
            }
        }


        private boolean hasExtraRow() {
            return networkState != null && networkState != NetworkState.DONE;
        }

        void setNetworkState(NetworkState newNetworkState) {
            NetworkState previousState = this.networkState;
            boolean previousExtraRow = hasExtraRow();
            this.networkState = newNetworkState;
            boolean newExtraRow = hasExtraRow();
            if (previousExtraRow != newExtraRow) {
                if (previousExtraRow) {
                    notifyItemRemoved(getItemCount());
                } else {
                    notifyItemInserted(getItemCount());
                }
            } else if (newExtraRow && previousState != newNetworkState) {
                notifyItemChanged(getItemCount() - 1);
            }
        }

        class ArticleItemViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;
            ArticleItemViewHolder(TextView tv) {
                super(tv);
                this.textView = tv;
            }

            void bindTo(Episode episode) {
                textView.setText(episode.getEpisode());
            }
        }

        class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

            private ProgressBar progressBar;
            NetworkStateItemViewHolder(ProgressBar pb) {
                super(pb);
                this.progressBar = pb;
            }

            void bindView(NetworkState networkState) {
                if (networkState == NetworkState.LOADING) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        }
    }
}
