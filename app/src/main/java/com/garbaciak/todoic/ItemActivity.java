package com.garbaciak.todoic;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ItemActivity extends AppCompatActivity {

    EditText etText;
    EditText etDueDate;
    Button btnSave;
    private Spinner spinnerPriority;

    int position;
    Date dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        position = getIntent().getIntExtra(MainActivity.PARAM_POSITION, 0);
        String text = getIntent().getStringExtra(MainActivity.PARAM_TEXT);
        dueDate = (Date)getIntent().getSerializableExtra(MainActivity.PARAM_DUE_DATE);
        Priority priority = (Priority)getIntent().getSerializableExtra(MainActivity.PARAM_PRIORITY);

        if (dueDate == null) {
            dueDate = new Date();
        }

        final Calendar dueDateCalendar = new GregorianCalendar();
        dueDateCalendar.setTime(dueDate);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                dueDateCalendar.set(Calendar.YEAR, year);
                dueDateCalendar.set(Calendar.MONTH, monthOfYear);
                dueDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDueDate(dueDateCalendar.getTime());
            }

        };

        etDueDate = (EditText) findViewById(R.id.dueDateEditText);
        updateDueDate(dueDate);
        etDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);
                new DatePickerDialog(ItemActivity.this, date, dueDateCalendar.get(Calendar.YEAR),dueDateCalendar.get(Calendar.MONTH), dueDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etText = (EditText) findViewById(R.id.textEditText);
        etText.setText(text);

        spinnerPriority = (Spinner) findViewById(R.id.prioritySinner);
        spinnerPriority.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard(v);
                return false;
            }
        });
        ArrayAdapter<Priority> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Priority.values());
        spinnerPriority.setAdapter(adapter);
        if (priority != null) {
            int spinnerPosition = adapter.getPosition(priority);
            spinnerPriority.setSelection(spinnerPosition);
        }

        if(etText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        btnSave = (Button) findViewById(R.id.btnSaveItem);

    }

    private static void hideSoftKeyboard(View v) {
        InputMethodManager in = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(v.getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void updateDueDate(Date dueDate) {
        this.dueDate = dueDate;
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(dueDate);
        etDueDate.setText(formattedDate);
    }

    public void onItemSave(View view) {
        Intent data = new Intent();

        data.putExtra(MainActivity.PARAM_POSITION, position);
        data.putExtra(MainActivity.PARAM_TEXT, etText.getText().toString());
        data.putExtra(MainActivity.PARAM_DUE_DATE, this.dueDate);
        data.putExtra(MainActivity.PARAM_PRIORITY, (Priority) spinnerPriority.getSelectedItem());

        setResult(RESULT_OK, data);
        finish();
    }
}
