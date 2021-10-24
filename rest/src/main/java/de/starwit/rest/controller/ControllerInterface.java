package de.starwit.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import de.starwit.persistence.entity.AbstractEntity;

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
