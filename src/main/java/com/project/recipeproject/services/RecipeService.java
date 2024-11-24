package com.project.recipeproject.services;

import com.project.recipeproject.commands.RecipeCommand;
import com.project.recipeproject.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand findCommandById(Long id);

    RecipeCommand saveRecipe(RecipeCommand recipe);

    void deleteRecipe(Long id);
}
