package com.guestlogix.takehome.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.binding.DataBindingComponentImpl;
import com.guestlogix.takehome.databinding.FragmentCharactersDetailBinding;
import com.guestlogix.takehome.utils.Optional;
import com.guestlogix.takehome.viewmodels.CharacterDetailViewModel;
import com.guestlogix.takehome.viewmodels.ViewModelFactory;

public class CharacterDetailFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentCharactersDetailBinding binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_characters_detail,
            container,
            false,
            new DataBindingComponentImpl(getViewLifecycleOwner())
        );

        ViewModelFactory factory = ViewModelFactory.getInstance(requireActivity().getApplication());
        CharacterDetailViewModel viewModel = ViewModelProviders.of(requireActivity(), factory).get(CharacterDetailViewModel.class);
        viewModel.loadCharacters(CharacterDetailFragmentArgs.fromBundle(getArguments()).getViewArgs());

        binding.setVm(viewModel);

        viewModel.getCharacter().observe(this, character -> {
            Optional.ofNullable(((AppCompatActivity) requireActivity()).getSupportActionBar()).
                ifPresent(toolbar -> toolbar.setTitle(character.getName()));
            binding.setCharacter(character);
        });

        return binding.getRoot();
    }
}
