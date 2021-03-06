package com.example.hw9_maktab28.LoginController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.hw9_maktab28.R;

public class SignupActivity extends AppCompatActivity {



    public static Intent newIntent(Context context) {
        return new Intent(context, SignupActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.signup_container_layout);
        if (fragment == null)
            fragmentManager
                    .beginTransaction()
                    .add(R.id.signup_container_layout, SignupFragment.newInstance())
                    .commit();
    }
}
