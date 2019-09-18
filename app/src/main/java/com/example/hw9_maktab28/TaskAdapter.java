package com.example.hw9_maktab28;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw9_maktab28.model.Task;

import java.io.Serializable;
import java.util.List;


    public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> implements Serializable {

        public static final String TASK_DETAIL_FRAGMENT_TAG = "TaskDetail";


        public List<Task> taskList;
        public static Fragment fragment;
        private RecyclerView recyclerView;

        public TaskAdapter(List<Task> taskList , Fragment fragment , RecyclerView recyclerView)
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
                recyclerView.setBackgroundColor(Color.BLUE);
            return taskList == null ? 0 : taskList.size();
        }


        public class TaskViewHolder extends RecyclerView.ViewHolder
        {
            private TextView taskNameTextView ;
            private TextView taskStateTextView;
            private TextView numberTextView;


            public TaskViewHolder(@NonNull final View itemView) {
                super(itemView);


                taskNameTextView = itemView.findViewById(R.id.taskName_textView);
                taskStateTextView = itemView.findViewById(R.id.taskState_textView);
                numberTextView = itemView.findViewById(R.id.number_textView);

            }



            public void bind(final Task task)
            {

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(task.getId() , TaskAdapter.this);

                        taskDetailFragment.setTargetFragment(fragment , 0);

                        taskDetailFragment.show(fragment.getFragmentManager() , TASK_DETAIL_FRAGMENT_TAG);
                    }
                });

                if(getAdapterPosition()% 2 == 0)
                    itemView.setBackgroundColor(0xFFEC407A);
                else
                    itemView.setBackgroundColor(0xFFF5B9CD);

                taskNameTextView.setText("Task Name : " + task.getTitle());
                taskStateTextView.setText("State : " + task.getState().toString());
                numberTextView.setText("Number : " + (getAdapterPosition()+1));
            }
        }


    }


