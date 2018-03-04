package com.gradledevextreme.light.newsbox.BottomNavigation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gradledevextreme.light.newsbox.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForYou extends Fragment {




    public ForYou() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_for_you, container, false);
    }




}
