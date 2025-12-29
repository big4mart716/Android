package com.example.personalcard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.personalcard.R;
import com.example.personalcard.data.entity.PersonCard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    private List<PersonCard> cardList = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PersonCard card);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setCardList(List<PersonCard> cards) {
        this.cardList = cards != null ? cards : new ArrayList<>();
        notifyDataSetChanged();
    }

    public PersonCard getCardAt(int position) {
        return cardList.get(position);
    }

    public void removeCard(int position) {
        cardList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        PersonCard card = cardList.get(position);
        holder.bind(card);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivAvatar;
        private final TextView tvName;
        private final TextView tvStudentId;
        private final TextView tvMajor;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStudentId = itemView.findViewById(R.id.tv_student_id);
            tvMajor = itemView.findViewById(R.id.tv_major);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(cardList.get(position));
                }
            });
        }

        public void bind(PersonCard card) {
            tvName.setText(card.getName());
            tvStudentId.setText("学号: " + card.getStudentId());
            tvMajor.setText("专业: " + card.getMajor());

            if (card.getAvatarPath() != null && !card.getAvatarPath().isEmpty()) {
                File file = new File(card.getAvatarPath());
                if (file.exists()) {
                    Glide.with(itemView.getContext())
                            .load(file)
                            .circleCrop()
                            .placeholder(R.color.md_theme_light_primaryContainer)
                            .into(ivAvatar);
                } else {
                    ivAvatar.setImageResource(R.color.md_theme_light_primaryContainer);
                }
            } else {
                ivAvatar.setImageResource(R.color.md_theme_light_primaryContainer);
            }
        }
    }
}
