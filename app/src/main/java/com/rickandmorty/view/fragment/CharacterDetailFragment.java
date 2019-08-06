package com.rickandmorty.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.rickandmorty.R;
import com.rickandmorty.databinding.FragmentCharacterDetailBinding;
import com.rickandmorty.model.character.CharactersModel;
import com.rickandmorty.utils.ActivityHelper;
import com.rickandmorty.utils.DialogUtil;
import com.rickandmorty.viewmodel.CharacterDetailContract;
import com.rickandmorty.viewmodel.CharacterDetailViewModel;
import com.rickandmorty.viewmodel.LifeCycle;

public class CharacterDetailFragment extends BaseFragment implements CharacterDetailContract.View{

  private CharacterDetailViewModel viewModel;
  private FragmentCharacterDetailBinding binding;

  public CharacterDetailFragment() {
    viewModel = new CharacterDetailViewModel();
  }

  @Override protected LifeCycle.ViewModel getViewModel() {
    return viewModel;
  }

  @Override public void onBackButtonPressed() {
    getActivity().onBackPressed();
  }

  @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_character_detail, container, false);
    binding = DataBindingUtil.bind(root);
    int characterId = getActivity().getIntent().getIntExtra(ActivityHelper.KEY_SINGLE_CHARACTER_ID, 0);
    bindViews();
    viewModel.setCharacterId(characterId);

    binding.icCharacterDetailBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onBackButtonPressed();
      }
    });

    return root;
  }

  @Override public void bindViews() {
  }

  @Override public void bindCharacterDetails(CharactersModel charactersModel) {
    binding.ivCharacter.setImageURI(charactersModel.getImage());
    binding.tvCharacterName.setText(charactersModel.getName());
    binding.tvCharacterDetails.setText(charactersModel.getCharacterDetails());
    binding.tvCharacterCreated.setText(charactersModel.getCreated());
  }

  @Override public void showErrorDialog() {
    DialogUtil.showErrorDialog(getContext(), getString(R.string.general_error_text), getString(R.string.ok),
        new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            onBackButtonPressed();
          }
        });
  }
}
