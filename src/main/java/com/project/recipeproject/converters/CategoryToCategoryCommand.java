package com.project.recipeproject.converters;

import com.project.recipeproject.commands.CategoryCommand;
import com.project.recipeproject.model.Category;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {
    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return new CategoryCommand();
        }
        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(source.getId());
        categoryCommand.setCategoryName(source.getCategoryName());
        return categoryCommand;
    }
}
