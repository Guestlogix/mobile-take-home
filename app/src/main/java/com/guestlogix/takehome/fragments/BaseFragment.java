package com.guestlogix.takehome.fragments;


import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.utils.Optional;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    protected Optional<NavController> findNavController() {
        return Optional.ofNullable(getActivity()).map( activity ->
            Navigation.findNavController(activity, R.id.nav_host_fragment)
        );
    }
}
