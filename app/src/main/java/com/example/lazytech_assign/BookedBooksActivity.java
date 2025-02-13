package com.example.lazytech_assign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BookedBooksActivity extends AppCompatActivity {
    private ListView bookedListView;
    private BookAdapter bookedBookAdapter;
    private ArrayList<String> bookedBooks, availableBooks;
    private HashMap<String, Integer> bookQuantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booked_books);

        bookedListView = findViewById(R.id.bookedListView);

        // Fetch the correct booked and available books
        bookedBooks = getIntent().getStringArrayListExtra("bookedBooks");
        availableBooks = getIntent().getStringArrayListExtra("availableBooks");
        bookQuantities = (HashMap<String, Integer>) getIntent().getSerializableExtra("bookQuantities");

        // Ensure lists & map are not null
        if (bookedBooks == null) bookedBooks = new ArrayList<>();
        if (availableBooks == null) availableBooks = new ArrayList<>();
        if (bookQuantities == null) bookQuantities = new HashMap<>();

        // Show only booked books in this screen
        bookedBookAdapter = new BookAdapter(this, availableBooks, bookedBooks, bookQuantities, true);
        bookedListView.setAdapter(bookedBookAdapter);
    }

    private void updateBookedBooks() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("updatedBookedBooks", bookedBooks);
        intent.putStringArrayListExtra("updatedAvailableBooks", availableBooks);
        intent.putExtra("updatedBookQuantities", (Serializable) bookQuantities);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onBackPressed() {
        updateBookedBooks();
        super.onBackPressed();
    }
}
