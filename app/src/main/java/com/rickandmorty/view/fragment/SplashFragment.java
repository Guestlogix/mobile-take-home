package com.rickandmorty.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.rickandmorty.R;
import com.rickandmorty.utils.ActivityHelper;
import com.rickandmorty.viewmodel.LifeCycle;
import com.rickandmorty.viewmodel.SplashViewContract;
import com.rickandmorty.viewmodel.SplashViewModel;

public class SplashFragment extends BaseFragment implements SplashViewContract.View {

  private final SplashViewModel splashViewModel;
  public SplashFragment() {
    splashViewModel = new SplashViewModel();
  }

  @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_splash, container, false);
    DataBindingUtil.bind(root);
    return root;
  }

  @Override protected LifeCycle.ViewModel getViewModel() {
    return splashViewModel;
  }

  @Override public void onBackButtonPressed() {
    getActivity().onBackPressed();
  }

  @Override public void navigateToAllEpisodes() {
    ActivityHelper.openAllEpisodes(getActivity());
  }
}
