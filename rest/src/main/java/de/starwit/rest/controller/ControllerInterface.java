package de.starwit.rest.controller;

import de.starwit.persistence.entity.AbstractEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ControllerInterface<DTO extends AbstractEntity<Long>> {

    @GetMapping
    public List<DTO> findAll();

    @GetMapping(value = "/{id}")
    public DTO findById(@PathVariable("id") Long id);

    @PutMapping
    public DTO save(@Valid @RequestBody DTO dto);

    @PostMapping
    public DTO update(@Valid @RequestBody DTO dto);

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id);
}