package com.project.recipeproject.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest {
    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        var id = 1L;

        category.setId(id);

        assertThat(category.getId()).isEqualTo(id);
    }

    @Test
    void getCategoryName() {
        var categoryName = "categoryName";

        category.setCategoryName(categoryName);

        assertThat(category.getCategoryName()).isEqualTo(categoryName);
    }

    @Test
    void getRecipes() {
        var recipe = new Recipe();
        recipe.setId(4L);

        category.setRecipes(Set.of(recipe));

        assertThat(category.getRecipes()).containsExactlyInAnyOrder(recipe);
    }
}