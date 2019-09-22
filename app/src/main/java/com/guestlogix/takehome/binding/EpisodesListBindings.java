package com.guestlogix.takehome.binding;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.guestlogix.takehome.data.Episode;
import com.guestlogix.takehome.fragments.EpisodesListingFragment;

/**
 * Contains {@link BindingAdapter}s for the {@link Episode} list.
 */
public class EpisodesListBindings {

    private LifecycleOwner lifecycle;

    public EpisodesListBindings(LifecycleOwner lifecycle) {
        this.lifecycle = lifecycle;
    }

    @BindingAdapter("android:data")
    public void setItems(RecyclerView listView, LiveData<PagedList<Episode>> items) {
        items.observe(lifecycle, episodes -> {
            EpisodesListingFragment.EpisodesListAdapter adapter = (EpisodesListingFragment.EpisodesListAdapter) listView.getAdapter();
            if (adapter != null) {
                adapter.submitList(episodes);
            }
        });
    }
}