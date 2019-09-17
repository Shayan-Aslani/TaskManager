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
import android.widget.CheckBox;

import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.State;
import com.example.hw9_maktab28.model.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {

    public static final String DATE_PICKER_FRAGMENT_TAG = "DatePicker";
    public static final String TIME_PICKER_FRAGMENT_TAG = "TimePicker";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    private TextInputEditText titleEditText ;
    private TextInputEditText descriptionEditText ;
    private MaterialButton dateButton;
    private MaterialButton timeButton ;
    private CheckBox doneCheckBox;
    Calendar taskCalendar = new GregorianCalendar();

    private Task mTask = new Task();

    public static AddTaskFragment newInstance() {

        Bundle args = new Bundle();
        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_add_task, null, false);

        initUi(view);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new Date());

                //create parent-child between CrimeDetailFragment and DatePickerFragment
                datePickerFragment.setTargetFragment(AddTaskFragment.this, REQUEST_CODE_DATE_PICKER);

                datePickerFragment.show(getFragmentManager(), DATE_PICKER_FRAGMENT_TAG);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(new Date());
                timePickerFragment.setTargetFragment(AddTaskFragment.this , REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getFragmentManager() , TIME_PICKER_FRAGMENT_TAG);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addTask();
                    }
                })
                .setView(view)
                .create();
    }

    private void initUi(View view)
    {
        titleEditText = view.findViewById(R.id.title_EditText_Add);
        descriptionEditText = view.findViewById(R.id.description_EditText_Add);
        dateButton = view.findViewById(R.id.date_Button_Add);
        timeButton = view.findViewById(R.id.time_Button_Add) ;
        doneCheckBox = view.findViewById(R.id.done_CheckBox_Add) ;
    }

    private void addTask(){
        mTask.setTitle(titleEditText.getText().toString());
        mTask.setDescription(descriptionEditText.getText().toString());
        mTask.setState(State.Todo);
        mTask.setDate(taskCalendar.getTime());
        Repository.getInstance().addTask(mTask);
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


}
