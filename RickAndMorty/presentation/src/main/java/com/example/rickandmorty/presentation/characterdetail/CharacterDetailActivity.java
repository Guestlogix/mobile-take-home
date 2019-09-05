package com.example.rickandmorty.presentation.characterdetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rickandmorty.R;
import com.example.rickandmorty.presentation.characterdetail.presenter.CharacterDetailPresenter;
import com.example.rickandmorty.presentation.characterdetail.view.CharacterView;
import com.example.rickandmorty.presentation.common.presenter.Presenter;
import com.example.rickandmorty.presentation.common.view.BaseActivity;

public class CharacterDetailActivity extends BaseActivity implements CharacterView {

    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String STATUS = "status";
    public static final String SPECIES = "species";
    public static final String ORIGIN = "origin";
    public static final String LOCATION = "location";
    public static final String IMAGE_URL = "image_url";

    private String mName, mStatus, mSpecies, mOrigin, mLocation, mImageUrl, mGender;
    private ImageView mCharacterImage;
    private TextView mNameTextView;
    private TextView mStatusTextView;
    private TextView mSpeciesTextView;
    private TextView mOriginTextView;
    private TextView mLocationTextView;
    private TextView mGenderTextView;
    private CharacterDetailPresenter mCharacterDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        getIntentExtras(getIntent());

        mCharacterImage = findViewById(R.id.character_image);
        mNameTextView = findViewById(R.id.character_name);
        mStatusTextView = findViewById(R.id.character_status);
        mSpeciesTextView = findViewById(R.id.character_species);
        mOriginTextView = findViewById(R.id.character_origin);
        mLocationTextView = findViewById(R.id.character_location);

        mNameTextView.setText(mName);
        mStatusTextView.setText(mStatus);
        mSpeciesTextView.setText(mSpecies);
        mOriginTextView.setText(mOrigin);
        mLocationTextView.setText(mLocation);

        mCharacterDetailPresenter = new CharacterDetailPresenter(this, this, mImageUrl);
    }

    @Override
    protected CharacterDetailPresenter getPresenter() {
        return mCharacterDetailPresenter;
    }

    private void getIntentExtras(Intent intent){
        mName = intent.getStringExtra(NAME);
        mGender = intent.getStringExtra(GENDER);
        mStatus = intent.getStringExtra(STATUS);
        mSpecies = intent.getStringExtra(SPECIES);
        mOrigin = intent.getStringExtra(ORIGIN);
        mLocation = intent.getStringExtra(LOCATION);
        mImageUrl = intent.getStringExtra(IMAGE_URL);
    }

    @Override
    public void showCharacterImage(final Bitmap imageBitmap) {
        mCharacterImage.setImageBitmap(imageBitmap);
    }

    @Override
    public void showError(final String message) {
        final Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
