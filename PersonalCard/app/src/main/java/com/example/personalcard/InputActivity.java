package com.example.personalcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {

    private EditText etName, etStudentId, etMajor, etPhone, etEmail;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        etName = findViewById(R.id.et_name);
        etStudentId = findViewById(R.id.et_student_id);
        etMajor = findViewById(R.id.et_major);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String studentId = etStudentId.getText().toString().trim();
                String major = etMajor.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                if (name.isEmpty() || studentId.isEmpty() || major.isEmpty() ||
                    phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(InputActivity.this,
                        R.string.toast_fill_all, Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(InputActivity.this, DisplayActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("studentId", studentId);
                intent.putExtra("major", major);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}
