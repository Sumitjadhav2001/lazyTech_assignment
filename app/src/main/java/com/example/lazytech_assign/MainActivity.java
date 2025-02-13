package com.example.lazytech_assign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView bookListView;
    private Button viewBookedBooksButton;
    private BookAdapter bookAdapter;
    private ArrayList<String> availableBooks, bookedBooks;
    private HashMap<String, Integer> bookQuantities;

    private static final int REQUEST_CODE_BOOKED_BOOKS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bookListView = findViewById(R.id.bookListView);
        viewBookedBooksButton = findViewById(R.id.viewBookedBooksButton);

        // Initialize book lists and quantities
        availableBooks = new ArrayList<>();
        bookedBooks = new ArrayList<>();
        bookQuantities = new HashMap<>();

        // Sample books with default quantities
        addBook("Android Development", 10);
        addBook("Kotlin for Beginners", 8);
        addBook("Mastering Java", 12);
        addBook("Data Structures & Algorithms", 6);
        addBook("Machine Learning Basics", 7);

        // Set up adapter
        bookAdapter = new BookAdapter(this, availableBooks, bookedBooks, bookQuantities, false);
        bookListView.setAdapter(bookAdapter);

        // Handle navigation to booked books
        viewBookedBooksButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookedBooksActivity.class);
            intent.putStringArrayListExtra("bookedBooks", bookedBooks);
            intent.putStringArrayListExtra("availableBooks", availableBooks);
            intent.putExtra("bookQuantities", bookQuantities);
            startActivityForResult(intent, REQUEST_CODE_BOOKED_BOOKS);
        });
    }

    // Add a book with its quantity
    private void addBook(String bookName, int quantity) {
        availableBooks.add(bookName);
        bookQuantities.put(bookName, quantity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BOOKED_BOOKS && resultCode == RESULT_OK) {
            bookedBooks = data.getStringArrayListExtra("updatedBookedBooks");
            availableBooks = data.getStringArrayListExtra("updatedAvailableBooks");
            bookQuantities = (HashMap<String, Integer>) data.getSerializableExtra("updatedBookQuantities");

            bookAdapter = new BookAdapter(this, availableBooks, bookedBooks, bookQuantities, false);
            bookListView.setAdapter(bookAdapter);
        }
    }
}
