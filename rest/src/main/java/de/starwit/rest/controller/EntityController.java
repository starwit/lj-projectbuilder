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

import de.starwit.allowedroles.IsAdmin;
import de.starwit.allowedroles.IsUser;
import de.starwit.dto.EntityDto;
import de.starwit.mapper.EntityMapper;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.Domain;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.rest.exception.NotificationDto;
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

    @IsAdmin
    @IsUser
    @Operation(summary = "Delete entity with id")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) throws NotificationException {
        domainService.deleteRelationsForAllTargetDomains(id);
        domainService.delete(id);
    }

    @IsAdmin
    @IsUser
    @Operation(summary = "Create entity in app with appid")
    @PostMapping(value = "/by-app/{appId}")
    public EntityDto save(@PathVariable("appId") Long appId, @Valid @RequestBody EntityDto dto) {
        return update(appId, dto);
    }

    @IsAdmin
    @IsUser
    @Operation(summary = "Update entity in app with appid")
    @PutMapping(value = "/by-app/{appId}")
    public EntityDto update(@PathVariable("appId") Long appId, @Valid @RequestBody EntityDto dto) {
        Domain domain = null;
        if (dto.getId() != null) {
            domain = domainService.findByAppAndDomainId(appId, dto.getId());
        }
        App app = new App();
        app.setId(appId);
        domain = mapper.convertToEntity(dto);
        domain = mapper.addParent(domain, app);
        if (domain.getId() != null) {
            domainService.deleteRelationsForAllTargetDomains(domain.getId());
        }
        domain = domainService.saveOrUpdate(domain);
        domainService.createRelationsForAllTargetDomains(appId, domain.getName(), domain.getRelationships());
        return mapper.convertToDto(domain);
    }

    @IsAdmin
    @IsUser
    @Operation(summary = "Update entity in app with appid")
    @PutMapping(value = "/position-by-app/{appId}")
    public EntityDto updatePosition(@PathVariable("appId") Long appId, @Valid @RequestBody EntityDto dto) {
        if (dto.getId() == null) {
            throw new EntityNotFoundException();
        }
        Domain domain = null;
        domain = domainService.findByAppAndDomainId(appId, dto.getId());
        domain.setPosition(dto.getPosition());
        domain = domainService.saveOrUpdate(domain);
        return mapper.convertToDto(domain);
    }

    @Operation(summary = "Get all entities in app with appid")
    @GetMapping(value = "/all-by-app/{appId}")
    public List<EntityDto> findAllByApp(@PathVariable("appId") Long id) {
        return mapper.convertToDtoList(domainService.findAllDomainsByApp(id));
    }

    @IsAdmin
    @IsUser
    @Operation(summary = "Create entities in app with appid")
    @PostMapping(value = "/all-by-app/{appId}")
    public List<EntityDto> saveAllByApp(@PathVariable("appId") Long appId, @Valid @RequestBody List<EntityDto> dtos) {
        return updateAllByApp(appId, dtos);
    }

    @IsAdmin
    @IsUser
    @Operation(summary = "Update entities in app with appid")
    @PutMapping(value = "/all-by-app/{appId}")
    public List<EntityDto> updateAllByApp(@PathVariable("appId") Long appId, @Valid @RequestBody List<EntityDto> dtos) {
        for (EntityDto dto : dtos) {
            update(appId, dto);
        }
        return mapper.convertToDtoList(domainService.findAllDomainsByApp(appId));
    }

    @Operation(summary = "Get entity by id in app with appid")
    @GetMapping(value = "/by-app/{appId}/{entityId}")
    public EntityDto findByIdAndApp(@PathVariable("appId") Long appId, @PathVariable("entityId") Long entityId) {
        Domain domain = domainService.findById(entityId);
        if (domain != null && domain.getApp() != null && domain.getApp().getId().equals(appId)) {
            return mapper.convertToDto(domain);
        } else {
            throw new WrongAppIdException(appId, (domain != null ? domain.getName() : ""));
        }
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
        NotificationDto output = new NotificationDto("error.entity.notfound", "Entity not found.");
        return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { WrongAppIdException.class })
    public ResponseEntity<Object> handleException(WrongAppIdException ex) {
        LOG.info(ex.getMessage());
        NotificationDto output = new NotificationDto("error.wrongAppId", ex.getMessage());
        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
    }
}
