package com.example.lazytech_assign;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class BookAdapter extends ArrayAdapter<String> {
    private final List<String> availableBooks;
    private final List<String> bookedBooks;
    private final Map<String, Integer> bookQuantities;
    private final Map<String, Integer> soldQuantities;
    private final LayoutInflater inflater;
    private final boolean isBookedBooksList;

    public BookAdapter(Context context, List<String> availableBooks, List<String> bookedBooks,
                       Map<String, Integer> bookQuantities, Map<String, Integer> soldQuantities,
                       boolean isBookedBooksList) {
        super(context, R.layout.book_item, isBookedBooksList ? bookedBooks : availableBooks);
        this.availableBooks = availableBooks;
        this.bookedBooks = bookedBooks;
        this.bookQuantities = bookQuantities;
        this.soldQuantities = soldQuantities;
        this.isBookedBooksList = isBookedBooksList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_item, parent, false);
        }

        TextView bookTitle = convertView.findViewById(R.id.bookTitle);
        TextView bookQuantity = convertView.findViewById(R.id.bookQuantity);
        Button sellButton = convertView.findViewById(R.id.sellButton);

        String bookName = isBookedBooksList ? bookedBooks.get(position) : availableBooks.get(position);
        bookTitle.setText(bookName);

        if (isBookedBooksList) {
            // Display the sold quantity instead of remaining quantity
            bookQuantity.setText("Sold: " + soldQuantities.getOrDefault(bookName, 0));
            sellButton.setVisibility(View.GONE);
        } else {
            // Display available quantity
            bookQuantity.setText("Available: " + bookQuantities.getOrDefault(bookName, 0));
            sellButton.setVisibility(View.VISIBLE);

            sellButton.setOnClickListener(v -> showSellDialog(position, bookName));
        }

        return convertView;
    }

    private void showSellDialog(int position, String bookName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sell " + bookName);

        View dialogView = inflater.inflate(R.layout.dialog_sell_quantity, null);
        EditText quantityInput = dialogView.findViewById(R.id.quantityInput);
        builder.setView(dialogView);

        builder.setPositiveButton("Sell", (dialog, which) -> {
            String input = quantityInput.getText().toString().trim();
            if (!input.isEmpty()) {
                int sellQuantity = Integer.parseInt(input);
                int available = bookQuantities.getOrDefault(bookName, 0);

                if (sellQuantity > 0 && sellQuantity <= available) {
                    bookQuantities.put(bookName, available - sellQuantity);
                    soldQuantities.put(bookName, soldQuantities.getOrDefault(bookName, 0) + sellQuantity);

                    if (bookQuantities.get(bookName) == 0) {
                        availableBooks.remove(bookName);
                    }

                    bookedBooks.add(bookName);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Invalid quantity!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}
