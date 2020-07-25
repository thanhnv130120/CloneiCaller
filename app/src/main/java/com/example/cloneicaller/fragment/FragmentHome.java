package com.example.cloneicaller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.cloneicaller.R;
import com.example.cloneicaller.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FragmentHome extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        setUpTab();
        return view;
    }

    private void setUpTab() {
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_dial_hover);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_history);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_blocking);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_setting);
                        break;
                    case 1:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_pad);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_history_hover);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_blocking);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_setting);
                        break;
                    case 2 :
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_pad);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_history);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_blocking_hover);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_setting);
                        break;
                    case 3:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_pad);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_history);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_blocking);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_setting_hover);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
