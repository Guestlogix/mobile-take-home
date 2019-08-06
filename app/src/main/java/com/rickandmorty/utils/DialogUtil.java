package com.rickandmorty.utils;

import android.app.Dialog;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;

public class DialogUtil {

  public static void showErrorDialog(Context context, String message, String buttonText, AlertDialog.OnClickListener onClickListener) {
    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
    alertDialog.setMessage(message);
    alertDialog.setButton(Dialog.BUTTON_POSITIVE, buttonText, onClickListener);
    alertDialog.show();
  }
}
