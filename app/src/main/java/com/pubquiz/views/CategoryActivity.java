package com.pubquiz.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pubquiz.R;
import com.pubquiz.models.Category;
import com.pubquiz.models.CategoryResponse;
import com.pubquiz.network.ApiServiceFactory;
import com.pubquiz.recycler.CategoryAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.ItemClickListener {

    private CategoryAdapter adapter;
    private ArrayList<Category> categories = new ArrayList<>();
    private Call<CategoryResponse> getCategoriesCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        initRecyclerView();
        getCategories();
    }

    // postavljanje adaptera i layout managera
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new CategoryAdapter(categories, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // api poziv
    private void getCategories() {
        getCategoriesCall = ApiServiceFactory.getApiService().getCategories();
        getCategoriesCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories.addAll(response.body().getTriviaCategories());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // callback na odabranu kategoriju iz recyclerview itema
    @Override
    public void onItemClick(Category category) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("category", String.valueOf(category.getId()));
        startActivity(intent);
    }

    // obavazena provjera
    // ako se ne stavi ova provjera, aplikacija puca ako se activity zavrsi a api poziv je u tijeku
    @Override
    protected void onStop() {
        super.onStop();
        if (getCategoriesCall != null) {
            getCategoriesCall.cancel();
        }
    }
}
