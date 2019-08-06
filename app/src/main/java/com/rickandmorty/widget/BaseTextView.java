package com.rickandmorty.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.rickandmorty.R;
import com.rickandmorty.utils.TypeFaceManager;

public class BaseTextView extends AppCompatTextView {

  String familyFont = "averta";

  public BaseTextView(Context context) {
    super(context);
    init();
  }

  public BaseTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray a = getContext().getTheme().obtainStyledAttributes(
        attrs,
        R.styleable.BaseTextView,
        0, 0);
    familyFont = a.getString(R.styleable.BaseTextView_tvFont);
    init();
  }

  public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray a = getContext().getTheme().obtainStyledAttributes(
        attrs,
        R.styleable.BaseTextView,
        0, 0);
    familyFont = a.getString(R.styleable.BaseTextView_tvFont);
    init();
  }

  private void init() {
    Typeface typeface = getTypeface();
    if (typeface != null && typeface.isBold()) {
      setTypeface(TypeFaceManager.getTypeFace(getContext(), TypeFaceManager.FONT_THE_ONE_BOLD));
    }else {
      setTypeface(TypeFaceManager.getTypeFace(getContext(), TypeFaceManager.FONT_THE_ONE_REGULAR));
    }
  }
}
