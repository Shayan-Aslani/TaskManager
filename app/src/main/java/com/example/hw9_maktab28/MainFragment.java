package com.example.hw9_maktab28;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public static final String ARG_USERID = "userId";

    private UUID userId;

    public static MainFragment newInstance(UUID userId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_USERID , userId);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = (UUID) getArguments().getSerializable(ARG_USERID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        ViewPagerAdapter myPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

}
