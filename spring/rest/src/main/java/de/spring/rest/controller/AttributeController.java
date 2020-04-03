package de.spring.rest.controller;

import de.spring.persistence.entity.AttributeEntity;
import de.spring.persistence.response.EntityListResponse;
import de.spring.persistence.response.EntityResponse;
import de.spring.service.impl.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.KeyStore.Entry.Attribute;
import java.util.List;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/attribute")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @GetMapping
    public EntityListResponse<AttributeEntity> findAll() {
      List<AttributeEntity> entities = this.attributeService.findAll();
      EntityListResponse<AttributeEntity> response = new EntityListResponse<AttributeEntity>(entities);
      ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
      response.setMetadata(responseMetadata);
      return response;
    }

    @GetMapping(value = "/{id}")
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
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.attributeService.saveOrUpdate(attribute));
      return response;
    }

    @PostMapping
    public EntityResponse<AttributeEntity> update(@RequestBody AttributeEntity attribute) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.attributeService.saveOrUpdate(attribute));
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<AttributeEntity> delete(@PathVariable("id") Long id) {
      AttributeEntity toBeDeleted = this.attributeService.findById(id);
      this.attributeService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      response.setMetadata(responseMetadata);

      return response;
    }

}
