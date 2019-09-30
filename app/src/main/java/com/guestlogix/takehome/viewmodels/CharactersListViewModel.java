package com.guestlogix.takehome.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.data.Character;
import com.guestlogix.takehome.data.source.CharactersDataSource;
import com.guestlogix.takehome.data.source.CharactersRepository;
import com.guestlogix.takehome.models.CharacterRowStub;
import com.guestlogix.takehome.network.GuestlogixException;
import com.guestlogix.takehome.network.NetworkState;
import com.guestlogix.takehome.utils.DateUtils;
import com.guestlogix.takehome.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CharactersListViewModel extends AndroidViewModel {

    public SingleLiveEvent<List<CharacterRowStub>> characters = new SingleLiveEvent<>();
    public LiveData<NetworkState> isLoading;
    public SingleLiveEvent<String> error = new SingleLiveEvent<>();

    public final SingleLiveEvent<Character> navigateToCharacterDetail = new SingleLiveEvent<>();

    private CharactersRepository mRepository;
    private Context mContext;

    CharactersListViewModel(Application context, CharactersRepository repository) {
        super(context);
        mContext = context;
        isLoading = repository.isLoading;
        mRepository = repository;
    }

    public void loadCharacters(String characterIds) {
        mRepository.getCharacters(characterIds, new CharactersDataSource.LoadCharactersCallback() {
            @Override
            public void onCharactersLoaded(List<Character> response) {
                Collections.sort(response, (o1, o2) -> {
                    Date d1 = DateUtils.parseDate(o1.getCreated());
                    Date d2 = DateUtils.parseDate(o2.getCreated());

                    if(d2 == null)
                        return -1;
                    else if(d1 == null)
                        return 1;
                    else
                        return d1.compareTo(d2);
                });

                List<CharacterRowStub> stubs = new ArrayList<>();

                for (Character c : response) {
                    stubs.add(new CharacterRowStub(
                        c.getName(),
                        DateUtils.getFormattedDate(c.getCreated()),
                        c.getStatus(),
                        c.getImage(),
                        c
                    ));
                }

                characters.postValue(stubs);
            }

            @Override
            public void onDataNotAvailable(GuestlogixException e) {
                String message;
                switch (e.getCode()) {
                    case NETWORK:
                    case DB_ERROR:
                        message = mContext.getString(R.string.characters_network_error);
                        break;

                    default:
                        message = mContext.getString(R.string.characters_data_error);
                        break;
                }
                error.postValue(message);
            }
        });
    }


    public void onCharacterClicked(CharacterRowStub character) {
        navigateToCharacterDetail.postValue(character.getReference());
    }
}
