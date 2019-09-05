package com.example.rickandmorty.presentation.episode.presenter;

import android.content.Context;

import com.example.rickandmorty.presentation.common.adapter.PaginationScrollListener;
import com.example.rickandmorty.presentation.common.presenter.BasePresenter;
import com.example.rickandmorty.presentation.episode.callback.EpisodeSelectedCallback;
import com.example.rickandmorty.presentation.episode.mapper.EpisodeViewModelListMapper;
import com.example.rickandmorty.presentation.episode.model.Episode;
import com.example.rickandmorty.presentation.episode.model.EpisodeList;
import com.example.rickandmorty.presentation.episode.model.EpisodePaginationInfo;
import com.example.rickandmorty.presentation.episode.view.EpisodeListView;
import com.example.rickandmortydata.entity.EpisodeListData;
import com.example.rickandmortydata.repository.RickAndMortyRepository;
import com.example.rickandmortydata.repository.RickAndMortyRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EpisodeListPresenter extends BasePresenter implements EpisodeSelectedCallback {

    private static final String COMMON_CHARACTER_URL = "https://rickandmortyapi.com/api/character/";
    private final RickAndMortyRepository rickAndMortyRepository;
    private EpisodeListView mEpisodeListView;
    private List<Episode> mEpisodesList;
    private EpisodeViewModelListMapper mEpisodeViewModelListMapper;
    private boolean mLoading;
    private EpisodePaginationInfo mEpisodePaginationInfo;

    public EpisodeListPresenter(final EpisodeListView episodeListView, final Context context) {
        mEpisodeListView = episodeListView;
        mEpisodesList = new ArrayList<>();
        rickAndMortyRepository =
                new RickAndMortyRepositoryImpl(context);
        mEpisodeViewModelListMapper = new EpisodeViewModelListMapper();
        rickAndMortyRepository.getAllEpisodes()
                .subscribe(new EpisodeListObserver());
        mLoading = true;
        mEpisodePaginationInfo = new EpisodePaginationInfo();
    }

    @Override
    public void onViewVisible() {
        super.onViewVisible();
    }

    @Override
    public void onViewHidden() {
        super.onViewHidden();
    }

    @Override
    public void loadNext() {
        if (mEpisodePaginationInfo.getNext() != null && !mEpisodePaginationInfo.getNext().isEmpty()) {
            rickAndMortyRepository.getEpisodeByPage(mEpisodePaginationInfo.getNext())
                    .subscribe(new EpisodeListObserver());
        }
    }

    @Override
    public void onEpisodeSelected(List<String> characterUrlList) {
        //This can be done differently where we pass in an EpisodeId and the episodeDetail
        //will query the episode and load each character through rx chaining.
        //I did it this way to simplify the implementation and reduce the number of network calls.
        if (characterUrlList != null && !characterUrlList.isEmpty()) {
            StringBuffer characterIdsBufferString = new StringBuffer();
            for (String characterUrl : characterUrlList) {
                final String characterId = characterUrl.replace(COMMON_CHARACTER_URL, "");
                characterIdsBufferString.append(characterId + ",");
            }
            final String characterIds =
                    characterIdsBufferString
                            .substring(0, characterIdsBufferString.lastIndexOf(","));
            mEpisodeListView.showEpisodeDetails(characterIds);
        }
    }

    public class EpisodeListObserver implements Observer<EpisodeListData> {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(EpisodeListData episodeDataList) {
            final EpisodeList episodeList = mEpisodeViewModelListMapper.apply(episodeDataList);
            mEpisodePaginationInfo = episodeList.getPaginationInfo();
            mEpisodesList = episodeList.getEpisodes();
            mEpisodeListView.showEpisodesList(mEpisodesList);
        }

        @Override
        public void onError(Throwable e) {
            mLoading = false;
            mEpisodeListView.showError(e.getMessage());
        }

        @Override
        public void onComplete() {
            mLoading = false;
        }
    }
}
