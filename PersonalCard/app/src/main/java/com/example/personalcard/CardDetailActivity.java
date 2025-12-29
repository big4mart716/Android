package com.example.personalcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.personalcard.data.entity.PersonCard;
import com.example.personalcard.data.repository.PersonCardRepository;
import com.example.personalcard.utils.DialogUtils;
import com.example.personalcard.utils.ImageUtils;
import com.google.android.material.button.MaterialButton;

import java.io.File;

public class CardDetailActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private TextView tvName, tvStudentId, tvMajor, tvPhone, tvEmail;
    private MaterialButton btnEdit, btnDelete;
    private PersonCardRepository repository;
    private PersonCard currentCard;
    private int cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);

        repository = new PersonCardRepository(this);
        cardId = getIntent().getIntExtra("card_id", -1);

        initViews();
        loadCardData();
    }

    private void initViews() {
        ivAvatar = findViewById(R.id.iv_avatar);
        tvName = findViewById(R.id.tv_name);
        tvStudentId = findViewById(R.id.tv_student_id);
        tvMajor = findViewById(R.id.tv_major);
        tvPhone = findViewById(R.id.tv_phone);
        tvEmail = findViewById(R.id.tv_email);
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);

        btnEdit.setOnClickListener(v -> editCard());
        btnDelete.setOnClickListener(v -> showDeleteConfirmation());
    }

    private void loadCardData() {
        repository.getCardById(cardId, card -> {
            if (card != null) {
                currentCard = card;
                displayCardInfo(card);
            } else {
                Toast.makeText(this, "名片不存在", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayCardInfo(PersonCard card) {
        tvName.setText(card.getName());
        tvStudentId.setText(card.getStudentId());
        tvMajor.setText(card.getMajor());
        tvPhone.setText(card.getPhone());
        tvEmail.setText(card.getEmail());

        if (card.getAvatarPath() != null && !card.getAvatarPath().isEmpty()) {
            File file = new File(card.getAvatarPath());
            if (file.exists()) {
                Glide.with(this)
                        .load(file)
                        .circleCrop()
                        .placeholder(R.color.md_theme_light_primaryContainer)
                        .into(ivAvatar);
            }
        }
    }

    private void editCard() {
        Intent intent = new Intent(this, EditCardActivity.class);
        intent.putExtra("card_id", cardId);
        startActivity(intent);
    }

    private void showDeleteConfirmation() {
        DialogUtils.showConfirmDialog(this,
                getString(R.string.confirm_delete_title),
                getString(R.string.confirm_delete_message),
                this::deleteCard,
                null);
    }

    private void deleteCard() {
        if (currentCard != null) {
            if (currentCard.getAvatarPath() != null && !currentCard.getAvatarPath().isEmpty()) {
                ImageUtils.deleteImageFile(currentCard.getAvatarPath());
            }

            repository.deleteCard(currentCard, id -> {
                Toast.makeText(this, R.string.toast_delete_success, Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCardData();
    }
}
