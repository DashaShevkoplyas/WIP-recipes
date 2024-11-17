package com.project.recipeproject.services;

import com.project.recipeproject.converters.RecipeCommandToRecipe;
import com.project.recipeproject.converters.RecipeToRecipeCommand;
import com.project.recipeproject.model.Recipe;
import com.project.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    private RecipeRepository recipeRepository;

    private RecipeServiceImpl recipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
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

    @Test
    public void getRecipeById() {
        var recipe = new Recipe();
        recipe.setId(1L);
        var recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        var result = recipeService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    //TODO: add tests
}