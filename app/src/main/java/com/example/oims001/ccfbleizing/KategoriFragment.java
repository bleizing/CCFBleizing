package com.example.oims001.ccfbleizing;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class KategoriFragment extends Fragment {


    public KategoriFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kategori, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager(ViewPager viewpager)
    {
        PageTabsAdapter adapter = new PageTabsAdapter(getActivity().getSupportFragmentManager());
        for (int i = 2013; i <= 2017; i++) {
            adapter.addFrag(new KategoriDetailFragment(), "" + i);
        }
        viewpager.setAdapter(adapter);
    }
}
