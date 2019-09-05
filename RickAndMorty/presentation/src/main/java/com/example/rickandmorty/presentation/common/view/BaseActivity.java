package com.example.rickandmorty.presentation.common.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.rickandmorty.presentation.common.presenter.Presenter;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSafePresenter().onViewVisible();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getSafePresenter().onViewHidden();
    }

    protected abstract Presenter getPresenter();

    private Presenter getSafePresenter() {
        final Presenter presenter = getPresenter();
        if (presenter == null) {
            throw new IllegalStateException("A presenter has not been provided for this Activity");
        }
        return presenter;
    }
}
