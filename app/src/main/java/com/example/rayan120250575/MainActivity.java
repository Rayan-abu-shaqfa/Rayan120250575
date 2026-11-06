package com.example.rayan120250575;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etGrade, etSearch;
    CheckBox cbSubmitted;
    Button btnAdd;
    RecyclerView recyclerView;
    DBHelper db;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etGrade = findViewById(R.id.etGrade);
        etSearch = findViewById(R.id.etSearch);
        cbSubmitted = findViewById(R.id.cbSubmitted);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);

        db = new DBHelper(this);
        adapter = new TaskAdapter(this, db.getAllTasks());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        btnAdd.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String gradeText = etGrade.getText().toString();


            if (title.isEmpty()) {etTitle.setError("أدخل اسم المهمة");
                return;
            }
            if (gradeText.isEmpty()) {etGrade.setError("أدخل العلامة");
                return;
            }

            double grade = Double.parseDouble(gradeText);
            db.addTask(title, grade, cbSubmitted.isChecked());

            etTitle.setText("");
            etGrade.setText("");
            cbSubmitted.setChecked(false);
            adapter.setList(db.getAllTasks());
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.setList(db.searchTasks(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}