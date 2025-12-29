package com.example.personalcard.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.personalcard.data.dao.PersonCardDao;
import com.example.personalcard.data.database.AppDatabase;
import com.example.personalcard.data.entity.PersonCard;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PersonCardRepository {

    private final PersonCardDao personCardDao;
    private final ExecutorService executorService;
    private final Handler mainHandler;

    public PersonCardRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        personCardDao = database.personCardDao();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public void insertCard(PersonCard card, OnOperationCompleteListener listener) {
        executorService.execute(() -> {
            long id = personCardDao.insert(card);
            mainHandler.post(() -> {
                if (listener != null) {
                    listener.onSuccess(id);
                }
            });
        });
    }

    public void updateCard(PersonCard card, OnOperationCompleteListener listener) {
        executorService.execute(() -> {
            card.setUpdatedAt(System.currentTimeMillis());
            personCardDao.update(card);
            mainHandler.post(() -> {
                if (listener != null) {
                    listener.onSuccess(card.getId());
                }
            });
        });
    }

    public void deleteCard(PersonCard card, OnOperationCompleteListener listener) {
        executorService.execute(() -> {
            personCardDao.delete(card);
            mainHandler.post(() -> {
                if (listener != null) {
                    listener.onSuccess(0);
                }
            });
        });
    }

    public void getAllCards(OnQueryCompleteListener listener) {
        executorService.execute(() -> {
            List<PersonCard> cards = personCardDao.getAllCards();
            mainHandler.post(() -> {
                if (listener != null) {
                    listener.onQueryComplete(cards);
                }
            });
        });
    }

    public void getCardById(int cardId, OnSingleCardListener listener) {
        executorService.execute(() -> {
            PersonCard card = personCardDao.getCardById(cardId);
            mainHandler.post(() -> {
                if (listener != null) {
                    listener.onCardLoaded(card);
                }
            });
        });
    }

    public void searchCards(String keyword, OnQueryCompleteListener listener) {
        executorService.execute(() -> {
            List<PersonCard> cards = personCardDao.searchCards(keyword);
            mainHandler.post(() -> {
                if (listener != null) {
                    listener.onQueryComplete(cards);
                }
            });
        });
    }

    public interface OnOperationCompleteListener {
        void onSuccess(long id);
    }

    public interface OnQueryCompleteListener {
        void onQueryComplete(List<PersonCard> cards);
    }

    public interface OnSingleCardListener {
        void onCardLoaded(PersonCard card);
    }
}
