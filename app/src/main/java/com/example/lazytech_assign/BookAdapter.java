package com.example.lazytech_assign;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<String> {
    private final List<String> availableBooks;
    private final List<String> bookedBooks;
    private final LayoutInflater inflater;
    private final boolean isBookedBooksList;

    public BookAdapter(Context context, List<String> availableBooks, List<String> bookedBooks, boolean isBookedBooksList) {
        super(context, R.layout.book_item, isBookedBooksList ? bookedBooks : availableBooks);
        this.availableBooks = availableBooks;
        this.bookedBooks = bookedBooks;
        this.isBookedBooksList = isBookedBooksList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_item, parent, false);
        }

        TextView bookTitle = convertView.findViewById(R.id.bookTitle);
        Button bookButton = convertView.findViewById(R.id.bookButton);

        String bookName = isBookedBooksList ? bookedBooks.get(position) : availableBooks.get(position);
        bookTitle.setText(bookName);

        if (isBookedBooksList) {
            bookButton.setText("Unbook");
            bookButton.setOnClickListener(v -> unbookBook(position, bookName));
        } else {
            bookButton.setText("Book");
            bookButton.setOnClickListener(v -> bookBook(position, bookName));
        }

        return convertView;
    }

    private void bookBook(int position, String bookName) {
        new AlertDialog.Builder(getContext())
                .setTitle("Booking Confirmation")
                .setMessage(bookName + " has been booked.")
                .setPositiveButton("OK", (dialog, which) -> {
                    availableBooks.remove(bookName);
                    bookedBooks.add(bookName);
                    notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void unbookBook(int position, String bookName) {
        new AlertDialog.Builder(getContext())
                .setTitle("Unbooking Confirmation")
                .setMessage(bookName + " is now available again.")
                .setPositiveButton("OK", (dialog, which) -> {
                    bookedBooks.remove(bookName);
                    availableBooks.add(bookName);
                    notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
