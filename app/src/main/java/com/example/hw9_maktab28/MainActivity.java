package com.example.hw9_maktab28;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.State;
import com.example.hw9_maktab28.model.Task;
import com.google.android.material.tabs.TabLayout;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static ViewPagerAdapter viewPagerAdapter;
    public static final String EXTRA_USER_ID = "com.example.hw9_maktab28.userid";
    private static UUID userId;

    public static Intent newIntent(Context context , UUID userId) {

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
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container_layout);
        if (fragment == null)
            fragmentManager
                    .beginTransaction()
                    .add(R.id.main_container_layout, MainFragment.newInstance(userId))
                    .commit();

    }

    public static void UpdateViewPager(){
        viewPagerAdapter.notifyDataSetChanged();
    }

}
