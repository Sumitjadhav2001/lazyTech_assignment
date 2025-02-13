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

import java.util.HashMap;
import java.util.List;

public class BookAdapter extends ArrayAdapter<String> {
    private final List<String> availableBooks;
    private final List<String> bookedBooks;
    private final HashMap<String, Integer> bookQuantities;
    private final LayoutInflater inflater;
    private final boolean isBookedBooksList;

    public BookAdapter(Context context, List<String> availableBooks, List<String> bookedBooks,
                       HashMap<String, Integer> bookQuantities, boolean isBookedBooksList) {
        super(context, R.layout.book_item, isBookedBooksList ? bookedBooks : availableBooks);
        this.availableBooks = availableBooks;
        this.bookedBooks = bookedBooks;
        this.bookQuantities = bookQuantities;
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
        int quantity = bookQuantities.getOrDefault(bookName, 0);

        bookTitle.setText(bookName);
        bookQuantity.setText("Qty: " + quantity);

        sellButton.setOnClickListener(v -> showSellDialog(position, bookName, quantity));

        return convertView;
    }

    private void showSellDialog(int position, String bookName, int availableQuantity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sell " + bookName);

        View dialogView = inflater.inflate(R.layout.dialog_sell_quantity, null);
        EditText quantityInput = dialogView.findViewById(R.id.quantityInput);
        builder.setView(dialogView);

        builder.setPositiveButton("Sell", (dialog, which) -> {
            int sellQuantity;
            try {
                sellQuantity = Integer.parseInt(quantityInput.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sellQuantity > availableQuantity || sellQuantity <= 0) {
                Toast.makeText(getContext(), "Invalid quantity", Toast.LENGTH_SHORT).show();
            } else {
                bookQuantities.put(bookName, availableQuantity - sellQuantity);
                if (!bookedBooks.contains(bookName)) {
                    bookedBooks.add(bookName);
                }
                notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
