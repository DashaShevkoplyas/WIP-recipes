package com.project.recipeproject.converters;

import com.project.recipeproject.commands.CategoryCommand;
import com.project.recipeproject.model.Category;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Nullable
    @Synchronized
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return new Category();
        }
        final Category category = new Category();
        category.setId(source.getId());
        category.setCategoryName(source.getCategoryName());
        return category;
    }
}
