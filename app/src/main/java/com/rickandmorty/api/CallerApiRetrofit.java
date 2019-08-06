package com.rickandmorty.api;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rickandmorty.BuildConfig;
import com.rickandmorty.model.character.CharactersModel;
import com.rickandmorty.model.EpisodesResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class CallerApiRetrofit implements CallerAPI {

  Context mContext;
  ApiService service;
  public CallerApiRetrofit(Context context) {
    Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create();
    mContext = context;
    Retrofit retrofit = new Retrofit.Builder()
        .client(getUnsafeOkHttpClient())
        .baseUrl(BuildConfig.BASE_URI)
        .addConverterFactory(new NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
    service = retrofit.create(ApiService.class);
  }

  class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, java.lang.annotation.Annotation[] annotations, Retrofit retrofit) {
      final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
      return new Converter<ResponseBody, Object>() {
        @Override
        public Object convert(ResponseBody body) throws IOException {
          if (body.contentLength() == 0) return null;
          return delegate.convert(body);
        }
      };
    }
  }


  public static OkHttpClient getUnsafeOkHttpClient() {
    try {
      // Create a trust manager that does not validate certificate chains
      final TrustManager[] trustAllCerts = new TrustManager[]{
          new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws
                CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return new java.security.cert.X509Certificate[]{};
            }
          }
      };

      // Install the all-trusting trust manager
      final SSLContext sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

      // Create an ssl socket factory with our all-trusting manager
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);

      builder.hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
      builder.connectTimeout(60, TimeUnit.SECONDS);
      builder.readTimeout(60, TimeUnit.SECONDS);
      builder.writeTimeout(60, TimeUnit.SECONDS);
      OkHttpClient okHttpClient = builder.build();
      return okHttpClient;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override public Call<EpisodesResponse> getAllEpisodes(String page) {
    Call<EpisodesResponse> configurations = service.getConfigurations(page);
    return configurations;
  }

  @Override public Call<List<CharactersModel>> getEpisodeCharacters(String characterIds) {
    Call<List<CharactersModel>> episodeCharacters = service.getEpisodeCharacters(characterIds);
    return episodeCharacters;
  }

  @Override public Call<CharactersModel> getCharacterDetails(int characterId) {
    Call<CharactersModel> characterDetails = service.getCharacterDetails(characterId);
    return characterDetails;
  }

  public interface ApiService {

    @GET(BuildConfig.URI_ALL_EPISODES)
    Call<EpisodesResponse> getConfigurations(@Query("page") String page);

    @GET("character/{ids}")
    Call<List<CharactersModel>> getEpisodeCharacters(@Path("ids") String characterIds);

    @GET("character/{id}")
    Call<CharactersModel> getCharacterDetails(@Path("id") int characterId);
  }

}
