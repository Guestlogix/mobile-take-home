package com.example.rickandmortydata.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.rickandmortydata.entity.CharacterData;
import com.example.rickandmortydata.entity.EpisodeData;
import com.example.rickandmortydata.entity.EpisodeListData;
import com.example.rickandmortydata.exception.NetworkConnectionException;
import com.example.rickandmortydata.mapper.CharacterJsonMapper;
import com.example.rickandmortydata.mapper.EpisodeJsonMapper;

import java.net.MalformedURLException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class RestApiImpl implements RestApi {

    private final Context mContext;
    private final EpisodeJsonMapper mEpisodeJsonMapper;
    private final CharacterJsonMapper mCharacterJsonMapper;

    public RestApiImpl(final Context context) {
        mContext = context;
        mEpisodeJsonMapper = new EpisodeJsonMapper();
        mCharacterJsonMapper = new CharacterJsonMapper();
    }

    @Override
    public Observable<EpisodeListData> episodesDataList(String url) {
        return Observable.create(emitter -> {
            if (isConnectedToNet()) {
                final String episodeListJson;
                if (url == null || url.isEmpty()) {
                    episodeListJson = getEpisodesfromApi();
                } else {
                    episodeListJson = getEpisodePagefromApi(url);
                }
                EpisodeListData listOfEpisodeData =
                        mEpisodeJsonMapper.transformEpisodeList(episodeListJson);
                emitter.onNext(listOfEpisodeData);
                emitter.onComplete();
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    @Override
    public Observable<List<CharacterData>> characterDataList(String characterIds) {
        return Observable.create(emitter -> {
           if (isConnectedToNet()) {
               final String characterListJson = getCharatersFromApi(characterIds);
               final List<CharacterData> listOfCharacters =
                       mCharacterJsonMapper.transformCharacterList(characterListJson);
               if (listOfCharacters != null && !listOfCharacters.isEmpty()) {
                   emitter.onNext(listOfCharacters);
                   emitter.onComplete();
               }
           } else {
               emitter.onError(new NetworkConnectionException());
           }
        });
    }

    @Override
    public Observable<byte[]> characterImageData(String imageUrl) {
        return Observable.create(emitter -> {
           if (isConnectedToNet()) {
               final byte[] imageByteArray = getCharacterImageFromApi(imageUrl);
               if (imageByteArray != null) {
                   emitter.onNext(imageByteArray);
                   emitter.onComplete();
               }
           } else {
               emitter.onError(new NetworkConnectionException());
           }
        });
    }

    private byte[] getCharacterImageFromApi(String imageUrl) throws MalformedURLException {
        final byte[] imageJson = new ApiConnection(imageUrl).requestImageResource();
        return imageJson;
    }

    private String getCharatersFromApi(String characterIds) throws MalformedURLException {
        final String characterJson =
                new ApiConnection(RestApi.CHARACTER_URL + characterIds).requestSyncCall();
        return characterJson;
    }

    private String getEpisodesfromApi() throws MalformedURLException {
        final String episodesJson = new ApiConnection(RestApi.EPISODE_URL).requestSyncCall();
        return episodesJson;
    }

    private String getEpisodePagefromApi(String url) throws MalformedURLException {
        final String episodesJson = new ApiConnection(url).requestSyncCall();
        return episodesJson;
    }

    private boolean isConnectedToNet() {
        final boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
