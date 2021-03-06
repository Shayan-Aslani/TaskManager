package com.example.hw9_maktab28.mainController;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hw9_maktab28.R;
import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.State;
import com.example.hw9_maktab28.model.Task;
import com.example.hw9_maktab28.utils.PictureUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import static com.example.hw9_maktab28.mainController.AddTaskFragment.DATE_PICKER_FRAGMENT_TAG;
import static com.example.hw9_maktab28.mainController.AddTaskFragment.REQUEST_CODE_DATE_PICKER;
import static com.example.hw9_maktab28.mainController.AddTaskFragment.REQUEST_CODE_TIME_PICKER;
import static com.example.hw9_maktab28.mainController.AddTaskFragment.TIME_PICKER_FRAGMENT_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends DialogFragment {


    private static final String ARG_TASK_ID = "taskId";

    private static final int REQUEST_CODE_CAPTURE_IMAGE = 2;
    public static final String FILE_PROVIDER_AUTHORITY = "com.example.hw9_maktab28.fileProvider";

    private Task mTask;
    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    private MaterialButton dateButton;
    private MaterialButton timeButton;
    private MaterialButton shareButton;
    private SeekBar stateSeekbar;
    private TextView todoSeekBarTxtView, doingseekBarTxtView, doneseekBarTxtView;

    private ImageView taskImgeView;
    private MaterialButton imageButtonPhoto;
    private File photoFile;
    private Uri photoUri;
    Calendar taskCalendar = new GregorianCalendar();

    private Intent cameraIntent;

    private Callbacks mCallbacks;

    public static TaskDetailFragment newInstance(UUID uuid) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, uuid);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Callbacks)
            mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /*
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mCallbacks.updateUi();
    }

     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTask = Repository.getInstance(getContext()).getTask((UUID) getArguments().getSerializable(ARG_TASK_ID));

        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = Repository.getInstance(getContext()).getPhotoFile(mTask);
        photoUri = FileProvider.getUriForFile(getContext(), FILE_PROVIDER_AUTHORITY, photoFile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       if (getActivity()
                .getPackageManager()
                .resolveActivity(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY) == null
                ||
                photoFile == null)
            taskImgeView.setEnabled(false);


        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_task_detail, null, false);
        initUi(view);
        getDetail();
        setViewEditable(false);
        updatePhotoView();

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareCompat.IntentBuilder shareIntent = ShareCompat.IntentBuilder.from(getActivity());
                shareIntent.setText(mTask.getTitle() + " : " + mTask.getDescription());
                shareIntent.getIntent().setAction(Intent.ACTION_SEND);
                shareIntent.getIntent().setType("text/plain");
                startActivity(shareIntent.getIntent());
            }
        });


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
                timePickerFragment.setTargetFragment(TaskDetailFragment.this, REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), TIME_PICKER_FRAGMENT_TAG);
            }
        });

        imageButtonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                grantCameraPermission(photoUri);
                startActivityForResult(cameraIntent, REQUEST_CODE_CAPTURE_IMAGE);
            }
        });

        stateSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                todoSeekBarTxtView.setTypeface(null, Typeface.NORMAL);
                doingseekBarTxtView.setTypeface(null, Typeface.NORMAL);
                doneseekBarTxtView.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                switch (seekBar.getProgress()) {
                    case 0:
                        todoSeekBarTxtView.setTypeface(null, Typeface.BOLD);
                        break;
                    case 1:
                        doingseekBarTxtView.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        doneseekBarTxtView.setTypeface(null, Typeface.BOLD);
                        break;
                }
            }
        });


        final Dialog detailDialog = new AlertDialog.Builder(getActivity())
                .setNeutralButton("Delete", null)
                .setNegativeButton("Edit", null)
                .setPositiveButton("Save", null)
                .setView(view)
                .create();

        detailDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button editButton = ((AlertDialog) detailDialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setViewEditable(true);
                    }
                });

                Button saveButton = ((AlertDialog) detailDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkInputs()) {
                            editTask();
                            mCallbacks.updateUi();
                            dismiss();
                        }
                    }
                });

                Button deleteButton = ((AlertDialog) detailDialog).getButton(AlertDialog.BUTTON_NEUTRAL);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new android.app.AlertDialog.Builder(getContext())
                                .setTitle("Delete Task ?")
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteTask();
                                        detailDialog.dismiss();
                                        mCallbacks.updateUi();
                                        Toast.makeText(getActivity(), "task Deleted !!", Toast.LENGTH_SHORT).show();
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

            taskCalendar.set(taskCalendar.get(Calendar.YEAR), taskCalendar.get(Calendar.MONTH), taskCalendar.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.HOUR_OF_DAY), cal2.get(Calendar.MINUTE));
        }
        else if (requestCode == REQUEST_CODE_CAPTURE_IMAGE) {
            getActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }

    }


    private void initUi(View view) {
        titleEditText = view.findViewById(R.id.title_editText_Detail);
        descriptionEditText = view.findViewById(R.id.description_editText_Detail);
        dateButton = view.findViewById(R.id.date_Button_Detail);
        timeButton = view.findViewById(R.id.time_Button_Detail);
        stateSeekbar = view.findViewById(R.id.taskstate_seekBar_Detail);
        todoSeekBarTxtView = view.findViewById(R.id.todo_SeekBar_TextView_Detail);
        doingseekBarTxtView = view.findViewById(R.id.doing_SeekBar_TextView_Detail);
        doneseekBarTxtView = view.findViewById(R.id.done_SeekBar_TextView_Detail);
        shareButton = view.findViewById(R.id.share_button_detail);
        taskImgeView = view.findViewById(R.id.task_imageView_detail);
        imageButtonPhoto = view.findViewById(R.id.camera_imageButton_detail);
    }

    private void getDetail() {
        titleEditText.setText(mTask.getTitle());
        descriptionEditText.setText(mTask.getDescription());
        SimpleDateFormat date_format = new SimpleDateFormat("YYYY/MM/dd");
        dateButton.setText(date_format.format(mTask.getDate()));
        date_format = new SimpleDateFormat("HH:mm:ss");
        timeButton.setText(date_format.format(mTask.getDate().getTime()));
        setSeekbarState(mTask.getState());
        taskCalendar.setTime(mTask.getDate());
        updatePhotoView();
    }

    private void setViewEditable(final boolean enabled) {
        titleEditText.setEnabled(enabled);
        descriptionEditText.setEnabled(enabled);
        dateButton.setEnabled(enabled);
        timeButton.setEnabled(enabled);
        imageButtonPhoto.setEnabled(enabled);
        stateSeekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return !enabled;
            }
        });
    }

    private void editTask() {
        mTask.setTitle(titleEditText.getText().toString());
        mTask.setDescription(descriptionEditText.getText().toString());
        mTask.setDate(taskCalendar.getTime());
        mTask.setState(getSeekbarState());
        try {
            Repository.getInstance(getContext()).updateTask(mTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteTask() {
        try {
            Repository.getInstance(getContext()).deleteTask(mTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setSeekbarState(State state) {
        switch (state) {
            case Todo:
                stateSeekbar.setProgress(0);
                break;
            case Doing:
                stateSeekbar.setProgress(1);
                break;
            case Done:
                stateSeekbar.setProgress(2);
                break;
        }
    }


    public State getSeekbarState() {
        switch (stateSeekbar.getProgress()) {
            case 0:
                return State.Todo;
            case 1:
                return State.Doing;
            case 2:
                return State.Done;
        }
        return null;
    }

    private boolean checkInputs() {
        if (titleEditText.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please input Title !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void grantCameraPermission(Uri photoUri) {
        //grant uri permission to camera
        List<ResolveInfo> cameraActivities = getActivity().getPackageManager()
                .queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : cameraActivities) {
            getActivity().grantUriPermission(activity.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    private void updatePhotoView() {
        if (photoFile == null || !photoFile.exists()) {
            taskImgeView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils
                    .getScaledBitmap(photoFile.getAbsolutePath(), getActivity());
            taskImgeView.setImageBitmap(bitmap);
        }
    }

    public interface Callbacks {
        void updateUi();
    }


}
