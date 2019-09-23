package com.guestlogix.takehome.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.binding.DataBindingComponentImpl;
import com.guestlogix.takehome.data.Character;
import com.guestlogix.takehome.databinding.FragmentCharactersListBinding;
import com.guestlogix.takehome.databinding.ItemCharactersRowBinding;
import com.guestlogix.takehome.network.imageloader.AssetManager;
import com.guestlogix.takehome.viewmodels.CharactersListViewModel;
import com.guestlogix.takehome.viewmodels.ViewModelFactory;
import com.guestlogix.takehome.views.NetworkStateItemViewHolder;

public class CharactersListFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentCharactersListBinding binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_characters_list,
            container,
            false,
            new DataBindingComponentImpl(getViewLifecycleOwner())
        );

        binding.rvCharacters.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCharacters.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        binding.rvCharacters.setAdapter(new CharactersListAdapter(requireContext()));

        ViewModelFactory factory = ViewModelFactory.getInstance(requireActivity().getApplication());
        CharactersListViewModel charactersViewModel = ViewModelProviders.of(requireActivity(), factory).get(CharactersListViewModel.class);
        binding.setViewmodel(charactersViewModel);

        charactersViewModel.loadCharacters(CharactersListFragmentArgs.fromBundle(getArguments()).getViewArgs());

        return binding.getRoot();
    }

    public class CharactersListAdapter extends ListAdapter<Character, RecyclerView.ViewHolder> {

        private static final int TYPE_PROGRESS = 0;
        private static final int TYPE_ITEM = 1;

        private Context context;
        private boolean isLoading;

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
                ((NetworkStateItemViewHolder) holder).bindView(false);
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
