package com.garbaciak.todoic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {


    private static final String PARAM_TEXT = "text";
    private static final String PARAM_POSITION = "position";

    EditText etText;
    Button btnSave;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String text = getIntent().getStringExtra(PARAM_TEXT);
        position = getIntent().getIntExtra(PARAM_POSITION, 0);

        etText = (EditText) findViewById(R.id.editText);
        etText.setText(text);
        if(etText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        btnSave = (Button) findViewById(R.id.btnSaveItem);

    }

    public void onItemSave(View view) {
        Intent data = new Intent();

        data.putExtra(PARAM_TEXT, etText.getText().toString());
        data.putExtra(PARAM_POSITION, position);

        setResult(RESULT_OK, data);
        finish();
    }
}
