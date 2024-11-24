package com.project.recipeproject.services;

import com.project.recipeproject.commands.RecipeCommand;
import com.project.recipeproject.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand findCommandById(Long id);

    RecipeCommand saveRecipe(RecipeCommand recipe);
}
