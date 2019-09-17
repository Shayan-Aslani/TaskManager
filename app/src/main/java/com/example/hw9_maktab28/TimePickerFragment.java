package com.example.hw9_maktab28;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {


    private static final String ARG_TASK_TIME = "taskTime";
    private static final String EXTRA_TASK_TIME = "com.example.hw9_maktab28.taskTime";


    private Date mDate;
    private TimePicker mTimePicker;

    public static String getExtraTaskTime() {
        return EXTRA_TASK_TIME;
    }

    public static TimePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TimePickerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDate = (Date) getArguments().getSerializable(ARG_TASK_TIME);
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_time_picker, null, false);

        mTimePicker = view.findViewById(R.id.timepicker);
        initTimePicker();
        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Date date = extractTime();
                        sendResult(date);
                    }
                })
                .setView(view)
                .create();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Date extractTime() {
        int hour = mTimePicker.getHour();
        int minute = mTimePicker.getMinute();
        GregorianCalendar calendar = new GregorianCalendar(0, 0,0 , hour, minute);
        return calendar.getTime();
    }

    private void initTimePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
    }

    private void sendResult(Date date) {
        Fragment fragment = getTargetFragment();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_TIME, date);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }


}
