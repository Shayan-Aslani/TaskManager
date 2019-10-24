package com.example.hw9_maktab28.mainController;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hw9_maktab28.R;
import com.example.hw9_maktab28.UserListFragment;
import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.Role;
import com.google.android.material.tabs.TabLayout;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public static final String ARG_USERID = "userId";

    private UUID userId;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;


    public static MainFragment newInstance(UUID userId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_USERID, userId);
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

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (Repository.getInstance(getContext()).getLoginedUser().getRole().equals(Role.ADMIN)) {
            menu.findItem(R.id.menu_userlist).setVisible(true);
            menu.findItem(R.id.menu_item_remove_all).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_remove_all:
                new AlertDialog.Builder(getContext())
                        .setTitle("want to delete All Tasks ?")
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteAllTask(userId);
                                updateViewPager();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create()
                        .show();

                return true;
            case R.id.menu_item_logout:
                new AlertDialog.Builder(getContext())
                        .setTitle("Are you sure to logout ?")
                        .setCancelable(false)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create()
                        .show();
                return true;
            case R.id.app_bar_search:
                return false;
            case R.id.menu_userlist:
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.main_container_layout, UserListFragment.newInstance())
                        .addToBackStack("")
                        .commit();
                return false;
        }

        return super.onOptionsItemSelected(item);
    }


    private void deleteAllTask(final UUID userId) {

        try {
            Repository.getInstance(getContext()).removeUserTaskList(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateViewPager() {
        viewPagerAdapter.notifyDataSetChanged();
    }

}
