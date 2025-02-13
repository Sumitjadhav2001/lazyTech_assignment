package com.example.lazytech_assign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView bookListView;
    private BookAdapter availableBookAdapter;
    private ArrayList<String> availableBooks, bookedBooks;
    private HashMap<String, Integer> bookQuantities, soldQuantities;
    private Button viewSoldBooksButton;
    private static final int REQUEST_CODE_SOLD_BOOKS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bookListView = findViewById(R.id.bookListView);
        viewSoldBooksButton = findViewById(R.id.viewBookedBooksButton);

        // List of books
        availableBooks = new ArrayList<>(Arrays.asList(
                "Chhava", "Shriman Yogi", "Panipat", "Batatyachi Chaal",
                "Mi Nathuram Godse Boltoy", "Mrutyunjay", "Yayati", "Raja Shivchatrapati"
        ));

        bookedBooks = new ArrayList<>();
        bookQuantities = new HashMap<>();
        soldQuantities = new HashMap<>();

        // Initialize book quantities (each book has 10 copies by default)
        for (String book : availableBooks) {
            bookQuantities.put(book, 10);
            soldQuantities.put(book, 0);
        }

        availableBookAdapter = new BookAdapter(this, availableBooks, bookedBooks, bookQuantities, soldQuantities, false);
        bookListView.setAdapter(availableBookAdapter);

        viewSoldBooksButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookedBooksActivity.class);
            intent.putStringArrayListExtra("bookedBooks", bookedBooks);
            intent.putExtra("soldQuantities", soldQuantities);
            startActivityForResult(intent, REQUEST_CODE_SOLD_BOOKS);
        });

        updateSoldBooksButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SOLD_BOOKS && resultCode == RESULT_OK) {
            bookedBooks.clear();
            bookedBooks.addAll(data.getStringArrayListExtra("updatedBookedBooks"));

            soldQuantities = (HashMap<String, Integer>) data.getSerializableExtra("updatedSoldQuantities");

            availableBookAdapter.notifyDataSetChanged();
            updateSoldBooksButton();
        }
    }

    public void updateSoldBooksButton() {
        if (bookedBooks.isEmpty()) {
            viewSoldBooksButton.setText("View Sold Books");
        } else {
            viewSoldBooksButton.setText("View Sold Books (" + bookedBooks.size() + ")");
        }
    }
}
