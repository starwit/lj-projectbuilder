package de.spring.rest.controller;

import de.spring.persistence.entity.CategoryEntity;
import de.spring.persistence.response.EntityListResponse;
import de.spring.persistence.response.EntityResponse;
import de.spring.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public EntityListResponse<CategoryEntity> findAll() {
      List<CategoryEntity> entities = this.categoryService.findAll();
      EntityListResponse<CategoryEntity> response = new EntityListResponse<CategoryEntity>(entities);
      ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
      response.setMetadata(responseMetadata);
      return response;
    }

    @GetMapping(value = "/{id}")
    public EntityResponse<CategoryEntity> findById(@PathVariable("id") Long id) {
      CategoryEntity entity = this.categoryService.findById(id);
      EntityResponse<CategoryEntity> rw = new EntityResponse<CategoryEntity>(entity);
      if (entity == null) {
        rw.setMetadata(new ResponseMetadata(ResponseCode.NOT_FOUND, "response.notfound"));
      }
      return rw;
    }

    @PutMapping
    public EntityResponse<CategoryEntity> save(@RequestBody CategoryEntity category) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.categoryService.saveOrUpdate(category));
      return response;
    }

    @PostMapping
    public EntityResponse<CategoryEntity> update(@RequestBody CategoryEntity category) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.categoryService.saveOrUpdate(category));
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<CategoryEntity> delete(@PathVariable("id") Long id) {
      CategoryEntity toBeDeleted = this.categoryService.findById(id);
      this.categoryService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      response.setMetadata(responseMetadata);

      return response;
    }

}
