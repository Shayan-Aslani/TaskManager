package com.example.hw9_maktab28;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {


    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.login_container_layout);
        if (fragment == null)
            fragmentManager
                    .beginTransaction()
                    .add(R.id.login_container_layout, LoginFragment.newInstance())
                    .commit();
    }
}
