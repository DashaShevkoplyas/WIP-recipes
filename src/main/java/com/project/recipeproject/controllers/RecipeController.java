package com.project.recipeproject.controllers;

import com.project.recipeproject.commands.RecipeCommand;
import com.project.recipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/show/{id}")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
        return "recipe/show";
    }

    @RequestMapping("/new")
    public String createNewRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/edit";
    }

    @PostMapping("/new")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        var savedCommand = recipeService.saveRecipe(recipeCommand);
        return "redirect:/recipe/show/" + savedCommand.getId();
    }
}
