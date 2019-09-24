package com.guestlogix.takehome.binding;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.guestlogix.takehome.fragments.CharactersListFragment;
import com.guestlogix.takehome.fragments.EpisodesListingFragment;
import com.guestlogix.takehome.models.CharacterRowStub;
import com.guestlogix.takehome.models.EpisodeRowStub;
import com.guestlogix.takehome.utils.Optional;

import java.util.List;

/**
 * Contains {@link BindingAdapter}s for the list.
 */
public class RecyclerViewBindings {

    private LifecycleOwner lifecycle;

    RecyclerViewBindings(LifecycleOwner lifecycle) {
        this.lifecycle = lifecycle;
    }

    @BindingAdapter("android:data")
    public void setEpisodes(RecyclerView listView, LiveData<PagedList<EpisodeRowStub>> items) {
        items.observe(lifecycle, episodes -> {
            EpisodesListingFragment.EpisodesListAdapter adapter = (EpisodesListingFragment.EpisodesListAdapter) listView.getAdapter();
            if (adapter != null) {
                adapter.submitList(episodes);
            }
        });
    }

    @BindingAdapter("android:loading")
    public void setLoading(RecyclerView listView, LiveData<Boolean> loading) {
        loading.observe(lifecycle, isLoading ->
            Optional.ofNullable(listView.getAdapter()).ifPresent(adapter -> {
                if(adapter instanceof EpisodesListingFragment.EpisodesListAdapter) {
                    ((EpisodesListingFragment.EpisodesListAdapter) adapter).setNetworkState(isLoading);
                } else if(adapter instanceof CharactersListFragment.CharactersListAdapter) {
                    ((CharactersListFragment.CharactersListAdapter) adapter).setNetworkState(isLoading);
                }
            })
        );
    }

    @BindingAdapter("android:data")
    public void setCharacters(RecyclerView listView, LiveData<List<CharacterRowStub>> items) {
        items.observe(lifecycle, characters -> {
            CharactersListFragment.CharactersListAdapter adapter = (CharactersListFragment.CharactersListAdapter) listView.getAdapter();
            if (adapter != null) {
                adapter.submitList(characters);
            }
        });
    }
}