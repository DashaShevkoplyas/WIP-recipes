package com.project.recipeproject.services;

import com.project.recipeproject.commands.RecipeCommand;
import com.project.recipeproject.converters.RecipeCommandToRecipe;
import com.project.recipeproject.converters.RecipeToRecipeCommand;
import com.project.recipeproject.model.Recipe;
import com.project.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Service debug log"); //comes together with slf4j annotation as a login tool

        Set<Recipe> recipes = new LinkedHashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipe(RecipeCommand recipeCommand) {
        var recipe = recipeCommandToRecipe.convert(recipeCommand);
        var savedRecipe = recipeRepository.save(recipe);
        log.debug("Saved recipe: {}", savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }
}
