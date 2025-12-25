package com.example.personalcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {

    private TextView tvName, tvStudentId, tvMajor, tvPhone, tvEmail;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        tvName = findViewById(R.id.tv_name);
        tvStudentId = findViewById(R.id.tv_student_id);
        tvMajor = findViewById(R.id.tv_major);
        tvPhone = findViewById(R.id.tv_phone);
        tvEmail = findViewById(R.id.tv_email);
        btnBack = findViewById(R.id.btn_back);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String studentId = intent.getStringExtra("studentId");
        String major = intent.getStringExtra("major");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");

        tvName.setText(getString(R.string.label_name) + name);
        tvStudentId.setText(getString(R.string.label_student_id) + studentId);
        tvMajor.setText(getString(R.string.label_major) + major);
        tvPhone.setText(getString(R.string.label_phone) + phone);
        tvEmail.setText(getString(R.string.label_email) + email);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
