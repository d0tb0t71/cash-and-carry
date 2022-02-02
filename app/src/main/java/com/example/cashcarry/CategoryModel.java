package com.example.cashcarry;

public class CategoryModel {

    int categoryLogo;
    String categoryName;

    public CategoryModel(int categoryLogo, String categoryName) {
        this.categoryLogo = categoryLogo;
        this.categoryName = categoryName;
    }

    public int getCategoryLogo() {
        return categoryLogo;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
