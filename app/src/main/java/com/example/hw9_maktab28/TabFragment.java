package com.example.hw9_maktab28;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.State;
import com.example.hw9_maktab28.model.Task;

import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {


    private RecyclerView toDoRecyclerView ;
    private List<Task> list;
    private TaskAdapter taskAdapter;
    private State tabState ;

    public TabFragment(State state) {
        tabState = state;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toDoRecyclerView = view.findViewById(R.id.task_recyclerView);
        toDoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        for (int i = 0 ; i<10 ; i++){
            Repository.getInstance().addTask(new Task("aaa" , tabState));
        }
        list = Repository.getInstance().getTaskList();
        taskAdapter = new TaskAdapter(list);
        toDoRecyclerView.setAdapter(taskAdapter);
    }

    private State getRdmState() {
        int index = new Random().nextInt(State.values().length);
        return State.values()[index];
    }

}
