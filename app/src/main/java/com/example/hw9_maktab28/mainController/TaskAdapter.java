package com.example.hw9_maktab28.mainController;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw9_maktab28.R;
import com.example.hw9_maktab28.model.Repository;
import com.example.hw9_maktab28.model.Role;
import com.example.hw9_maktab28.model.Task;
import com.google.android.material.card.MaterialCardView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> implements Serializable, Filterable {

    public static final String TASK_DETAIL_FRAGMENT_TAG = "TaskDetail";


    private List<Task> taskList;
    private List<Task> filteredTaskList;
    private Fragment fragment;
    private RecyclerView recyclerView;
    private Context mContext ;

    public TaskAdapter(Context context , List<Task> taskList, Fragment fragment, RecyclerView recyclerView) {
        this.mContext = context;
        this.taskList = taskList;
        this.filteredTaskList = taskList;
        this.fragment = fragment;
        this.recyclerView = recyclerView;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity activity = (Activity) parent.getContext();
        View view = activity.getLayoutInflater().inflate(R.layout.tasks_row, parent, false);
        return new TaskAdapter.TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = filteredTaskList.get(position);
        holder.bind(task);
    }


    @Override
    public int getItemCount() {
        if (filteredTaskList.size() > 0)
            recyclerView.setBackgroundColor(Color.WHITE);
        else
            recyclerView.setBackgroundResource(R.drawable.ic_custom_background);

        return filteredTaskList == null ? 0 : filteredTaskList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredTaskList = taskList;
                } else {
                    List<Task> filteredList = new ArrayList<>();
                    for (Task row : taskList) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getDescription().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredTaskList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredTaskList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredTaskList = (List<Task>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskNameTextView;
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


        public void bind(final Task task) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(task.getId());
                    taskDetailFragment.setTargetFragment(fragment, 0);
                    taskDetailFragment.show(fragment.getFragmentManager(), TASK_DETAIL_FRAGMENT_TAG);
                }
            });

/*
            if (getAdapterPosition() % 2 == 0)
                ((MaterialCardView) itemView).setCardBackgroundColor(0xFFBEC2FF);
            else
                ((MaterialCardView) itemView).setCardBackgroundColor(0x804250B2);
*/
            taskNameTextView.setText(task.getTitle());
            if(Repository.getInstance(mContext).getLoginedUser().getRole().equals(Role.USER))
                taskStateTextView.setText(task.getState().toString());
            else if(Repository.getInstance(mContext).getLoginedUser().getRole().equals(Role.ADMIN))
                taskStateTextView.setText("User : " + Repository.getInstance(mContext).getUser(task.getUserID()).getUsername());
            letterTextView.setText(String.valueOf(task.getTitle().charAt(0)));
            dateTextView.setText( task.getDate().toString());
        }
    }


}


