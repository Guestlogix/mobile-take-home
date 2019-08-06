package com.rickandmorty.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.rickandmorty.R;
import com.rickandmorty.adapter.CharactersAdapter;
import com.rickandmorty.databinding.FragmentEpisodeCharactersBinding;
import com.rickandmorty.interfaces.RecyclerItemOnClick;
import com.rickandmorty.model.EpisodeModel;
import com.rickandmorty.model.character.CharactersModel;
import com.rickandmorty.utils.ActivityHelper;
import com.rickandmorty.utils.DialogUtil;
import com.rickandmorty.viewmodel.EpisodeCharactersViewContract;
import com.rickandmorty.viewmodel.EpisodeCharactersViewModel;
import com.rickandmorty.viewmodel.LifeCycle;
import com.rickandmorty.widget.SpacesItemDecoration;
import java.util.List;

public class EpisodeCharactersFragment extends BaseFragment implements EpisodeCharactersViewContract.View {

  private FragmentEpisodeCharactersBinding binding;
  private EpisodeCharactersViewModel viewModel;

  public EpisodeCharactersFragment() {
    viewModel = new EpisodeCharactersViewModel();
  }

  private RecyclerItemOnClick itemOnClick = new RecyclerItemOnClick() {
    @Override
    public void onClickItem(View view, RecyclerView.ViewHolder viewHolder, String tagAction, Object data, int position) {
      if (data instanceof CharactersModel) {
        CharactersModel charactersModel = (CharactersModel) data;
        ActivityHelper.openCharacterDetails(getActivity(), charactersModel.getId());
      }
    }
  };

  @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_episode_characters, container, false);
    binding = DataBindingUtil.bind(root);
    String characterIds = getActivity().getIntent().getStringExtra(ActivityHelper.KEY_CHARACTER_IDS);
    bindViews();
    viewModel.setCharacterIds(characterIds);

    binding.icBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onBackButtonPressed();
      }
    });

    return root;
  }

  @Override protected LifeCycle.ViewModel getViewModel() {
    return viewModel;
  }

  @Override public void onBackButtonPressed() {
    getActivity().onBackPressed();
  }

  @Override public void bindViews() {
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
    binding.rcEpisodesCharacters.setHasFixedSize(true);
    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
    binding.rcEpisodesCharacters.setLayoutManager(gridLayoutManager);
    binding.rcEpisodesCharacters.setNestedScrollingEnabled(false);
    binding.rcEpisodesCharacters.setItemAnimator(new DefaultItemAnimator());
    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
    binding.rcEpisodesCharacters.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
  }

  @Override public void bindCharactersData(List<CharactersModel> charactersResponseList) {
    CharactersAdapter charactersAdapter = new CharactersAdapter();
    charactersAdapter.setCharactersList(charactersResponseList);
    charactersAdapter.setItemOnClick(itemOnClick);
    binding.rcEpisodesCharacters.setAdapter(charactersAdapter);
  }

  @Override public void navigateToDetail() {

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
