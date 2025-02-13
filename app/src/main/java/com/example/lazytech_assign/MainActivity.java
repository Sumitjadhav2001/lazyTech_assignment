package com.example.lazytech_assign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView bookListView;
    private BookAdapter availableBookAdapter;
    private ArrayList<String> availableBooks, bookedBooks;
    private Button viewBookedBooksButton;
    private static final int REQUEST_CODE_BOOKED_BOOKS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bookListView = findViewById(R.id.bookListView);
        viewBookedBooksButton = findViewById(R.id.viewBookedBooksButton);

        availableBooks = new ArrayList<>(Arrays.asList(
                "Chhava", "Shriman Yogi", "Panipat", "Batatyachi Chaal",
                "Mi Nathuram Godse Boltoy", "Mrutyunjay", "Yayati", "Raja Shivchatrapati",
                "Swami", "Maza Pravas", "Sambhaji", "Peshwai", "Duniyadari",
                "Shala", "Garambicha Bapu", "Kosala", "Agnipankh", "Mi Durga Boltey",
                "Rarang Dhang", "Zadazadati", "Jhadajhadati", "Partner",
                "Hindu", "Gharandaaj", "Shodh", "Athavaniche Pakshi",
                "Astitva", "Sambhaji Maharaj", "Ajinkyatara", "Ek Hoti Rajkanya"
        ));

        bookedBooks = new ArrayList<>();

        availableBookAdapter = new BookAdapter(this, availableBooks, bookedBooks, false);
        bookListView.setAdapter(availableBookAdapter);



        viewBookedBooksButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookedBooksActivity.class);
            intent.putStringArrayListExtra("bookedBooks", bookedBooks);
            intent.putStringArrayListExtra("availableBooks", availableBooks);
            startActivityForResult(intent, REQUEST_CODE_BOOKED_BOOKS);
        });

        updateBookedBooksButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BOOKED_BOOKS && resultCode == RESULT_OK) {
            bookedBooks.clear();
            bookedBooks.addAll(data.getStringArrayListExtra("updatedBookedBooks"));

            availableBooks.clear();
            availableBooks.addAll(data.getStringArrayListExtra("updatedAvailableBooks"));

            availableBookAdapter.notifyDataSetChanged();
            updateBookedBooksButton();
        }
    }

    public void updateBookedBooksButton() {
        if (bookedBooks.isEmpty()) {
            viewBookedBooksButton.setText("View Booked Books");
        } else {
            viewBookedBooksButton.setText("View Booked Books (" + bookedBooks.size() + ")");
        }
    }
}
