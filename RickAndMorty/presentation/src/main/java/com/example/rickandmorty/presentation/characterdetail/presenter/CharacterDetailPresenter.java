package com.example.rickandmorty.presentation.characterdetail.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.rickandmorty.presentation.characterdetail.view.CharacterView;
import com.example.rickandmorty.presentation.common.presenter.BasePresenter;
import com.example.rickandmortydata.repository.RickAndMortyRepository;
import com.example.rickandmortydata.repository.RickAndMortyRepositoryImpl;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CharacterDetailPresenter extends BasePresenter {

    private final Context mContext;
    private final RickAndMortyRepository mRickAndMortyRepository;
    private final CharacterView mCharacterView;

    public CharacterDetailPresenter(Context context, CharacterView characterView, String imageUrl) {
        mContext = context;
        mCharacterView = characterView;
        mRickAndMortyRepository = new RickAndMortyRepositoryImpl(mContext);
        mRickAndMortyRepository.getCharacterImageBytes(imageUrl).subscribe(new CharacterImageObserver());
    }

    @Override
    public void onViewHidden() {
        super.onViewHidden();
    }

    @Override
    public void onViewVisible() {
        super.onViewVisible();
    }

    public class CharacterImageObserver implements Observer<byte[]>{

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(byte[] imageBytes) {
            final Bitmap imageBitmap =
                    BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            mCharacterView.showCharacterImage(imageBitmap);
        }

        @Override
        public void onError(Throwable e) {
            mCharacterView.showError(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    }
}
