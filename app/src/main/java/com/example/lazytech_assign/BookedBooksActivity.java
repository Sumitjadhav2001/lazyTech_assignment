package com.example.lazytech_assign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BookedBooksActivity extends AppCompatActivity {
    private ListView bookedListView;
    private BookAdapter bookedBookAdapter;
    private ArrayList<String> bookedBooks, availableBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booked_books);

        bookedListView = findViewById(R.id.bookedListView);

        // Fetch the correct booked and available books
        bookedBooks = getIntent().getStringArrayListExtra("bookedBooks");
        availableBooks = getIntent().getStringArrayListExtra("availableBooks");

        // Ensure it's a separate list instance
        if (bookedBooks == null) bookedBooks = new ArrayList<>();
        if (availableBooks == null) availableBooks = new ArrayList<>();

        // Show only booked books in this screen
        bookedBookAdapter = new BookAdapter(this, availableBooks, bookedBooks, true);
        bookedListView.setAdapter(bookedBookAdapter);
    }

    public void updateAvailableBooks() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("updatedBookedBooks", bookedBooks);
        intent.putStringArrayListExtra("updatedAvailableBooks", availableBooks);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onBackPressed() {
        updateAvailableBooks();
        super.onBackPressed();
    }
}
