package com.rickandmorty.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.rickandmorty.interfaces.RecyclerItemOnClick;

public abstract class BaseAdapter extends RecyclerView.Adapter {

  public static final String TAG_ITEM_CLICK = "TAG_ITEM_CLICK";
  RecyclerItemOnClick mItemOnClick;
  boolean isHasFooter = true;
  boolean isStillLoadingMore = false;
  public static final int TYPE_ITEM = 1;
  public static final int TYPE_FOOTER = 2;

  public BaseAdapter setItemOnClick(RecyclerItemOnClick itemOnClick) {
    mItemOnClick = itemOnClick;
    return this;
  }

  public boolean isHasFooter() {
    return isHasFooter;
  }

  public void setHasFooter(boolean hasFooter) {
    isHasFooter = hasFooter;
  }

  public boolean isStillLoadingMore() {
    return isStillLoadingMore;
  }

  public void setStillLoadingMore(boolean stillLoadingMore) {
    isStillLoadingMore = stillLoadingMore;
  }

}
