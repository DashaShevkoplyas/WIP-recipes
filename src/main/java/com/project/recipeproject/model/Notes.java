package com.project.recipeproject.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Recipe recipe;
    @Lob
    private String notesText;

}
