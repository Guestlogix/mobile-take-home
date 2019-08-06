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
import com.rickandmorty.adapter.EpisodeItemsAdapter;
import com.rickandmorty.databinding.FragmentAllEpisodesBinding;
import com.rickandmorty.interfaces.RecyclerItemOnClick;
import com.rickandmorty.model.EpisodeModel;
import com.rickandmorty.utils.ActivityHelper;
import com.rickandmorty.utils.Constants;
import com.rickandmorty.utils.DialogUtil;
import com.rickandmorty.utils.EndlessRecyclerViewScrollListener;
import com.rickandmorty.utils.Util;
import com.rickandmorty.viewmodel.AllEpisodesContract;
import com.rickandmorty.viewmodel.AllEpisodesViewModel;
import com.rickandmorty.viewmodel.LifeCycle;
import com.rickandmorty.widget.SpacesItemDecoration;
import java.util.List;
import java.util.Objects;

public class AllEpisodesFragment extends BaseFragment implements AllEpisodesContract.View {

  private AllEpisodesViewModel allEpisodesViewModel;
  private FragmentAllEpisodesBinding allEpisodesBinding;
  private EndlessRecyclerViewScrollListener scrollListenerBrandStore;
  private EpisodeItemsAdapter episodeItemsAdapter;

  public AllEpisodesFragment() {
    allEpisodesViewModel = new AllEpisodesViewModel();
  }

  private RecyclerItemOnClick recyclerItemOnClick = new RecyclerItemOnClick() {
    @Override
    public void onClickItem(View view, RecyclerView.ViewHolder viewHolder, String tagAction, Object data, int position) {
      if (data instanceof EpisodeModel) {
        EpisodeModel episodeModel = (EpisodeModel) data;
        allEpisodesViewModel.getCharactersFromEpisode(episodeModel, position);
      }
    }
  };

  @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_all_episodes, container, false);
    allEpisodesBinding = DataBindingUtil.bind(root);
    bindViews();
    return root;
  }

  @Override protected LifeCycle.ViewModel getViewModel() {
    return allEpisodesViewModel;
  }

  @Override public void onBackButtonPressed() {
    Objects.requireNonNull(getActivity()).onBackPressed();
  }

  @Override public void bindViews() {
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
    allEpisodesBinding.rcAllEpisodes.setHasFixedSize(true);
    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
    allEpisodesBinding.rcAllEpisodes.setLayoutManager(gridLayoutManager);
    allEpisodesBinding.rcAllEpisodes.setNestedScrollingEnabled(false);
    allEpisodesBinding.rcAllEpisodes.setItemAnimator(new DefaultItemAnimator());
    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
    allEpisodesBinding.rcAllEpisodes.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    scrollListenerBrandStore = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
      @Override
      public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        if (allEpisodesViewModel.getEpisodes() != null && !allEpisodesViewModel.isFinishLoadMore() && episodeItemsAdapter != null) {
          episodeItemsAdapter.setStillLoadingMore(true);
          allEpisodesViewModel.loadAllEpisodes(Constants.LOADING_MORE);
        } else {
          if (episodeItemsAdapter != null) episodeItemsAdapter.setStillLoadingMore(false);
        }
      }
    };

    allEpisodesBinding.rcAllEpisodes.addOnScrollListener(scrollListenerBrandStore);
  }

  @Override public void bindEpisodesData(List<EpisodeModel> episodesList) {
    episodeItemsAdapter = new EpisodeItemsAdapter();
    episodeItemsAdapter.setEpisodesList(episodesList).setItemOnClick(recyclerItemOnClick);
    allEpisodesBinding.rcAllEpisodes.setAdapter(episodeItemsAdapter);
  }

  @Override public void navigateToEpisode(String episodeIds) {
    ActivityHelper.openCharacters(getActivity(), episodeIds);
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

  @Override public void hideLoadingMore() {
    if (episodeItemsAdapter != null) {
      episodeItemsAdapter.setStillLoadingMore(false);
      episodeItemsAdapter.notifyDataSetChanged();
    }
  }
}
