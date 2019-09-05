package com.example.rickandmorty.presentation.episode.mapper;

import com.example.rickandmorty.presentation.episode.model.EpisodePaginationInfo;
import com.example.rickandmortydata.entity.Info;

import java.util.function.Function;

public class EpisodeViewModelPaginationInfoMapper implements Function<Info, EpisodePaginationInfo> {
    @Override
    public EpisodePaginationInfo apply(Info info) {
        return new EpisodePaginationInfo(info.getCount(), info.getPages(), info.getNext(), info.getPrev());
    }
}
