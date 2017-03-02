package com.garbaciak.todoic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String EMPTY_TEXT = "";
    private static final String PARAM_TEXT = "text";
    private static final String PARAM_POSITION = "position";
    private static final String TODO_FILE = "todo.txt";

    private final int REQUEST_CODE = 20;

    ArrayList<String> todoItems;
    ArrayAdapter<String> aTodDoAdapter;
    ListView lvItems;
    EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aTodDoAdapter);
        etText = (EditText) findViewById(R.id.etEditText);

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
        todoItems.remove(position);
        aTodDoAdapter.notifyDataSetChanged();
        writeItems();
    }

    private void saveItem(int position, String text) {
        todoItems.set(position, text);
        aTodDoAdapter.notifyDataSetChanged();
        writeItems();
    }

    public void populateArrayItems() {
        readItems();
        aTodDoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, TODO_FILE);
        if (!file.exists()) {
            createNewFile(file);
        }

        try {
            todoItems = new ArrayList<>(FileUtils.readLines(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNewFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
           FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchEditView(String todoText, int position) {

        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra(PARAM_TEXT, todoText);
        i.putExtra(PARAM_POSITION, position);

        startActivityForResult(i, REQUEST_CODE);
    }

    public void onAddItem(View view) {
        aTodDoAdapter.add(etText.getText().toString());
        etText.setText(EMPTY_TEXT);
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            String text = data.getExtras().getString(PARAM_TEXT);
            int position = data.getExtras().getInt(PARAM_POSITION, 0);
            saveItem(position, text);
        }
    }
}
