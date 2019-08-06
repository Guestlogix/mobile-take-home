package com.rickandmorty.utils;

import android.content.Context;
import android.graphics.Typeface;
import java.util.HashMap;

public class TypeFaceManager {
  public static final String FONT_THE_ONE_REGULAR = "Regular.ttf";
  public static final String FONT_THE_ONE_BOLD = "Bold.ttf";

  private static HashMap<String, Typeface> mFontMap = new HashMap<>();

  public static Typeface getTypeFace(Context context, String name) {
    Typeface typeface = mFontMap.get(name);

    if(typeface == null) {
      typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + name);
      mFontMap.put(name, typeface);
    }

    return typeface;
  }
}
