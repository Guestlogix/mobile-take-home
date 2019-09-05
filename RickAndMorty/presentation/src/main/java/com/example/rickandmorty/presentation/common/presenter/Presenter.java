package com.example.rickandmorty.presentation.common.presenter;

/**
 * Interface representing a Presenter.
 */
public interface Presenter {

    /**
     * Used to register to events and to start operations when view becomes visible
     */
    void onViewVisible();

    /**
     * Used to unregister events when view is hidden
     */
    void onViewHidden();

    void loadNext();
}
