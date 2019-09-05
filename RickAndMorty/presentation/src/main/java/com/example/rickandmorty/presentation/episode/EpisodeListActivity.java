package com.example.rickandmorty.presentation.episode;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rickandmorty.R;
import com.example.rickandmorty.presentation.common.adapter.LayoutManager;
import com.example.rickandmorty.presentation.common.adapter.PaginationScrollListener;
import com.example.rickandmorty.presentation.common.controller.DefaultNavigator;
import com.example.rickandmorty.presentation.common.controller.Navigator;
import com.example.rickandmorty.presentation.common.presenter.Presenter;
import com.example.rickandmorty.presentation.common.view.BaseActivity;
import com.example.rickandmorty.presentation.episode.adapter.EpisodesListAdapter;
import com.example.rickandmorty.presentation.episode.callback.EpisodeClickListener;
import com.example.rickandmorty.presentation.episode.model.Episode;
import com.example.rickandmorty.presentation.episode.presenter.EpisodeListPresenter;
import com.example.rickandmorty.presentation.episode.view.EpisodeListView;

import java.util.List;

public class EpisodeListActivity extends BaseActivity implements EpisodeListView, EpisodeClickListener {

    private ProgressBar mProgressBar;
    private RecyclerView mEpisodesListView;
    private EpisodeListPresenter mEpisodeListPresenter;
    private EpisodesListAdapter mEpisodeListAdapter;
    private LayoutManager mEpisodeLayoutManager;
    private DividerItemDecoration mDividerItemDecoration;
    private PaginationScrollListener mPaginationScrollListener;
    private Navigator mNavigator;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progressbar);
        mEpisodesListView = findViewById(R.id.episode_list);
        mEpisodeListPresenter = new EpisodeListPresenter(this, getApplicationContext());
        mEpisodeListAdapter = new EpisodesListAdapter(this);
        mEpisodeLayoutManager = new LayoutManager(this);
        mDividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mPaginationScrollListener = new EpisodePaginationScrollListenerImpl(mEpisodeLayoutManager);
        mNavigator = new DefaultNavigator(this);

        mEpisodeListAdapter.setEpisodeClickListener(this);
        mEpisodesListView.setAdapter(mEpisodeListAdapter);
        mEpisodesListView.setLayoutManager(mEpisodeLayoutManager);
        mEpisodesListView.addItemDecoration(mDividerItemDecoration);
        mEpisodesListView.addOnScrollListener(mPaginationScrollListener);

    }

    @Override
    public void showEpisodesList(final List<Episode> episodeList) {
        mEpisodeListAdapter.setEpisodeListViewModel(episodeList);
    }

    @Override
    public void showEpisodeDetails(final String characterIds) {
        mNavigator.navigateToEpisodeDetail(characterIds);
    }

    @Override
    public void showError(final String message) {
        final Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected EpisodeListPresenter getPresenter() {
        return mEpisodeListPresenter;
    }

    @Override
    public void showLoadingView() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(final View view,
                        final int position,
                        final List<String> characterListUrl) {
        getPresenter().onEpisodeSelected(characterListUrl);
    }

    public class EpisodePaginationScrollListenerImpl extends PaginationScrollListener {

        public EpisodePaginationScrollListenerImpl(final LayoutManager mLayoutManager) {
            super(mLayoutManager);
        }

        @Override
        public boolean isLoading() {
            return false;
        }

        @Override
        public boolean isLastPage() {
            return false;
        }

        @Override
        public void loadNextPage() {
            getPresenter().loadNext();
        }
    }
}
