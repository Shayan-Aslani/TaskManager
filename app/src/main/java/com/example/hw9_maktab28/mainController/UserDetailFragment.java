package com.example.hw9_maktab28.mainController;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw9_maktab28.R;
import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.User;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends DialogFragment {

    public static final String ARG_USER_ID = "userId";
    private UUID userId;
    private User mUser ;
    private Callbacks mCallbacks ;

    private TextView usernameTextView , passwordTextView , taskNumberTextView ;
    public UserDetailFragment() {
        // Required empty public constructor
    }

    public static UserDetailFragment newInstance(UUID userId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);
        UserDetailFragment fragment = new UserDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof UserDetailFragment.Callbacks)
            mCallbacks = (UserDetailFragment.Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_user_detail, null, false);
        initUi(view);
        getDetail();

        final Dialog detailDialog = new AlertDialog.Builder(getActivity())
                .setNeutralButton("Delete", null)
                .setPositiveButton("Ok", null)
                .setTitle(mUser.getUsername())
                .setView(view)
                .create();

        detailDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {


                Button saveButton = ((AlertDialog) detailDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            dismiss();
                    }
                });

                Button deleteButton = ((AlertDialog) detailDialog).getButton(AlertDialog.BUTTON_NEUTRAL);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new android.app.AlertDialog.Builder(getContext())
                                .setTitle("Delete User ?")
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteUser();
                                        detailDialog.dismiss();
                                        mCallbacks.updateUserListUi();
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, null)
                                .create()
                                .show();
                    }
                });
            }
        });
        return detailDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = (UUID) getArguments().get(ARG_USER_ID);
        mUser = Repository.getInstance(getContext()).getUser(userId);
    }

    private void initUi(View view){
        usernameTextView = view.findViewById(R.id.username_textView);
        passwordTextView = view.findViewById(R.id.password_textView);
        taskNumberTextView = view.findViewById(R.id.tasknumber_textView);
    }

    private void getDetail(){
        usernameTextView.setText("Username : " + mUser.getUsername());
        passwordTextView.setText("Password : " + mUser.getPassword());
        taskNumberTextView.setText("Number of Tasks : " +
                Repository.getInstance(getContext()).getUserTaskList(mUser.getUserId()).size());
    }

    private void deleteUser(){

        if(!mUser.equals(Repository.getInstance(getContext()).getLoginedUser())) {
            try {
                Repository.getInstance(getContext()).removeUserTaskList(mUser.getUserId());
                Repository.getInstance(getContext()).deleteUser(mUser);
                Toast.makeText(getActivity(), "user Deleted !!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(getContext() ,"You can't remove yourself !!", Toast.LENGTH_SHORT).show();
    }

    public interface Callbacks {
        void updateUserListUi();
    }

}
