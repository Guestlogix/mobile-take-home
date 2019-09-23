package com.guestlogix.takehome.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.guestlogix.takehome.data.Character;
import com.guestlogix.takehome.data.source.CharactersDataSource;
import com.guestlogix.takehome.data.source.CharactersRepository;
import com.guestlogix.takehome.utils.DateUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CharactersListViewModel extends AndroidViewModel {

    public MutableLiveData<List<Character>> characters = new MutableLiveData<>(Collections.emptyList());
    public LiveData<Boolean> isLoading;

    private CharactersRepository mRepository;

    CharactersListViewModel(Application context, CharactersRepository repository) {
        super(context);
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

                characters.postValue(response);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
