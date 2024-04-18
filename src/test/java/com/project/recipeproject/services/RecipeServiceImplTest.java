package com.project.recipeproject.services;

import com.project.recipeproject.model.Recipe;
import com.project.recipeproject.repositories.RecipeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    @Mock
    private RecipeRepository recipeRepository;

    private RecipeServiceImpl recipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {
        var recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.findAll()).thenReturn(Set.of(recipe));

        Set<Recipe> recipeSet = recipeService.getRecipes();

        verify(recipeRepository, times(1)).findAll();
        assertThat(recipeSet.size()).isEqualTo(1);
        assertThat(recipeSet).containsExactlyInAnyOrder(recipe);
    }
}