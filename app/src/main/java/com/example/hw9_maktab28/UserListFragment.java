package com.example.hw9_maktab28;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hw9_maktab28.mainController.TaskDetailFragment;
import com.example.hw9_maktab28.mainController.UserDetailFragment;
import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.User;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends Fragment {

    private RecyclerView userRecyclerView ;
    private List<User> mUserList;

    private UserAdapter userAdapter;

    public static final String USER_DETAIL_FRAGMENT_TAG = "userDetail";

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance() {

        Bundle args = new Bundle();

        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserList = Repository.getInstance(getContext()).getUserList() ;
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.app_bar_search).setVisible(false);
        menu.findItem(R.id.menu_item_remove_all).setVisible(false);
        menu.findItem(R.id.menu_userlist).setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_list, container, false);
        initUi(view);
        setUserRecyclerView();
        return view;
    }


    public void initUi(View view){
        userRecyclerView = view.findViewById(R.id.userList_recyclerView);
    }


    private void setUserRecyclerView()
    {
        userAdapter = new UserAdapter(mUserList);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userRecyclerView.setAdapter(userAdapter);
    }


    public void updateUi() {
        mUserList = Repository.getInstance(getContext()).getUserList();
        userAdapter.setUserList(mUserList);
        userAdapter.notifyDataSetChanged();
    }

    private class UserHolder extends RecyclerView.ViewHolder {

        private TextView usernameTextView;
        private TextView dateTextView;
        private TextView taskNumberTextView ;
        private TextView letterTextView ;

        private User mUser;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            usernameTextView = itemView.findViewById(R.id.taskTitle_textView);
            dateTextView = itemView.findViewById(R.id.date_textView);
            taskNumberTextView = itemView.findViewById(R.id.taskState_textView);
            letterTextView = itemView.findViewById(R.id.letter_TextView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserDetailFragment userDetailFragment = UserDetailFragment.newInstance(mUser.getUserId());
                    userDetailFragment.setTargetFragment(UserListFragment.this, 0);
                    userDetailFragment.show(UserListFragment.this.getFragmentManager(), USER_DETAIL_FRAGMENT_TAG);
                }
            });
        }

        public void bind(User user) {
            usernameTextView.setText(user.getUsername());
            dateTextView.setText(user.getDate().toString());
            String number = String.valueOf(Repository.getInstance(getContext()).getUserTaskList(user.getUserId()).size());
            taskNumberTextView.setText("Task Number : " + number);
            letterTextView.setText(String.valueOf(user.getUsername().charAt(0)));
            mUser = user;

        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {

        private List<User> userList;

        public UserAdapter(List<User> users) {
            userList = users;
        }

        public void setUserList(List<User> userList)
        {
            this.userList = userList;
        }


        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.tasks_row, parent, false);
            UserHolder userHolder = new UserHolder(view);
            return userHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.bind(userList.get(position));
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }
    }


}
