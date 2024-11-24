package com.project.recipeproject.controllers;

import com.project.recipeproject.commands.IngredientCommand;
import com.project.recipeproject.commands.UnitOfMeasureCommand;
import com.project.recipeproject.services.IngredientService;
import com.project.recipeproject.services.RecipeService;
import com.project.recipeproject.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientController {
    private final UnitOfMeasureService uomService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(UnitOfMeasureService uomService, RecipeService recipeService, IngredientService ingredientService) {
        this.uomService = uomService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}/ingredients")
    public String getIngredients(@PathVariable String id, Model model) {
        log.debug("Getting ingredients for {}", id);
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
        return "recipe/ingredients/list";
    }

    @GetMapping("/{recipeId}/ingredients/{ingredientId}/show")
    public String showIngredients(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        log.debug("Getting ingredients list {} for {}", ingredientId, recipeId);
        var ingredient = ingredientService.findByIngredientAndRecipeId(Long.parseLong(recipeId), Long.parseLong(ingredientId));
        model.addAttribute("ingredient", ingredient);
        return "recipe/ingredients/show";
    }

    @GetMapping("{recipeId}/ingredients/new")
    public String createIngredient(@PathVariable String recipeId, Model model) {
        var id = Long.parseLong(recipeId);
        var recipeCommand = recipeService.findCommandById(id);
        if (recipeCommand == null) {
            throw new NoSuchElementException("Recipe not found");
        }
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(id);
        model.addAttribute("ingredient", ingredientCommand);
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("uomList", uomService.listAllUnitOfMeasures());
        return "recipe/ingredients/edit";
    }

    @PostMapping
    @RequestMapping("/{recipeId}/ingredients/change")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.save(command);
        log.debug("Saved recipe id:{}", savedCommand.getRecipeId());
        log.debug("Saved ingredient id:{}", savedCommand.getId());
        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredients/" + savedCommand.getId() + "/show";
    }
}
