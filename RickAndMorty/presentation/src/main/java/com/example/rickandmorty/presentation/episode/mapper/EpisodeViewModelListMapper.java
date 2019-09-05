package com.example.rickandmorty.presentation.episode.mapper;

import com.example.rickandmorty.presentation.episode.model.Episode;
import com.example.rickandmorty.presentation.episode.model.EpisodeList;
import com.example.rickandmorty.presentation.episode.model.EpisodePaginationInfo;
import com.example.rickandmortydata.entity.EpisodeData;
import com.example.rickandmortydata.entity.EpisodeListData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Converts a list of episode data from data to list of episode view model.
 */
public class EpisodeViewModelListMapper implements Function<EpisodeListData, EpisodeList> {

    private final EpisodeViewModelMapper mEpisodeViewModelMapper;
    private final EpisodeViewModelPaginationInfoMapper mEpisodeViewModelPaginationInfoMapper;

    public EpisodeViewModelListMapper() {
        mEpisodeViewModelMapper = new EpisodeViewModelMapper();
        mEpisodeViewModelPaginationInfoMapper = new EpisodeViewModelPaginationInfoMapper();
    }

    @Override
    public EpisodeList apply(EpisodeListData episodeDataList) {
        if (episodeDataList == null) {
            throw new IllegalStateException();
        }

        final EpisodePaginationInfo episodePaginationInfo =
                mEpisodeViewModelPaginationInfoMapper.apply(episodeDataList.getInfo());

        final List<Episode> episodeList = new ArrayList<>();
        for (EpisodeData episodeData : episodeDataList.getResults()) {
            final Episode episode = mEpisodeViewModelMapper.apply(episodeData);
            episodeList.add(episode);
        }
        return new EpisodeList(episodePaginationInfo, episodeList);
    }
}
