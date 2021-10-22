package de.starwit.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.dto.EntityDto;
import de.starwit.mapper.EntityMapper;
import de.starwit.persistence.entity.Domain;
import de.starwit.service.impl.DomainService;

@RestController
@RequestMapping("${rest.base-path}/entities")
public class EntityController implements ControllerInterface<EntityDto> {

    final static Logger LOG = LoggerFactory.getLogger(EntityController.class);

	@Autowired
	private DomainService domainService;

	@Autowired
	private EntityMapper mapper;

    @Override
    public List<EntityDto> findAll() {
        return mapper.convertToDtoList(domainService.findAll());
    }

    @Override
    public EntityDto findById(Long id) {
        return mapper.convertToDto(domainService.findById(id));
    }

    @Override
    public EntityDto save(EntityDto dto) {
        return update(dto);
    }

    @Override
    public EntityDto update(EntityDto dto) {
        Domain domain = mapper.convertToEntity(dto);
		domain = domainService.saveOrUpdate(domain);
		return mapper.convertToDto(domain);
    }

    @Override
    public void delete(Long id) {
        domainService.delete(id);
    }
}
