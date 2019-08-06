package com.rickandmorty.adapter.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.rickandmorty.R;
import com.rickandmorty.databinding.ViewCharacterDeadBinding;

public class CharacterDeadViewHolder extends RecyclerView.ViewHolder {

  public ViewCharacterDeadBinding mBinding;

  public CharacterDeadViewHolder(View itemView) {
    super(itemView);
    mBinding= DataBindingUtil.bind(itemView);
  }

  public static View getView(ViewGroup parent){
    return LayoutInflater.from(parent.getContext()).inflate(R.layout.view_character_dead, parent, false);
  }
}
