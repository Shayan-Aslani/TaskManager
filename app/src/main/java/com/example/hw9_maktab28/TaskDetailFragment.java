package com.example.hw9_maktab28;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.State;
import com.example.hw9_maktab28.model.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends DialogFragment {


    private static final String ARG_TASK_ID = "taskId";
    private Task mTask ;
    private TextInputEditText titleEditText ;
    private TextInputEditText descriptionEditText ;
    private MaterialButton dateButton;
    private MaterialButton timeButton ;
    private CheckBox doneCheckBox;

    public static TaskDetailFragment newInstance(UUID uuid) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID , uuid);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTask = Repository.getInstance().getTask((UUID)getArguments().getSerializable(ARG_TASK_ID));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_task_detail, null, false);
        initUi(view);
        getDetail();


        return new AlertDialog.Builder(getActivity())
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                    }
                })
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setView(view)
                .create();
    }


    private void initUi(View view)
    {
        titleEditText = view.findViewById(R.id.title_editText_Detail);
        descriptionEditText = view.findViewById(R.id.description_editText_Detail);
        dateButton = view.findViewById(R.id.date_Button_Add);
        timeButton = view.findViewById(R.id.time_Button_Add) ;
        doneCheckBox = view.findViewById(R.id.done_CheckBox_Add) ;
    }

    private void getDetail(){
        titleEditText.setText(mTask.getTitle());
        descriptionEditText.setText(mTask.getDescription());
        SimpleDateFormat date_format = new SimpleDateFormat("YYYY/mm/dd");
        dateButton.setText(date_format.format(mTask.getDate()));
        date_format = new SimpleDateFormat("HH:mm:ss");
        timeButton.setText(date_format.format(mTask.getDate().getTime()));
        doneCheckBox.setChecked(mTask.getState().equals(State.Done));
    }

}
