package com.gradledevextreme.light.newsbox.BottomNavigation;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gradledevextreme.light.newsbox.R;
import com.gradledevextreme.light.newsbox.World.W_Business;
import com.gradledevextreme.light.newsbox.World.W_Entertainment;
import com.gradledevextreme.light.newsbox.World.W_Science;
import com.gradledevextreme.light.newsbox.World.W_Sports;
import com.gradledevextreme.light.newsbox.World.W_Technology;
import com.gradledevextreme.light.newsbox.World.W_TopStories;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class World extends Fragment {




    private TabLayout tabLayout;
    private ViewPager viewPager;




    public World() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_head_lines, null);




        //Declaration of view pager
        viewPager = (ViewPager) view.findViewById(R.id.vpPager);
        setupViewPager(viewPager);





        //tablayout declaration
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);




        // Inflate the layout for this fragment
        return view;
    }




    class ViewPagerAdapter extends FragmentPagerAdapter {




        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }




        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }




        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }




        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }




    }




    private void setupViewPager(ViewPager viewPager) {




        World.ViewPagerAdapter adapter = new World.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new W_TopStories(), "Top Stories");
        adapter.addFragment(new W_Business(), "Business");
        adapter.addFragment(new W_Technology(), "Technology");
        adapter.addFragment(new W_Entertainment(), "Entertainment");
        adapter.addFragment(new W_Sports(), "Sports");
        adapter.addFragment(new W_Science(), "Science");




        viewPager.setAdapter(adapter);




    }
}
