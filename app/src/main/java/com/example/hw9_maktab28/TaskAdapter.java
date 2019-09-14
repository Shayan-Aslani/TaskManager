package com.example.hw9_maktab28;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw9_maktab28.model.Task;

import java.util.List;


    public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

        private List<Task> taskList;

        public TaskAdapter(List<Task> taskList)
        {
            this.taskList = taskList;
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
        public int getItemCount() {
            return taskList == null ? 0 : taskList.size();
        }


        public static class TaskViewHolder extends RecyclerView.ViewHolder
        {
            private TextView taskNameTextView ;
            private TextView taskStateTextView;
            private TextView numberTextView;



            public TaskViewHolder(@NonNull View itemView) {
                super(itemView);

                taskNameTextView = itemView.findViewById(R.id.taskName_textView);
                taskStateTextView = itemView.findViewById(R.id.taskState_textView);
                numberTextView = itemView.findViewById(R.id.number_textView);

            }

            public void bind(final Task task)
            {
                if(getAdapterPosition()% 2 == 0)
                    itemView.setBackgroundColor(0xFFEC407A);
                else
                    itemView.setBackgroundColor(0xFFF5B9CD);

                taskNameTextView.setText("Task Name : " + task.getName());
                taskStateTextView.setText("State : " + task.getState().toString());
                numberTextView.setText("Number : " + (getAdapterPosition()+1));
            }
        }
    }


