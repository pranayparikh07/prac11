package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etId, etName;
    Button btnInsert, btnView, btnUpdate, btnDelete;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        btnInsert = findViewById(R.id.btnInsert);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        db = new DBHelper(this);

        btnInsert.setOnClickListener(v -> {
            if (db.insertData(etName.getText().toString()))
                Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show();
        });

        btnView.setOnClickListener(v -> {
            Cursor c = db.getData();
            if (c.getCount() == 0) {
                Toast.makeText(this, "No Records Found", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder sb = new StringBuilder();
            while (c.moveToNext()) {
                sb.append("ID: ").append(c.getInt(0))
                  .append(" | Name: ").append(c.getString(1))
                  .append("\n");
            }
            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        });

        btnUpdate.setOnClickListener(v -> {
            if (db.updateData(etId.getText().toString(), etName.getText().toString()))
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener(v -> {
            if (db.deleteData(etId.getText().toString()))
                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
        });
    }
}
