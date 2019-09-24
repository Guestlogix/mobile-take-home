package com.guestlogix.takehome.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.binding.DataBindingComponentImpl;
import com.guestlogix.takehome.databinding.FragmentEpisodeListingBinding;
import com.guestlogix.takehome.databinding.ItemEpisodeRowBinding;
import com.guestlogix.takehome.models.EpisodeRowStub;
import com.guestlogix.takehome.viewmodels.EpisodesListViewModel;
import com.guestlogix.takehome.viewmodels.ViewModelFactory;
import com.guestlogix.takehome.views.NetworkStateItemViewHolder;

public class EpisodesListingFragment extends BaseFragment {

    private EpisodesListViewModel mEpisodesViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentEpisodeListingBinding binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_episode_listing,
            container,
            false,
            new DataBindingComponentImpl(getViewLifecycleOwner())
        );

        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(requireActivity().getApplication());
        mEpisodesViewModel = ViewModelProviders.of(requireActivity(), factory).get(EpisodesListViewModel.class);

        binding.setViewmodel(mEpisodesViewModel);

        binding.rvEpisodes.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvEpisodes.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.rvEpisodes.setAdapter(new EpisodesListAdapter(requireContext()));


        mEpisodesViewModel.navigateToCharacterList.observe(this, characters ->
            findNavController().ifPresent(navController ->
                navController.navigate(
                    EpisodesListingFragmentDirections.actionEpisodesListingFragmentToCharactersListFragment(characters)
                )
            )
        );

        return binding.getRoot();
    }

    public class EpisodesListAdapter extends PagedListAdapter<EpisodeRowStub, RecyclerView.ViewHolder> {

        private static final int TYPE_PROGRESS = 0;
        private static final int TYPE_ITEM = 1;

        private Context context;
        private boolean isLoading;

        /*
         * The DiffUtil is defined in the constructor
         */
        EpisodesListAdapter(Context context) {
            super(EpisodeRowStub.DIFF_CALLBACK);
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
                ((NetworkStateItemViewHolder) holder).bindView(isLoading);
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

        @Override
        public int getItemCount() {
            return hasExtraRow() ? super.getItemCount() + 1 : super.getItemCount();
        }

        private boolean hasExtraRow() {
            return isLoading;
        }

        public void setNetworkState(boolean isLoading) {
            boolean previousExtraRow = hasExtraRow();
            this.isLoading = isLoading;
            boolean newExtraRow = hasExtraRow();

            if (previousExtraRow != newExtraRow) {
                if (previousExtraRow) {
                    notifyItemRemoved(getItemCount()-1);
                } else {
                    notifyItemInserted(getItemCount()+1);
                }
            }
        }

        class EpisodeItemViewHolder extends RecyclerView.ViewHolder {

            private ItemEpisodeRowBinding binding;
            EpisodeItemViewHolder(ItemEpisodeRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            void bindTo(EpisodeRowStub episode) {
                binding.setData(episode);

                binding.getRoot().setOnClickListener(v ->
                    findNavController().ifPresent(navController ->
                        mEpisodesViewModel.onEpisodeClicked(episode)
                    )
                );
            }
        }
    }
}
