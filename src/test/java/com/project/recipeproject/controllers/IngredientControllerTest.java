package com.project.recipeproject.controllers;

import com.project.recipeproject.commands.IngredientCommand;
import com.project.recipeproject.commands.RecipeCommand;
import com.project.recipeproject.services.IngredientService;
import com.project.recipeproject.services.RecipeService;
import com.project.recipeproject.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    @Mock
    private RecipeService recipeService;
    @Mock
    private UnitOfMeasureService unitOfMeasureService;
    @Mock
    private IngredientService ingredientService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        IngredientController ingredientController = new IngredientController(unitOfMeasureService, recipeService, ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void listOfIngredients() throws Exception {
        var recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(any())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).findCommandById(any());
    }

    @Test
    public void showIngredient() throws Exception {
        var ingredientCommand = new IngredientCommand();
        when(ingredientService.findByIngredientAndRecipeId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredients/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void addIngredientForm() throws Exception {
        var recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(unitOfMeasureService.listAllUnitOfMeasures()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/edit"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipeService).findCommandById(anyLong());
    }

    @Test
    public void saveOrUpdate() throws Exception {
        var command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);
        when(ingredientService.save(any())).thenReturn(command);

        mockMvc.perform(post("/recipe/2/ingredients")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients/3/show"));
    }
}