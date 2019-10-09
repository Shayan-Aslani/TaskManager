package com.example.hw9_maktab28.mainController;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.hw9_maktab28.R;
import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.State;
import com.example.hw9_maktab28.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {


    public static final int REQUEST_CODE_ADD_TASK = 0;
    private RecyclerView taskRecyclerView;
    private List<Task> list;
    public static TaskAdapter taskAdapter;
    private State tabState ;
    private FloatingActionButton fab;
    public static final String ARG_TAB_STATE = "TabState";
    public static final String ADD_TASK_FRAGMENT_TAG = "AddTask";


    public static TabFragment newInstance(State state) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TAB_STATE , state);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabState = (State) getArguments().getSerializable(ARG_TAB_STATE);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tab, container, false);
        initUi(view);

        setTaskRecyclerView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskFragment addTaskFragment = AddTaskFragment.newInstance(tabState);
                addTaskFragment.setTargetFragment(TabFragment.this, REQUEST_CODE_ADD_TASK);
                addTaskFragment.show(getFragmentManager(), ADD_TASK_FRAGMENT_TAG);
            }
        });
        return view;
    }

    private void initUi(View view)
    {
        fab = view.findViewById(R.id.fab);
        taskRecyclerView = view.findViewById(R.id.task_recyclerView);
    }

    private void setTaskRecyclerView(){
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = Repository.getInstance(getContext()).getUserStateTaskList(tabState , Repository.getInstance(getContext()).getLoginedUser().getUserId());
        taskAdapter = new TaskAdapter(list , this , taskRecyclerView);
        taskRecyclerView.setAdapter(taskAdapter);
    }


}
