package com.example.rickandmorty.presentation.episodedetail.presenter;

import android.content.Context;

import com.example.rickandmorty.presentation.common.presenter.BasePresenter;
import com.example.rickandmorty.presentation.episodedetail.callback.CharacterSelectedCallback;
import com.example.rickandmorty.presentation.episodedetail.mapper.CharacterViewModelMapper;
import com.example.rickandmorty.presentation.episodedetail.model.Character;
import com.example.rickandmorty.presentation.episodedetail.view.EpisodeDetailListView;
import com.example.rickandmortydata.entity.CharacterData;
import com.example.rickandmortydata.repository.RickAndMortyRepository;
import com.example.rickandmortydata.repository.RickAndMortyRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EpisodeDetailPresenter extends BasePresenter implements CharacterSelectedCallback {

    private Context mContext;
    private EpisodeDetailListView mEpisodeDetailListView;
    private String mCharacterIds;
    private RickAndMortyRepository mRickAndMortyRepository;
    private List<Character> characterList;
    private CharacterViewModelMapper mCharacterViewModelMapper;

    public EpisodeDetailPresenter(final Context context,
                                  final EpisodeDetailListView episodeDetailListView,
                                  final String characterIds) {
        mContext = context;
        mEpisodeDetailListView = episodeDetailListView;
        mCharacterIds = characterIds;
        characterList = new ArrayList<>();
        mCharacterViewModelMapper = new CharacterViewModelMapper();
        mRickAndMortyRepository = new RickAndMortyRepositoryImpl(mContext);
        mRickAndMortyRepository.getCharactersWithId(mCharacterIds)
                .subscribe(new CharacterListObserver());
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
    public void onCharacterSelected(Character character) {
        mEpisodeDetailListView.showCharacterDetail(character);
    }

    public class CharacterListObserver implements Observer<List<CharacterData>> {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<CharacterData> characterData) {
            characterList = mCharacterViewModelMapper.apply(characterData);
            mEpisodeDetailListView.showCharactersList(characterList);
        }

        @Override
        public void onError(Throwable e) {
            mEpisodeDetailListView.showError(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    }
}
