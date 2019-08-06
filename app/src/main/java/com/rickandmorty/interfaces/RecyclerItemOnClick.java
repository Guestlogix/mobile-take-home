package com.rickandmorty.interfaces;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerItemOnClick {
  void onClickItem(View view, RecyclerView.ViewHolder viewHolder,String tagAction, Object data, int position);
}
