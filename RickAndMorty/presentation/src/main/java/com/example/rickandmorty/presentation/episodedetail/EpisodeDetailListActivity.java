package com.example.rickandmorty.presentation.episodedetail;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.rickandmorty.R;
import com.example.rickandmorty.presentation.common.adapter.LayoutManager;
import com.example.rickandmorty.presentation.common.controller.DefaultNavigator;
import com.example.rickandmorty.presentation.common.controller.Navigator;
import com.example.rickandmorty.presentation.common.presenter.Presenter;
import com.example.rickandmorty.presentation.common.view.BaseActivity;
import com.example.rickandmorty.presentation.episodedetail.adapter.EpisodeDetailListAdapter;
import com.example.rickandmorty.presentation.episodedetail.callback.EpisodeDetailClickListener;
import com.example.rickandmorty.presentation.episodedetail.model.Character;
import com.example.rickandmorty.presentation.episodedetail.presenter.EpisodeDetailPresenter;
import com.example.rickandmorty.presentation.episodedetail.view.EpisodeDetailListView;

import java.util.List;

public class EpisodeDetailListActivity extends BaseActivity implements EpisodeDetailListView, EpisodeDetailClickListener {

    public static final String CHARACTER_IDS = "character_ids";
    private RecyclerView mCharacterListView;
    private EpisodeDetailPresenter mEpisodeDetailPresenter;
    private EpisodeDetailListAdapter mEpisodeDetailListAdapter;
    private LayoutManager mEpisodeDetailLayoutManager;
    private DividerItemDecoration mDividerItemDecoration;
    private String mCharacterIds;
    private Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail_list);
        mCharacterIds = getIntent().getStringExtra(CHARACTER_IDS);

        mCharacterListView = findViewById(R.id.character_list);
        mNavigator = new DefaultNavigator(this);
        mEpisodeDetailPresenter =
                new EpisodeDetailPresenter(
                        getApplicationContext(),
                        this,
                        mCharacterIds);
        mEpisodeDetailListAdapter = new EpisodeDetailListAdapter(getApplicationContext());
        mEpisodeDetailListAdapter.setOnCharacterSelectedClickListener(this);
        mEpisodeDetailLayoutManager = new LayoutManager(this);
        mDividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mCharacterListView.setAdapter(mEpisodeDetailListAdapter);
        mCharacterListView.setLayoutManager(mEpisodeDetailLayoutManager);
        mCharacterListView.addItemDecoration(mDividerItemDecoration);
    }

    @Override
    protected EpisodeDetailPresenter getPresenter() {
        return mEpisodeDetailPresenter;
    }

    @Override
    public void showLoadingView() {
        //Show the loading view
    }

    @Override
    public void showCharactersList(final List<Character> charactersList) {
        mEpisodeDetailListAdapter.showCharactersListViewModel(charactersList);
    }

    @Override
    public void showCharacterDetail(final Character character) {
        mNavigator.navigateToCharacterDetail(character);
    }

    @Override
    public void showError(final String message) {
        final Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onClick(final View view,
                        final int position,
                        final Character character) {
        getPresenter().onCharacterSelected(character);
    }
}
