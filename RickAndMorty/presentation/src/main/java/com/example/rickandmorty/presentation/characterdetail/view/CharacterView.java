package com.example.rickandmorty.presentation.characterdetail.view;

import android.graphics.Bitmap;

public interface CharacterView {

    /**
     * Dispalys the character image to the user
     * @param imageBitmap
     */
    void showCharacterImage(Bitmap imageBitmap);

    /**
     * Displays a toast to the user incase of error
     * @param message
     */
    void showError(String message);
}
