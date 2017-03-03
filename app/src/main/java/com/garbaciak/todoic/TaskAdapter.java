package com.garbaciak.todoic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by SGA on 02.03.2017.
 */

public class TaskAdapter extends ArrayAdapter<Task> {

    public static final String EMPTY_TEXT = "";

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.priority);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.dueDateEditText);
        // Populate the data into the template view using the data object

        tvName.setText(task.text);

        if (task.priority != null) {
            tvPriority.setText(task.priority.toString());
            switch (task.priority) {
                case HIGH:
                    tvPriority.setTextColor(Color.RED);
                    break;
                case MEDIUM:
                    tvPriority.setTextColor(Color.parseColor("#F9A825"));
                    break;
                case LOW:
                    tvPriority.setTextColor(Color.BLUE);
                    break;
            }
        } else {
            tvPriority.setText(EMPTY_TEXT);
        }

        if(task.dueDate != null) {
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(task.dueDate);
            tvDueDate.setText(formattedDate);
        } else {
            tvDueDate.setText(EMPTY_TEXT);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}