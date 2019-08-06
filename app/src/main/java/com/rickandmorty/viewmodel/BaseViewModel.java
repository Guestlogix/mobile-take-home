package com.rickandmorty.viewmodel;

import android.app.ProgressDialog;

class BaseViewModel {

  private ProgressDialog progressDialog;
  boolean initial = true;

  void showProgress(LifeCycle.View viewCallBack) {
    if (viewCallBack != null && viewCallBack.getActivity() != null) {
      progressDialog = new ProgressDialog(viewCallBack.getActivity());
      progressDialog.setCancelable(true);
      progressDialog.setMessage("Loading ...");
      progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      progressDialog.setCancelable(false);
      progressDialog.show();
    }
  }

  void hideProgress() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  public boolean isInitial() {
    return initial;
  }

  public void setInitial(boolean initial) {
    this.initial = initial;
  }
}
