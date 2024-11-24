package com.project.recipeproject.converters;

import com.project.recipeproject.commands.NotesCommand;
import com.project.recipeproject.model.Notes;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {
    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return new NotesCommand();
        }
        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setNotesText(source.getNotesText());
        return notesCommand;
    }
}
