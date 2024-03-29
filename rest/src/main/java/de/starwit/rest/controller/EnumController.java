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
import de.starwit.dto.EnumDto;
import de.starwit.mapper.EnumMapper;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.EnumDef;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.rest.exception.NotificationDto;
import de.starwit.rest.exception.WrongAppIdException;
import de.starwit.service.impl.AppService;
import de.starwit.service.impl.EnumDefService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${rest.base-path}/enums")
public class EnumController {

    static final Logger LOG = LoggerFactory.getLogger(EnumController.class);

    @Autowired
    private EnumDefService enumDefService;

    @Autowired
    private AppService appService;

    @Autowired
    private EnumMapper enumMapper;

    @Operation(summary = "Get all enums in app with appid")
    @GetMapping(value = "/all-by-app/{appId}")
    public List<EnumDto> findAllByAppId(@PathVariable("appId") Long appId) {
        List<EnumDef> enums = enumDefService.findAllEnumsByApp(appId);
        return enumMapper.convertToDtoList(enums);
    }

    @IsAdmin
    @IsUser
    @Operation(summary = "Create enum in app with appid")
    @PostMapping(value = "/by-app/{appId}")
    public EnumDto save(@PathVariable("appId") Long appId, @Valid @RequestBody EnumDto enumDto) {
        return update(appId, enumDto);
    }

    @IsAdmin
    @IsUser
    @Operation(summary = "Update entity in app with appid")
    @PutMapping(value = "/by-app/{appId}")
    public EnumDto update(@PathVariable("appId") Long appId, @Valid @RequestBody EnumDto enumDto) {
        EnumDef enumDef = enumMapper.convertToEntity(enumDto);
        App app = appService.findById(appId);
        enumDef.setApp(app);
        enumDef = enumDefService.saveOrUpdate(enumDef);
        return enumMapper.convertToDto(enumDef);
    }

    @IsAdmin
    @IsUser
    @Operation(summary = "Delete enum with id")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) throws NotificationException {
        enumDefService.delete(id);
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<NotificationDto> handleException(EntityNotFoundException ex) {
        NotificationDto output = new NotificationDto("error.entity.notfound", "Enum not found.");
        return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { WrongAppIdException.class })
    public ResponseEntity<NotificationDto> handleException(WrongAppIdException ex) {
        LOG.info(ex.getMessage());
        NotificationDto output = new NotificationDto("error.wrongAppId", ex.getMessage());
        return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
    }
}
