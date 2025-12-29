package com.example.personalcard.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.personalcard.data.entity.PersonCard;

import java.util.List;

@Dao
public interface PersonCardDao {

    @Insert
    long insert(PersonCard card);

    @Update
    void update(PersonCard card);

    @Delete
    void delete(PersonCard card);

    @Query("DELETE FROM person_cards WHERE id = :cardId")
    void deleteById(int cardId);

    @Query("SELECT * FROM person_cards ORDER BY updated_at DESC")
    List<PersonCard> getAllCards();

    @Query("SELECT * FROM person_cards WHERE id = :cardId LIMIT 1")
    PersonCard getCardById(int cardId);

    @Query("SELECT * FROM person_cards WHERE name LIKE '%' || :keyword || '%' OR student_id LIKE '%' || :keyword || '%' ORDER BY updated_at DESC")
    List<PersonCard> searchCards(String keyword);

    @Query("SELECT * FROM person_cards WHERE student_id = :studentId LIMIT 1")
    PersonCard getCardByStudentId(String studentId);

    @Query("SELECT COUNT(*) FROM person_cards")
    int getCardCount();

    @Query("DELETE FROM person_cards")
    void deleteAll();
}
