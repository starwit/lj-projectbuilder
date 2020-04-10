package de.starwit.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.persistence.entity.AttributeEntity;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.persistence.validation.EntityValidator;
import de.starwit.service.impl.AttributeService;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/attribute")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @GetMapping("/query/all")
    public EntityListResponse<AttributeEntity> findAll() {
      List<AttributeEntity> entities = this.attributeService.findAll();
      EntityListResponse<AttributeEntity> response = new EntityListResponse<AttributeEntity>(entities);
      ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
      response.setMetadata(responseMetadata);
      return response;
    }

    @GetMapping(value = "/query/{id}")
    public EntityResponse<AttributeEntity> findById(@PathVariable("id") Long id) {
      AttributeEntity entity = this.attributeService.findById(id);
      EntityResponse<AttributeEntity> rw = new EntityResponse<AttributeEntity>(entity);
      if (entity == null) {
        rw.setMetadata(new ResponseMetadata(ResponseCode.NOT_FOUND, "response.notfound"));
      }
      return rw;
    }

    @PutMapping
    public EntityResponse<AttributeEntity> save(@RequestBody AttributeEntity attribute) {
      EntityResponse<AttributeEntity> response = new EntityResponse<AttributeEntity>();
      response.setResult(this.attributeService.saveOrUpdate(attribute));
      return response;
    }

    @PostMapping
    public EntityResponse<AttributeEntity> update(@RequestBody AttributeEntity attribute) {
      EntityResponse<AttributeEntity> response = new EntityResponse<AttributeEntity>();
      response.setResult(this.attributeService.saveOrUpdate(attribute));
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<AttributeEntity> delete(@PathVariable("id") Long id) {
      AttributeEntity toBeDeleted = this.attributeService.findById(id);
      this.attributeService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      EntityResponse<AttributeEntity> response = new EntityResponse<AttributeEntity>();
      response.setMetadata(responseMetadata);

      return response;
    }

}
