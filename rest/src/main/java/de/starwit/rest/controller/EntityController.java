package de.starwit.rest.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.dto.EntityDto;
import de.starwit.mapper.EntityMapper;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.Domain;
import de.starwit.rest.exception.WrongAppIdException;
import de.starwit.service.impl.DomainService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${rest.base-path}/entities")
public class EntityController {

    final static Logger LOG = LoggerFactory.getLogger(EntityController.class);

    @Autowired
    private DomainService domainService;

    @Autowired
    private EntityMapper mapper;

    @Operation(summary = "Get entity with id")
    @GetMapping(value = "/{entityId}")
    public EntityDto findById(@PathVariable("entityId") Long entityId) {
        Domain domain = domainService.findById(entityId);
        return mapper.convertToDto(domain);
    }

    @Operation(summary = "Delete entity with id")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        domainService.delete(id);
    }

    @Operation(summary = "Create entity in app with appid")
    @PutMapping(value = "/byApp/{appId}")
    public EntityDto save(@PathVariable("appId") Long appId, @Valid @RequestBody EntityDto dto) {
        return update(appId, dto);
    }

    @Operation(summary = "Update entity in app with appid")
    @PostMapping(value = "/byApp/{appId}")
    public EntityDto update(@PathVariable("appId") Long appId, @Valid @RequestBody EntityDto dto) {
        Domain domain = null;
        if (dto.getId() != null) {
            domain = domainService.findByAppAndDomainId(appId, dto.getId());
        }
        App app = new App();
        app.setId(appId);
        domain = mapper.convertToEntity(dto);
        domain.setApp(app);
        domain = domainService.saveOrUpdate(domain);
        return mapper.convertToDto(domain);
    }

    @Operation(summary = "Get all entities in app with appid")
    @GetMapping(value = "/allByApp/{appId}")
    public List<EntityDto> findAllByApp(@PathVariable("appId") Long id) {
        return mapper.convertToDtoList(domainService.findAllDomainsByApp(id));
    }

    @Operation(summary = "Create (or update) entities in app with appid")
    @PutMapping(value = "/allByApp/{appId}")
    public List<EntityDto> saveAllByApp(@PathVariable("appId") Long appId, @Valid @RequestBody List<EntityDto> dtos) {
        return updateAllByApp(appId, dtos);
    }

    @Operation(summary = "Create (or update) entities in app with appid")
    @PostMapping(value = "/allByApp/{appId}")
    public List<EntityDto> updateAllByApp(@PathVariable("appId") Long appId, @Valid @RequestBody List<EntityDto> dtos) {
        for (EntityDto dto : dtos) {
            update(appId, dto);
        }
        return mapper.convertToDtoList(domainService.findAllDomainsByApp(appId));
    }

    @Operation(summary = "Get entity by id in app with appid")
    @GetMapping(value = "/byApp/{appId}/{entityId}")
    public EntityDto findByIdAndApp(@PathVariable("appId") Long appId, @PathVariable("entityId") Long entityId) {
        Domain domain = domainService.findById(entityId);
        if (domain.getApp() != null && domain.getApp().getId() == appId) {
            return mapper.convertToDto(domain);
        } else {
            throw new WrongAppIdException(appId, (domain != null ? domain.getName() : ""));
        }
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
        LOG.info("Entity not found. ", ex.getMessage());
        return new ResponseEntity<Object>("Entity not found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { WrongAppIdException.class })
    public ResponseEntity<Object> handleException(WrongAppIdException ex) {
        LOG.info(ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
