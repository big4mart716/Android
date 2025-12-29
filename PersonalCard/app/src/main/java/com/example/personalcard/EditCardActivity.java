package com.example.personalcard;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.personalcard.data.entity.PersonCard;
import com.example.personalcard.data.repository.PersonCardRepository;
import com.example.personalcard.utils.DialogUtils;
import com.example.personalcard.utils.ImageUtils;
import com.example.personalcard.utils.PermissionUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class EditCardActivity extends AppCompatActivity {

    private MaterialCardView cardAvatar;
    private ImageView ivAvatar;
    private TextInputLayout tilName, tilStudentId, tilMajor, tilPhone, tilEmail;
    private TextInputEditText etName, etStudentId, etMajor, etPhone, etEmail;
    private MaterialButton btnSave;

    private PersonCardRepository repository;
    private String currentAvatarPath;
    private int editingCardId = -1;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        repository = new PersonCardRepository(this);

        initViews();
        checkEditMode();
    }

    private void initViews() {
        cardAvatar = findViewById(R.id.card_avatar);
        ivAvatar = findViewById(R.id.iv_avatar);
        tilName = findViewById(R.id.til_name);
        tilStudentId = findViewById(R.id.til_student_id);
        tilMajor = findViewById(R.id.til_major);
        tilPhone = findViewById(R.id.til_phone);
        tilEmail = findViewById(R.id.til_email);
        etName = findViewById(R.id.et_name);
        etStudentId = findViewById(R.id.et_student_id);
        etMajor = findViewById(R.id.et_major);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        btnSave = findViewById(R.id.btn_save);

        cardAvatar.setOnClickListener(v -> showAvatarOptions());
        btnSave.setOnClickListener(v -> saveCard());
    }

    private void checkEditMode() {
        editingCardId = getIntent().getIntExtra("card_id", -1);
        if (editingCardId != -1) {
            isEditMode = true;
            setTitle(R.string.title_edit_card);
            loadCardData();
        } else {
            setTitle(R.string.title_new_card);
        }
    }

    private void loadCardData() {
        repository.getCardById(editingCardId, card -> {
            if (card != null) {
                etName.setText(card.getName());
                etStudentId.setText(card.getStudentId());
                etMajor.setText(card.getMajor());
                etPhone.setText(card.getPhone());
                etEmail.setText(card.getEmail());
                currentAvatarPath = card.getAvatarPath();

                if (currentAvatarPath != null && !currentAvatarPath.isEmpty()) {
                    File file = new File(currentAvatarPath);
                    if (file.exists()) {
                        Glide.with(this).load(file).circleCrop().into(ivAvatar);
                    }
                }
            }
        });
    }

    private void showAvatarOptions() {
        String[] options = {getString(R.string.camera), getString(R.string.gallery)};
        DialogUtils.showChoiceDialog(this, getString(R.string.choose_avatar_source), options,
                which -> {
                    if (which == 0) {
                        openCamera();
                    } else {
                        openGallery();
                    }
                });
    }

    private void openCamera() {
        if (PermissionUtils.checkCameraPermission(this)) {
            ImageUtils.openCamera(this);
        } else {
            PermissionUtils.requestCameraPermission(this);
        }
    }

    private void openGallery() {
        if (PermissionUtils.checkImagePermission(this)) {
            ImageUtils.openGallery(this);
        } else {
            PermissionUtils.requestImagePermission(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == PermissionUtils.REQUEST_CAMERA) {
                ImageUtils.openCamera(this);
            } else if (requestCode == PermissionUtils.REQUEST_READ_IMAGES) {
                ImageUtils.openGallery(this);
            }
        } else {
            Toast.makeText(this, R.string.toast_permission_denied, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {
                Uri resultUri = UCrop.getOutput(data);
                if (resultUri != null) {
                    String savedPath = ImageUtils.compressAndSaveImage(this, resultUri);
                    if (savedPath != null) {
                        if (currentAvatarPath != null && !currentAvatarPath.isEmpty()) {
                            ImageUtils.deleteImageFile(currentAvatarPath);
                        }
                        currentAvatarPath = savedPath;
                        Glide.with(this).load(new File(savedPath)).circleCrop().into(ivAvatar);
                    }
                }
            } else if (requestCode == ImageUtils.REQUEST_CAMERA || requestCode == ImageUtils.REQUEST_GALLERY) {
                Uri sourceUri = null;
                if (requestCode == ImageUtils.REQUEST_CAMERA) {
                    sourceUri = ImageUtils.getCameraImageUri();
                } else if (data != null) {
                    sourceUri = data.getData();
                }

                if (sourceUri != null) {
                    startCrop(sourceUri);
                }
            }
        } else if (resultCode == UCrop.RESULT_ERROR && data != null) {
            Throwable error = UCrop.getError(data);
            Toast.makeText(this, "裁剪失败: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void startCrop(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));

        UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(800, 800)
                .start(this);
    }

    private boolean validateForm() {
        boolean isValid = true;

        String name = etName.getText().toString().trim();
        if (name.isEmpty()) {
            tilName.setError(getString(R.string.error_name_empty));
            isValid = false;
        } else {
            tilName.setError(null);
        }

        String studentId = etStudentId.getText().toString().trim();
        if (studentId.isEmpty()) {
            tilStudentId.setError(getString(R.string.error_student_id_empty));
            isValid = false;
        } else {
            tilStudentId.setError(null);
        }

        String major = etMajor.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.error_email_invalid));
            isValid = false;
        } else {
            tilEmail.setError(null);
        }

        return isValid;
    }

    private void saveCard() {
        if (!validateForm()) {
            return;
        }

        String name = etName.getText().toString().trim();
        String studentId = etStudentId.getText().toString().trim();
        String major = etMajor.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (isEditMode) {
            repository.getCardById(editingCardId, card -> {
                if (card != null) {
                    card.setName(name);
                    card.setStudentId(studentId);
                    card.setMajor(major);
                    card.setPhone(phone);
                    card.setEmail(email);
                    card.setAvatarPath(currentAvatarPath);

                    repository.updateCard(card, id -> {
                        Toast.makeText(this, R.string.toast_save_success, Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        } else {
            PersonCard newCard = new PersonCard(name, studentId, major, phone, email);
            newCard.setAvatarPath(currentAvatarPath);

            repository.insertCard(newCard, id -> {
                Toast.makeText(this, R.string.toast_save_success, Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}
