package de.ttt.rest.controller;

import de.ttt.service.impl.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/attribute")
public class AttributeController {

    private int x;

    @Autowired
    private AttributeService attributeService;

    @GetMapping
    public List<Attribute> findAll() {
        return this.attributeService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Attribute findById(@PathVariable("id") Long id) {
        return this.attributeService.findById(id);
    }

    @PutMapping
    public EntityResponse<Attribute> save(@RequestBody Attribute attribute) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.attributeService.saveOrUpdate(attribute));
      return response;
    }

    @PostMapping
    public EntityResponse<Attribute> update(@RequestBody Attribute attribute) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.attributeService.saveOrUpdate(attribute));
      return response;
    }

    // example path: /api/attribute/1
    @DeleteMapping(value = "/{id}")
    public EntityResponse<Attribute> delete(@PathVariable("id") Long id) {
      Attribute toBeDeleted = this.attributeService.findById(id);
      this.attributeService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      response.setMetadata(responseMetadata);

      return response;
    }

}
