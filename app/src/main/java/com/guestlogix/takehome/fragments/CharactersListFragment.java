package com.guestlogix.takehome.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.Utils.Optional;
import com.guestlogix.takehome.databinding.ItemCharactersRowBinding;
import com.guestlogix.takehome.models.Character;
import com.guestlogix.takehome.network.NetworkState;
import com.guestlogix.takehome.network.imageloader.AssetManager;
import com.guestlogix.takehome.viewmodels.CharactersListViewModel;
import com.guestlogix.takehome.viewmodels.factory.CharactersListViewModelFactory;
import com.guestlogix.takehome.views.NetworkStateItemViewHolder;

public class CharactersListFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RecyclerView rvCharacters = (RecyclerView) inflater.inflate(R.layout.fragment_characters_list, container, false);

        Optional.ofNullable(getContext()).ifPresent(context -> {
            rvCharacters.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCharacters.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            rvCharacters.setAdapter(new CharactersListAdapter(context));
        });

        CharactersListViewModelFactory factory = new CharactersListViewModelFactory(
            CharactersListFragmentArgs.fromBundle(getArguments()).getViewArgs()
        );
        CharactersListViewModel viewModel = ViewModelProviders.of(this, factory).get(CharactersListViewModel.class);

        viewModel.getCharacters().observe(this, characters ->
            Optional.ofNullable(rvCharacters.getAdapter())
                .map(adapter -> (CharactersListAdapter) adapter)
                .ifPresent(adapter -> adapter.submitList(characters))
        );

        viewModel.getNetworkState().observe(this, networkState ->
            Optional.ofNullable(rvCharacters.getAdapter())
                .map(adapter -> (CharactersListAdapter) adapter)
                .ifPresent(adapter -> adapter.setNetworkState(networkState))
        );

        return rvCharacters;
    }

    public class CharactersListAdapter extends ListAdapter<Character, RecyclerView.ViewHolder> {

        private static final int TYPE_PROGRESS = 0;
        private static final int TYPE_ITEM = 1;

        private Context context;
        private NetworkState networkState;

        /*
         * The DiffUtil is defined in the constructor
         */
        CharactersListAdapter(Context context) {
            super(Character.DIFF_CALLBACK);
            this.context = context;
        }


        /*
         * Default method of RecyclerView.Adapter
         */
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_PROGRESS) {
                return new NetworkStateItemViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_progress_row, parent, false)
                );

            } else {
                return new CharacterItemViewHolder(
                    ItemCharactersRowBinding.inflate(LayoutInflater.from(context), parent, false)
                );
            }
        }


        /*
         * Default method of RecyclerView.Adapter
         */
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof CharacterItemViewHolder) {
                ((CharacterItemViewHolder) holder).bindTo(getItem(position));
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

        class CharacterItemViewHolder extends RecyclerView.ViewHolder {

            private ItemCharactersRowBinding binding;

            CharacterItemViewHolder(ItemCharactersRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            void bindTo(Character character) {
                binding.setData(character);
                AssetManager.getInstance().loadImage(character.getImage(), binding.ivCharacter);
                binding.getRoot().setOnClickListener(v ->
                    findNavController().ifPresent(navController ->
                        navController.navigate(
                            CharactersListFragmentDirections.actionCharactersListFragmentToCharacterDetailFragment(character)
                        )
                    )
                );
            }
        }
    }
}
