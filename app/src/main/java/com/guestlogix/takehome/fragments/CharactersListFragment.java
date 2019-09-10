package com.guestlogix.takehome.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.Utils.Optional;
import com.guestlogix.takehome.viewmodels.CharactersListViewModel;
import com.guestlogix.takehome.viewmodels.EpisodesListingViewModel;

public class CharactersListFragment extends Fragment {

    private CharactersListViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RecyclerView rvCharacters = (RecyclerView) inflater.inflate(R.layout.fragment_characters_list, container, false);

        Optional.ofNullable(getContext()).ifPresent(context -> {
            rvCharacters.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCharacters.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//            rvCharacters.setAdapter(new EpisodesListAdapter(context));
        });

        CharactersListViewModel viewModel = ViewModelProviders.of(this).get(CharactersListViewModel.class);
//        viewModel.getCharacters().observe(this, episodes ->
//                Optional.ofNullable(rvCharacters.getAdapter())
//                        .map(adapter -> (EpisodesListingFragment.EpisodesListAdapter) adapter)
//                        .ifPresent(adapter -> adapter.submitList(episodes))
//        );

//        viewModel.getNetworkState().observe(this, networkState ->
//                Optional.ofNullable(rvCharacters.getAdapter())
//                        .map(adapter -> (EpisodesListingFragment.EpisodesListAdapter) adapter)
//                        .ifPresent(adapter -> adapter.setNetworkState(networkState))
//        );

        return rvCharacters;
    }
}
