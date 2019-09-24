package com.example.hw9_maktab28.mainController;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw9_maktab28.R;
import com.example.hw9_maktab28.model.Task;

import java.io.Serializable;
import java.util.List;


    public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> implements Serializable {

        public static final String TASK_DETAIL_FRAGMENT_TAG = "TaskDetail";


        private List<Task> taskList;
        public  Fragment fragment;
        private RecyclerView recyclerView;

        public TaskAdapter(List<Task> taskList , Fragment fragment , RecyclerView recyclerView )
        {
            this.taskList = taskList;
            this.fragment = fragment;
            this.recyclerView = recyclerView;
        }


        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Activity activity = (Activity) parent.getContext();
            View view = activity.getLayoutInflater().inflate(R.layout.tasks_row , parent , false);
            return new TaskAdapter.TaskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = taskList.get(position);
            holder.bind(task);
        }


        @Override
        public int getItemCount()
        {
            if(taskList.size()> 0 )
                recyclerView.setBackgroundColor(Color.WHITE);
            else
                recyclerView.setBackgroundResource(R.drawable.ic_custom_background);

            return taskList == null ? 0 : taskList.size();
        }


        public class TaskViewHolder extends RecyclerView.ViewHolder
        {
            private TextView taskNameTextView ;
            private TextView taskStateTextView;
            private TextView dateTextView;
            private TextView letterTextView;


            public TaskViewHolder(@NonNull final View itemView) {
                super(itemView);
                taskNameTextView = itemView.findViewById(R.id.taskTitle_textView);
                taskStateTextView = itemView.findViewById(R.id.taskState_textView);
                letterTextView = itemView.findViewById(R.id.letter_TextView);
                dateTextView = itemView.findViewById(R.id.date_textView);

            }



            public void bind(final Task task)
            {

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(task.getId());
                        taskDetailFragment.setTargetFragment(fragment , 0);
                        taskDetailFragment.show(fragment.getFragmentManager() , TASK_DETAIL_FRAGMENT_TAG);
                    }
                });

                if(getAdapterPosition()% 2 == 0)
                    itemView.setBackgroundColor(0xFFBEC2FF);
                else
                    itemView.setBackgroundColor(0x804250B2);

                taskNameTextView.setText("Task Name : " + task.getTitle());
//                taskStateTextView.setText("State : " + task.getState().toString());
                letterTextView.setText(String.valueOf(task.getTitle().charAt(0)));
                dateTextView.setText("Date : " + task.getDate());
            }
        }




    }


