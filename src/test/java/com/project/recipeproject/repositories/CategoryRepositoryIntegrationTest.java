package com.project.recipeproject.repositories;

import com.project.recipeproject.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryIntegrationTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByCategoryName() {
        Optional<Category> category = categoryRepository.findByCategoryName("Italian");

        assertTrue(category.isPresent());
        assertEquals("Italian", category.get().getCategoryName());
    }
}