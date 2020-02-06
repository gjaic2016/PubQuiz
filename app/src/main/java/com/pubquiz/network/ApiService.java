package com.pubquiz.network;

import com.pubquiz.models.CategoryResponse;
import com.pubquiz.models.QuestionsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api_category.php")
    Call<CategoryResponse> getCategories();

    @GET("api.php?amount=3&difficulty=easy&type=multiple")
    Call<QuestionsResponse> getQuestions(@Query("category") String categoryId);
}
