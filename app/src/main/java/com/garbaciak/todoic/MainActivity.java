package com.garbaciak.todoic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String PARAM_ID = "id";
    public static final String PARAM_TEXT = "text";
    public static final String PARAM_PRIORITY = "priority";
    public static final String PARAM_DUE_DATE = "due_date";
    public static final String PARAM_POSITION = "position";

    private final int REQUEST_CODE = 20;

    ArrayList<Task> todoItems;
    TaskAdapter aTodDoAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aTodDoAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItem(position);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchEditView(todoItems.get(position), position);
            }
        });
    }

    private void removeItem(int position) {
        todoItems.get(position).delete();
//        todoItems.remove(position);
//        aTodDoAdapter.notifyDataSetChanged();
        updateTodoList();
    }


    public void populateArrayItems() {
        todoItems = new ArrayList<>(SQLite.select().
                from(Task.class).queryList());
        aTodDoAdapter = new TaskAdapter(this, todoItems);
    }

    private void updateTodoList() {
        todoItems.clear();
        todoItems.addAll(new ArrayList<>(SQLite.select().
                from(Task.class).queryList()));
        aTodDoAdapter.notifyDataSetChanged();
    }

    public void launchEditView(Task task, int position) {

        Intent i = new Intent(MainActivity.this, ItemActivity.class);
        i.putExtra(PARAM_POSITION, position);
        i.putExtra(PARAM_ID, task.id);
        i.putExtra(PARAM_TEXT, task.text);
        i.putExtra(PARAM_DUE_DATE, task.dueDate);
        i.putExtra(PARAM_PRIORITY, task.priority);

        startActivityForResult(i, REQUEST_CODE);
    }

    public void onAddItem(View view) {

//        Task task = new Task();
//        task.text = etText.getText().toString();
//        task.dueDate = new Date();
//        task.priority= Priority.HIGH;
//        task.save();

//        aTodDoAdapter.add(task);
//        etText.setText(EMPTY_TEXT);
        launchEditView(new Task(), -1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            int position = data.getIntExtra(PARAM_POSITION, 0);
            Task task;
            if (position == -1) {
                task = new Task();
            } else {
                task = todoItems.get(position);
            }
//            task.id = getIntent().getIntExtra(MainActivity.PARAM_ID, 0);
            task.text = data.getStringExtra(PARAM_TEXT);
            task.dueDate = (Date)data.getSerializableExtra(PARAM_DUE_DATE);
            task.priority= (Priority)data.getSerializableExtra(PARAM_PRIORITY);

            task.save();

            updateTodoList();
        }
    }
}
