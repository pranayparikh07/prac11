package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etId, etName, etAge;
    Button btnAdd, btnView, btnUpdate, btnDelete;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);

        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(v -> {
            boolean inserted = db.insertData(etName.getText().toString(), Integer.parseInt(etAge.getText().toString()));
            showToast(inserted ? "Record Added" : "Insert Failed");
        });

        btnView.setOnClickListener(v -> {
            Cursor res = db.getData();
            if (res.getCount() == 0) {
                showToast("No Records Found");
                return;
            }
            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("ID: ").append(res.getString(0)).append("\n");
                buffer.append("Name: ").append(res.getString(1)).append("\n");
                buffer.append("Age: ").append(res.getString(2)).append("\n\n");
            }
            showToast(buffer.toString());
        });

        btnUpdate.setOnClickListener(v -> {
            boolean updated = db.updateData(etId.getText().toString(),
                    etName.getText().toString(),
                    Integer.parseInt(etAge.getText().toString()));
            showToast(updated ? "Record Updated" : "Update Failed");
        });

        btnDelete.setOnClickListener(v -> {
            int deleted = db.deleteData(etId.getText().toString());
            showToast(deleted > 0 ? "Record Deleted" : "Delete Failed");
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
