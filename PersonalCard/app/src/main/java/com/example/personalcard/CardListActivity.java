package com.example.personalcard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.personalcard.adapter.CardListAdapter;
import com.example.personalcard.data.entity.PersonCard;
import com.example.personalcard.data.repository.PersonCardRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class CardListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardListAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private View emptyView;
    private TextInputEditText searchEdit;
    private PersonCardRepository repository;
    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        repository = new PersonCardRepository(this);

        initViews();
        setupRecyclerView();
        setupSearch();
        loadCards();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        emptyView = findViewById(R.id.empty_view);
        searchEdit = findViewById(R.id.search_edit);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);

        swipeRefresh.setOnRefreshListener(this::loadCards);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditCardActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        adapter = new CardListAdapter();
        adapter.setOnItemClickListener(card -> {
            Intent intent = new Intent(this, CardDetailActivity.class);
            intent.putExtra("card_id", card.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                searchRunnable = () -> {
                    String keyword = s.toString().trim();
                    if (keyword.isEmpty()) {
                        loadCards();
                    } else {
                        searchCards(keyword);
                    }
                };

                searchHandler.postDelayed(searchRunnable, 300);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadCards() {
        repository.getAllCards(cards -> {
            swipeRefresh.setRefreshing(false);
            updateUI(cards);
        });
    }

    private void searchCards(String keyword) {
        repository.searchCards(keyword, this::updateUI);
    }

    private void updateUI(List<PersonCard> cards) {
        adapter.setCardList(cards);
        if (cards == null || cards.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCards();
    }
}
