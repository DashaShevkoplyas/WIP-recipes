package com.project.recipeproject.bootstrap;

import com.project.recipeproject.model.*;
import com.project.recipeproject.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private RecipeRepository recipeRepository;

    DataLoader(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Data loader debug log");

        recipeRepository.saveAll(recipes());

    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    private List<Recipe> recipes() {
        return List.of();
    }
}
