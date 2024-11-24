package com.project.recipeproject.converters;

import com.project.recipeproject.commands.NotesCommand;
import com.project.recipeproject.model.Notes;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if (source == null) {
            return new Notes();
        }
        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setNotesText(source.getNotesText());
        return notes;
    }
}
