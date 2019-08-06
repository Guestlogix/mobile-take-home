package com.rickandmorty.repository;

import android.content.Context;
import androidx.annotation.NonNull;
import com.rickandmorty.api.CallBackApi;
import com.rickandmorty.api.CallerAPI;
import com.rickandmorty.api.CallerApiRetrofit;
import com.rickandmorty.model.EpisodesResponse;
import com.rickandmorty.model.character.CharactersModel;
import com.rickandmorty.utils.ActivityHelper;
import com.rickandmorty.utils.Constants;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseRepository {

  private CallerAPI mCallerApi;
  private Context context;

  public BaseRepository(Context context) {
    this.context = context;
    //If real server
    mCallerApi = new CallerApiRetrofit(context);

  }

  public CallerAPI getCallerApi() {
    return mCallerApi;
  }

  public void getEpisodeCharactersData(String characterIds, final CallBackApi<List<CharactersModel>, Throwable> callBack) throws IOException {
    Call<List<CharactersModel>> call = getCallerApi().getEpisodeCharacters(characterIds);
    call.enqueue(new Callback<List<CharactersModel>>() {
      @Override
      public void onResponse(@NonNull Call<List<CharactersModel>> call, @NonNull Response<List<CharactersModel>> response) {
        if (response.isSuccessful()) {
          callBack.onSuccess(response.body());
        } else {
          callBack.onProcessError(response.code(), response.message(), response.errorBody());
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<CharactersModel>> call, @NonNull Throwable t) {
        callBack.onFailure(t);
      }
    });
  }

  public void getCharacterDetails(int characterId, final CallBackApi<CharactersModel, Throwable> callBack) throws IOException {
    Call<CharactersModel> call = getCallerApi().getCharacterDetails(characterId);
    call.enqueue(new Callback<CharactersModel>() {
      @Override
      public void onResponse(@NonNull Call<CharactersModel> call, @NonNull Response<CharactersModel> response) {
        if (response.isSuccessful()) {
          callBack.onSuccess(response.body());
        } else {
          callBack.onProcessError(response.code(), response.message(), response.errorBody());
        }
      }

      @Override
      public void onFailure(@NonNull Call<CharactersModel> call, @NonNull Throwable t) {
        callBack.onFailure(t);
      }
    });
  }

  public Response<EpisodesResponse> getAllEpisodesData(String page) throws IOException {
      Call<EpisodesResponse> call = getCallerApi().getAllEpisodes(page);
      Response<EpisodesResponse> response = call.execute();
      if (response.isSuccessful()) {
        return response;
      } else if (response.code() == Constants.EXPIRED_CODE) {
          Call<EpisodesResponse> secondCall = getCallerApi().getAllEpisodes(page);
          Response<EpisodesResponse> secondResponse = secondCall.execute();
          if (secondResponse.isSuccessful()) {
            return secondResponse;
          } else {
            if (secondResponse.code() == Constants.EXPIRED_CODE) {
              ActivityHelper.restartLogin(context);
              return null;
            } else {
              return secondResponse;
            }
          }
      } else {
        return response;
      }
  }
}
