package com.example.hw9_maktab28.LoginController;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hw9_maktab28.R;
import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {


    private TextInputEditText usernameEditText ;
    private TextInputEditText passwordEditText;
    private MaterialButton signupButton;

    public static SignupFragment newInstance() {

        Bundle args = new Bundle();

        SignupFragment fragment = new SignupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String username =  usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(signupCheck(username ,  password))
                {
                    Repository.getInstance(getContext()).addUser(new User(username,password));
                    Toast.makeText(getActivity(), "SignUp Succesfully !", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }
        });
    }

    private void initUi(View view){

        usernameEditText = view.findViewById(R.id.username_EditText_signup);
        passwordEditText = view.findViewById(R.id.password_EditText_signup);
        signupButton = view.findViewById(R.id.signup_Button_signupFragment);
    }

    private boolean signupCheck(String username , String password)
    {
        for(User user : Repository.getInstance(getContext()).getUserList()) {
            if (user.getUsername().equals(username)) {
                Toast.makeText(getActivity(), "Invalid Username !", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(username.length() == 0 || password.length() == 0)
        {
            Toast.makeText(getActivity(), "Invalid Username or Password !!!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
