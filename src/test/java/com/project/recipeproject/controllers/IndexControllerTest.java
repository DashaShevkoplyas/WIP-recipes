package com.project.recipeproject.controllers;

import com.project.recipeproject.model.Recipe;
import com.project.recipeproject.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {
    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;
    @Captor
    private ArgumentCaptor<Set<Recipe>> recipesCaptor;

    private IndexController indexController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        indexController = new IndexController(recipeService);
    }

    //the way to test endpoints but in unit tests maner, not an actual call of endpoint and triggering the DB
    //just checking it is callable and correctly responce
    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getIndexPage() {
        var recipes = Set.of(new Recipe());
        when(recipeService.getRecipes()).thenReturn(recipes);

        var result = indexController.getIndexPage(model);

        assertThat(result).isEqualTo("index");
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), recipesCaptor.capture());
        assertThat(recipesCaptor.getValue()).isEqualTo(recipes);
    }
}