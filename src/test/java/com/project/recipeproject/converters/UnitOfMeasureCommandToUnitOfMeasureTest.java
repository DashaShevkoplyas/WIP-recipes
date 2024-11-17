package com.project.recipeproject.converters;

import com.project.recipeproject.commands.UnitOfMeasureCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
//TODO: add tests for other converters
class UnitOfMeasureCommandToUnitOfMeasureTest {
    private static final String DESCRIPTION = "description";
    private static final Long ID = 1L;

    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    public void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void nullParams() {
        assertThat(converter.convert(null)).isNull();
    }

    @Test
    public void emptyParams() {
        var command = new UnitOfMeasureCommand();

        assertThat(converter.convert(command)).isNotNull();
    }

    @Test
    void convert() {
        var command = new UnitOfMeasureCommand();
        command.setId(ID);
        command.setUom(DESCRIPTION);

        var uom = converter.convert(command);

        assertThat(uom).isNotNull();
        assertThat(uom.getId()).isEqualTo(ID);
        assertThat(uom.getUom()).isEqualTo(DESCRIPTION);
    }
}