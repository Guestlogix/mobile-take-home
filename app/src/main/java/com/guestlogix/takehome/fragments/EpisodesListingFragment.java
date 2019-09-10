package com.guestlogix.takehome.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.Utils.Optional;
import com.guestlogix.takehome.databinding.ItemEpisodeRowBinding;
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
                return new NetworkStateItemViewHolder(
                        LayoutInflater.from(context).inflate(R.layout.item_progress_row, parent, false)
                );

            } else {
                return new EpisodeItemViewHolder(
                        ItemEpisodeRowBinding.inflate(LayoutInflater.from(context), parent, false)
                );
            }
        }


        /*
         * Default method of RecyclerView.Adapter
         */
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof EpisodeItemViewHolder) {
                ((EpisodeItemViewHolder)holder).bindTo(getItem(position));
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

        class EpisodeItemViewHolder extends RecyclerView.ViewHolder {

            private ItemEpisodeRowBinding binding;
            EpisodeItemViewHolder(ItemEpisodeRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;



                binding.getRoot().setOnClickListener(v -> Optional.ofNullable(getActivity())
                        .map( activity -> Navigation.findNavController(activity, R.id.nav_host_fragment))
                        .ifPresent(navController ->
                                navController.navigate(R.id.charactersListFragment)
                        )
                );
            }

            void bindTo(Episode episode) {
                binding.setData(episode);
            }
        }

        class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

            private View progressView;
            NetworkStateItemViewHolder(View pb) {
                super(pb);
                this.progressView = pb;
            }

            void bindView(NetworkState networkState) {
                progressView.setVisibility(networkState == NetworkState.LOADING ? View.VISIBLE : View.GONE);
            }
        }
    }
}
