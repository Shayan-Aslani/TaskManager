package com.example.hw9_maktab28.LoginController;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hw9_maktab28.mainController.MainActivity;
import com.example.hw9_maktab28.R;
import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextInputEditText usernameEditText ;
    private TextInputEditText passwordEditText;
    private MaterialButton loginButton;
    private MaterialButton signupButton;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignupActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (loginCheck(usernameEditText.getText().toString(), passwordEditText.getText().toString())) {
                    User user =  Repository.getInstance().getUser(usernameEditText.getText().toString() , passwordEditText.getText().toString());
                    startActivity(MainActivity.newIntent(getActivity(), user.getUserId()));
                }
                else
                    Toast.makeText(getActivity(), "Incorrect Username or Password !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initUi(View view){

        usernameEditText = view.findViewById(R.id.username_EditText_login);
        passwordEditText = view.findViewById(R.id.password_EditText_login);
        loginButton = view.findViewById(R.id.login_Button_loginFragment) ;
        signupButton = view.findViewById(R.id.signup_Button_loginFragment);
    }


    private boolean loginCheck(String username , String password)
        {
        if(Repository.getInstance().getUserList().contains(new User(username , password)))
        return true;

        return false;
        }

}
