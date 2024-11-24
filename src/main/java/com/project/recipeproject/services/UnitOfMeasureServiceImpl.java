package com.project.recipeproject.services;

import com.project.recipeproject.commands.UnitOfMeasureCommand;
import com.project.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.project.recipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUnitOfMeasures() {
        Set<UnitOfMeasureCommand> uomList = new LinkedHashSet<>();
        unitOfMeasureRepository.findAll().iterator().forEachRemaining(uom -> {
            var command = unitOfMeasureToUnitOfMeasureCommand.convert(uom);
            uomList.add(command);
        });
        return uomList;
    }
}
