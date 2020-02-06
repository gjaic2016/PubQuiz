package com.pubquiz.models;

import java.util.List;

public class CategoryResponse {

    private List<Category> trivia_categories;

    public CategoryResponse() {
    }

    public List<Category> getTriviaCategories() {
        return trivia_categories;
    }
}
