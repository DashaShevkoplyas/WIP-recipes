package com.project.recipeproject.converters;

import com.project.recipeproject.commands.RecipeCommand;
import com.project.recipeproject.model.Category;
import com.project.recipeproject.model.Ingredient;
import com.project.recipeproject.model.Recipe;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final NotesToNotesCommand notesToNotesCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryToCategoryCommand, NotesToNotesCommand notesToNotesCommand, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.notesToNotesCommand = notesToNotesCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setImage(source.getImage());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setNotes(notesToNotesCommand.convert(source.getNotes()));
        final Set<Category> categories = source.getCategories();
        if (categories != null && !categories.isEmpty()) {
            categories.forEach(category -> recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
        }

        final Set<Ingredient> ingredients = source.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            ingredients.forEach(ingredient -> recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
        }

        return recipeCommand;
    }
}
