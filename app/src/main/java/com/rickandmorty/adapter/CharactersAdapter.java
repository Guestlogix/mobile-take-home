package com.rickandmorty.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rickandmorty.R;
import com.rickandmorty.adapter.holder.CharacterAliveViewHolder;
import com.rickandmorty.adapter.holder.CharacterDeadViewHolder;
import com.rickandmorty.databinding.ViewCharacterAliveBinding;
import com.rickandmorty.databinding.ViewCharacterDeadBinding;
import com.rickandmorty.model.character.CharactersModel;
import java.util.List;

public class CharactersAdapter extends BaseAdapter {

  public static final int TYPE_ITEM_DEAD = 1;
  public static final int TYPE_ITEM_ALIVE = 2;
  public static final int TYPE_ITEM_UNKNOWN = 3;
  private static final String STATUS_ALIVE = "alive";
  private static final String STATUS_DEAD = "dead";
  private List<CharactersModel> charactersList;

  @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == TYPE_ITEM_ALIVE) {
      View view = CharacterAliveViewHolder.getView(parent);
      return new CharacterAliveViewHolder(view);
    } else if (viewType == TYPE_ITEM_DEAD) {
      View view = CharacterDeadViewHolder.getView(parent);
      return new CharacterDeadViewHolder(view);
    } else {
      View view = CharacterAliveViewHolder.getView(parent);
      return new CharacterAliveViewHolder(view);
    }
  }

  @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
    if (holder instanceof CharacterAliveViewHolder) {
      final CharacterAliveViewHolder viewHolder = (CharacterAliveViewHolder) holder;
      ViewCharacterAliveBinding viewCharacterAliveBinding = viewHolder.mBinding;
      final CharactersModel charactersModel = (CharactersModel) getCharactersList().get(position);

      viewCharacterAliveBinding.tvCharacterName.setText(charactersModel.getName());
      viewCharacterAliveBinding.tvSpecies.setText(charactersModel.getSpecies());
      viewCharacterAliveBinding.tvGender.setText(charactersModel.getGender());
      viewCharacterAliveBinding.tvStatus.setText(charactersModel.getStatus());

      if (charactersModel.getImage() != null) {
        viewCharacterAliveBinding.imgItem.setImageURI(charactersModel.getImage());
      }

      viewCharacterAliveBinding.getRoot().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mItemOnClick != null) {
            mItemOnClick.onClickItem(v, viewHolder, TAG_ITEM_CLICK, charactersModel, position);
          }
        }
      });
    } else if (holder instanceof CharacterDeadViewHolder) {
      final CharacterDeadViewHolder viewHolder = (CharacterDeadViewHolder) holder;
      ViewCharacterDeadBinding viewCharacterDeadBinding = viewHolder.mBinding;
      final CharactersModel charactersModel = (CharactersModel) getCharactersList().get(position);

      viewCharacterDeadBinding.tvCharacterName.setText(charactersModel.getName());
      viewCharacterDeadBinding.tvSpecies.setText(charactersModel.getSpecies());
      viewCharacterDeadBinding.tvGender.setText(charactersModel.getGender());
      viewCharacterDeadBinding.tvStatus.setText(charactersModel.getStatus());

      if (charactersModel.getImage() != null) {
        viewCharacterDeadBinding.imgItem.setImageURI(charactersModel.getImage());
      } else {
        viewCharacterDeadBinding.imgItem.setImageResource(R.drawable.no_image);
      }

      viewCharacterDeadBinding.getRoot().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mItemOnClick != null) {
            mItemOnClick.onClickItem(v, viewHolder, TAG_ITEM_CLICK, charactersModel, position);
          }
        }
      });
    } else {
      final CharacterAliveViewHolder viewHolder = (CharacterAliveViewHolder) holder;
      ViewCharacterAliveBinding viewCharacterAliveBinding = viewHolder.mBinding;
      final CharactersModel charactersModel = (CharactersModel) getCharactersList().get(position);

      viewCharacterAliveBinding.tvCharacterName.setText(charactersModel.getName());
      viewCharacterAliveBinding.tvSpecies.setText(charactersModel.getSpecies());
      viewCharacterAliveBinding.tvGender.setText(charactersModel.getGender());
      viewCharacterAliveBinding.tvStatus.setText(charactersModel.getStatus());

      if (charactersModel.getImage() != null) {
        viewCharacterAliveBinding.imgItem.setImageURI(charactersModel.getImage());
      } else {
        viewCharacterAliveBinding.imgItem.setImageResource(R.drawable.no_image);
      }

      viewCharacterAliveBinding.getRoot().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mItemOnClick != null) {
            mItemOnClick.onClickItem(v, viewHolder, TAG_ITEM_CLICK, charactersModel, position);
          }
        }
      });
    }
  }

  @Override public int getItemCount() {
    return charactersList.size();
  }

  @Override
  public int getItemViewType(int position) {
    CharactersModel charactersModel = (CharactersModel) getCharactersList().get(position);
    if (charactersModel.getStatus().equalsIgnoreCase(STATUS_ALIVE)) {
      return TYPE_ITEM_ALIVE;
    } else if (charactersModel.getStatus().equalsIgnoreCase(STATUS_DEAD)) {
      return TYPE_ITEM_DEAD;
    } else {
      return TYPE_ITEM_UNKNOWN;
    }
  }

  public void setCharactersList(List<CharactersModel> charactersResponseList) {
    this.charactersList = charactersResponseList;
  }

  private List<CharactersModel> getCharactersList() {
    return charactersList;
  }
}
