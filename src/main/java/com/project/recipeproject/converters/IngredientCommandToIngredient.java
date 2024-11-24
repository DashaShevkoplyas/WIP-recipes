package com.project.recipeproject.converters;

import com.project.recipeproject.commands.IngredientCommand;
import com.project.recipeproject.model.Ingredient;
import com.project.recipeproject.model.Recipe;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnit;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnit) {
        this.unitOfMeasureCommandToUnit = unitOfMeasureCommandToUnit;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return new Ingredient();
        }
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());
        ingredient.setUom(unitOfMeasureCommandToUnit.convert(source.getUom()));
        if (source.getRecipeId() != null) {
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.setIngredients(ingredient);
        }

        return ingredient;
    }
}
