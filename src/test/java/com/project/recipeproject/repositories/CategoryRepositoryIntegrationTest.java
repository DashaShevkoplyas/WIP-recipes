package com.project.recipeproject.repositories;

import com.project.recipeproject.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=always")
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