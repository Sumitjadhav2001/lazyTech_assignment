package com.example.lazytech_assign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class BookedBooksActivity extends AppCompatActivity {
    private ListView bookedListView;
    private BookAdapter bookedBookAdapter;
    private ArrayList<String> bookedBooks;
    private HashMap<String, Integer> soldQuantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booked_books);

        bookedListView = findViewById(R.id.bookedListView);

        bookedBooks = getIntent().getStringArrayListExtra("bookedBooks");
        soldQuantities = (HashMap<String, Integer>) getIntent().getSerializableExtra("soldQuantities");

        if (bookedBooks == null) bookedBooks = new ArrayList<>();
        if (soldQuantities == null) soldQuantities = new HashMap<>();

        bookedBookAdapter = new BookAdapter(this, new ArrayList<>(), bookedBooks, new HashMap<>(), soldQuantities, true);
        bookedListView.setAdapter(bookedBookAdapter);
    }
}
