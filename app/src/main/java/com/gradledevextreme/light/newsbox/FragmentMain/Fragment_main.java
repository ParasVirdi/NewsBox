package com.gradledevextreme.light.newsbox.FragmentMain;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gradledevextreme.light.newsbox.R;


/**
  Main Fragment in Navigation Activity
 */
public class Fragment_main extends Fragment {


    public Fragment_main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fragment_main, container, false);
        return rootView;

    }

}
