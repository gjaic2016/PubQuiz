package com.pubquiz.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pubquiz.R;
import com.pubquiz.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    public interface ItemClickListener {
        void onItemClick(Category category);
    }

    private ArrayList<Category> categories;
    private CategoryAdapter.ItemClickListener listener;

    public CategoryAdapter(ArrayList<Category> categories, CategoryAdapter.ItemClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CategoryAdapter.CategoryHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_category, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryHolder categoryHolder, int i) {
        final Category category = categories.get(i);
        TextView categoryName = categoryHolder.itemView.findViewById(R.id.category_name);
        categoryName.setText(category.getName());
        categoryHolder.itemView.setOnClickListener(view -> listener.onItemClick(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        CategoryHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
