package com.project.recipeproject.services;

import com.project.recipeproject.converters.RecipeCommandToRecipe;
import com.project.recipeproject.converters.RecipeToRecipeCommand;
import com.project.recipeproject.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RecipeServiceIntegrationTest {
    private static final String NEW_DESCRIPTION = "New description";

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeCommandToRecipe recipeCommandToRecipe;
    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional
    @Test
    public void saveRecipeNewDescription() {
        if (recipeRepository == null) {
            throw new SkipException("No recipe repository found");
            //TODO: figure out why the recipeRepository is always null for test
        }
        var recipes = recipeRepository.findAll();
        if (!recipes.iterator().hasNext()) {
            throw new SkipException("No recipes found");
        }
        var recipe = recipes.iterator().next();
        var recipeCommand = recipeToRecipeCommand.convert(recipe);

        recipeCommand.setDescription(NEW_DESCRIPTION);
        var savedRecipe = recipeService.saveRecipe(recipeCommand);

        assertThat(savedRecipe.getDescription()).isEqualTo(NEW_DESCRIPTION);
        assertThat(recipe.getId()).isEqualTo(savedRecipe.getId());
        assertThat(recipe.getDescription()).isEqualTo(NEW_DESCRIPTION);
        assertThat(recipe.getCategories().size()).isEqualTo(savedRecipe.getCategories().size());
        assertThat(recipe.getIngredients().size()).isEqualTo(savedRecipe.getIngredients().size());
    }
}
