package com.project.recipeproject.services;

import com.project.recipeproject.commands.IngredientCommand;
import com.project.recipeproject.converters.IngredientCommandToIngredient;
import com.project.recipeproject.converters.IngredientToIngredientCommand;
import com.project.recipeproject.model.Ingredient;
import com.project.recipeproject.repositories.RecipeRepository;
import com.project.recipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByIngredientAndRecipeId(Long recipeId, Long ingredientId) {
        var recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()) {
            throw new NoSuchElementException("Recipe not found");
        }
        return recipe.get().getIngredients().stream()
                .filter(Objects::nonNull)
                .filter(ingredient -> ingredientId.equals(ingredient.getId()))
                .map(ingredientToIngredientCommand::convert)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    public IngredientCommand save(IngredientCommand ingredientCommand) {
        var recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (recipeOptional.isEmpty()) {
            log.error("Recipe not found for id: {}", ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }

        var recipe = recipeOptional.get();
        var ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        ingredient.setRecipe(recipe);
        recipe.setIngredients(ingredient);
        var savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                .findFirst();
        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }
}
