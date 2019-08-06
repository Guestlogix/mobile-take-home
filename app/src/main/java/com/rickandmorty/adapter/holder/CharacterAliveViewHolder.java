package com.rickandmorty.adapter.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.rickandmorty.R;
import com.rickandmorty.databinding.ViewCharacterAliveBinding;

public class CharacterAliveViewHolder extends RecyclerView.ViewHolder {

  public ViewCharacterAliveBinding mBinding;

  public CharacterAliveViewHolder(View itemView) {
    super(itemView);
    mBinding= DataBindingUtil.bind(itemView);
  }

  public static View getView(ViewGroup parent){
     return LayoutInflater.from(parent.getContext()).inflate(R.layout.view_character_alive, parent, false);
  }
}
