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
@RequestMapping("${rest.base-path}/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> findAll() {
        return this.categoryService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Category findById(@PathVariable("id") Long id) {
        return this.categoryService.findById(id);
    }

    @PutMapping
    public EntityResponse<Category> save(@RequestBody Category category) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.categoryService.saveOrUpdate(category));
      return response;
    }

    @PostMapping
    public EntityResponse<Category> update(@RequestBody Category category) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.categoryService.saveOrUpdate(category));
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<Category> delete(@PathVariable("id") Long id) {
      Category toBeDeleted = this.categoryService.findById(id);
      this.categoryService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      response.setMetadata(responseMetadata);

      return response;
    }

}
