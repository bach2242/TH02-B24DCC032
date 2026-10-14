package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter(@NonNull Context context, @NonNull List<Expense> expenses) {
        super(context, 0, expenses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_expense, parent, false);
        }

        Expense expense = getItem(position);

        TextView tvName = convertView.findViewById(R.id.tvItemName);
        TextView tvAmount = convertView.findViewById(R.id.tvItemAmount);
        TextView tvCategory = convertView.findViewById(R.id.tvItemCategory);
        TextView tvDate = convertView.findViewById(R.id.tvItemDate);

        if (expense != null) {
            tvName.setText(expense.getName());
            tvAmount.setText(Expense.formatCurrency(expense.getAmount()));
            tvCategory.setText(expense.getCategory());
            tvDate.setText(expense.getDate());
        }

        return convertView;
    }
}