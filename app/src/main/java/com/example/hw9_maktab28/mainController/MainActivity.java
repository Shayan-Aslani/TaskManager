package com.example.hw9_maktab28.mainController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.hw9_maktab28.R;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements TaskDetailFragment.Callbacks,
        AddTaskFragment.Callbacks , UserDetailFragment.Callbacks{

    public static final String EXTRA_USER_ID = "com.example.hw9_maktab28.userid";
    private static UUID userId;
    private Fragment fragment;

    public static Intent newIntent(Context context, UUID userId) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = (UUID) getIntent().getSerializableExtra(EXTRA_USER_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.main_container_layout);

            if (fragment == null)
                fragmentManager
                        .beginTransaction()
                        .add(R.id.main_container_layout, MainFragment.newInstance(userId))
                        .commit();

    }

    @Override
    public void onBackPressed() {
            if (getFragmentManager().getBackStackEntryCount() != 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }

    }

    public void updateViewPager() {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_container_layout);
        if (mainFragment != null)
            mainFragment.updateViewPager();
    }



    @Override
    public void updateUi() {
        updateViewPager();
    }

    @Override
    public void onTaskAdded() {
        updateViewPager();
    }

    @Override
    public void updateUserListUi() {
        UserListFragment userListFragment = (UserListFragment) getSupportFragmentManager().findFragmentById(R.id.main_container_layout);
        if (userListFragment != null)
            userListFragment.updateUi();
    }
}
