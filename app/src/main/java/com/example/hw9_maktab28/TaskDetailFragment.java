package com.example.hw9_maktab28;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.State;
import com.example.hw9_maktab28.model.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import static com.example.hw9_maktab28.AddTaskFragment.ARG_TAB_ADAPTER;
import static com.example.hw9_maktab28.AddTaskFragment.DATE_PICKER_FRAGMENT_TAG;
import static com.example.hw9_maktab28.AddTaskFragment.REQUEST_CODE_DATE_PICKER;
import static com.example.hw9_maktab28.AddTaskFragment.REQUEST_CODE_TIME_PICKER;
import static com.example.hw9_maktab28.AddTaskFragment.TIME_PICKER_FRAGMENT_TAG;


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
    private TaskAdapter taskAdapter;
    Calendar taskCalendar = new GregorianCalendar();

    public static TaskDetailFragment newInstance(UUID uuid , TaskAdapter taskAdapter) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID , uuid);
        args.putSerializable(ARG_TAB_ADAPTER , taskAdapter);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        taskAdapter.taskList = Repository.getInstance().getTaskList();
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTask = Repository.getInstance().getTask((UUID)getArguments().getSerializable(ARG_TASK_ID));

        taskAdapter = (TaskAdapter) getArguments().get(ARG_TAB_ADAPTER);

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

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new Date());
                datePickerFragment.setTargetFragment(TaskDetailFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), DATE_PICKER_FRAGMENT_TAG);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(new Date());
                timePickerFragment.setTargetFragment(TaskDetailFragment.this , REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getFragmentManager() , TIME_PICKER_FRAGMENT_TAG);
            }
        });

        final Dialog dialog = new AlertDialog.Builder(getActivity())
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        deleteTask();
                        Toast.makeText(getActivity(), "task Deleted !!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Edit", null)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editTask();
                    }
                })
                .setView(view)
                .create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button okButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setViewEditable(true);
                    }
                });
            }
        });


        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.getExtraTaskDate());
            SimpleDateFormat date_format = new SimpleDateFormat("YYYY/MM/dd");
            dateButton.setText(date_format.format(date));
            taskCalendar.setTime(date);
        }
        else if (requestCode == REQUEST_CODE_TIME_PICKER) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.getExtraTaskTime());
            Calendar cal2 = new GregorianCalendar();
            cal2.setTime(date);
            SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss");
            timeButton.setText(date_format.format(cal2.getTime()));

            taskCalendar.set(taskCalendar.get(Calendar.YEAR) , taskCalendar.get(Calendar.MONTH) , taskCalendar.get(Calendar.DAY_OF_MONTH ), cal2.get(Calendar.HOUR_OF_DAY) , cal2.get(Calendar.MINUTE));
        }

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
        SimpleDateFormat date_format = new SimpleDateFormat("YYYY/MM/dd");
        dateButton.setText(date_format.format(mTask.getDate()));
        date_format = new SimpleDateFormat("HH:mm:ss");
        timeButton.setText(date_format.format(mTask.getDate().getTime()));
        doneCheckBox.setChecked(mTask.getState().equals(State.Done));
    }

    private void setViewEditable(boolean enabled){
        titleEditText.setEnabled(enabled);
        descriptionEditText.setEnabled(enabled);
        dateButton.setEnabled(enabled);
        timeButton.setEnabled(enabled);
        doneCheckBox.setEnabled(enabled);
    }

    private void editTask(){
        mTask.setTitle(titleEditText.getText().toString());
        mTask.setDescription(descriptionEditText.getText().toString());
        mTask.setDate(taskCalendar.getTime());
        try {
            Repository.getInstance().updateTask(mTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteTask(){
        try {
            Repository.getInstance().deleteTask(mTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
