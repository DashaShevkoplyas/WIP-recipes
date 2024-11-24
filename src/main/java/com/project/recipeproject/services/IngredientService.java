package com.project.recipeproject.services;

import com.project.recipeproject.commands.IngredientCommand;
import org.springframework.stereotype.Service;

public interface IngredientService {
    IngredientCommand findByIngredientAndRecipeId(Long recipeId, Long ingredientId);

    IngredientCommand save(IngredientCommand ingredientCommand);
}
