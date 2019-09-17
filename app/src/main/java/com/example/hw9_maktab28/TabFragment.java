package com.example.hw9_maktab28;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.State;
import com.example.hw9_maktab28.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {


    private RecyclerView taskRecyclerView;
    private List<Task> list;
    private TaskAdapter taskAdapter;
    private State tabState ;
    private FloatingActionButton fab;
    public static final String ADD_TASK_FRAGMENT_TAG = "AddTask";


    public TabFragment(State state) {
        tabState = state;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tab, container, false);
        initUi(view);

        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = Repository.getInstance().getStateTaskList(tabState);
        taskAdapter = new TaskAdapter(list , this);
        taskRecyclerView.setAdapter(taskAdapter);
        if(list.size() == 0)
            taskRecyclerView.setBackgroundColor(Color.BLUE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddTaskFragment addTaskFragment = AddTaskFragment.newInstance(tabState , taskAdapter);

                addTaskFragment.setTargetFragment(TabFragment.this, 0);

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


}
