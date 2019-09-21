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
import com.guestlogix.takehome.Utils.Optional;
import com.guestlogix.takehome.binding.DataBindingComponentImpl;
import com.guestlogix.takehome.databinding.FragmentCharactersDetailBinding;
import com.guestlogix.takehome.network.imageloader.AssetManager;
import com.guestlogix.takehome.viewmodels.CharacterDetailViewModel;
import com.guestlogix.takehome.viewmodels.factory.CharacterDetailViewModelFactory;

public class CharacterDetailFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentCharactersDetailBinding binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_characters_detail,
            container,
            false,
            new DataBindingComponentImpl()
        );

        CharacterDetailViewModelFactory factory = new CharacterDetailViewModelFactory(
            CharacterDetailFragmentArgs.fromBundle(getArguments()).getViewArgs()
        );
        CharacterDetailViewModel viewModel = ViewModelProviders.of(this, factory).get(CharacterDetailViewModel.class);

        viewModel.getCharacter().observe(this, character -> {
            Optional.ofNullable(((AppCompatActivity) requireActivity()).getSupportActionBar()).
                ifPresent(toolbar -> toolbar.setTitle(character.getName()));

            AssetManager.getInstance().loadImage(character.getImage(), binding.ivCharacter);
            binding.setCharacter(character);
        });



        return binding.getRoot();
    }
}
