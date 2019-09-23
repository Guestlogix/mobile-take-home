package com.guestlogix.takehome.binding;

import androidx.databinding.DataBindingComponent;
import androidx.lifecycle.LifecycleOwner;

public class DataBindingComponentImpl implements DataBindingComponent {

    private LifecycleOwner lifecycleOwner;

    public DataBindingComponentImpl(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public RecyclerViewBindings getRecyclerViewBindings() {
        return new RecyclerViewBindings(lifecycleOwner);
    }
}
