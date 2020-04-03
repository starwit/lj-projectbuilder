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
@RequestMapping("${rest.base-path}/codetemplate")
public class CodeTemplateController {

    @Autowired
    private CodeTemplateService codeTemplateService;

    @GetMapping
    public List<CodeTemplate> findAll() {
        return this.codeTemplateService.findAll();
    }

    @GetMapping(value = "/{id}")
    public CodeTemplate findById(@PathVariable("id") Long id) {
        return this.codeTemplateService.findById(id);
    }

    @PutMapping
    public EntityResponse<CodeTemplate> save(@RequestBody CodeTemplate codeTemplate) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.codeTemplateService.saveOrUpdate(codeTemplate));
      return response;
    }

    @PostMapping
    public EntityResponse<CodeTemplate> update(@RequestBody CodeTemplate codeTemplate) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.codeTemplateService.saveOrUpdate(codeTemplate));
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<CodeTemplate> delete(@PathVariable("id") Long id) {
      CodeTemplate toBeDeleted = this.codeTemplateService.findById(id);
      this.codeTemplateService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      response.setMetadata(responseMetadata);

      return response;
    }

    @GetMapping(value = "/query/byprojecttemplate/{projecttemplateId}")
    public EntityListResponse<CodeTemplate> findAllCodeTemplatesByProjectTemplate(
      @PathParam("projecttemplateId") Long projecttemplateId) {
      ProjectTemplate projectTemplate = projectTemplateService.findById(projecttemplateId);
      if (projectTemplate == null) {
        EntityListResponse<CodeTemplate> response = new EntityListResponse<CodeTemplate>(null);
        ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.NOT_FOUND, "responsecode.notfound");
        response.setMetadata(responseMetadata);
        return response;
      } else {
        List<CodeTemplateEntity> entities = codeTemplateService.findAllCodeTemplatesByProjectTemplate(projecttemplateId);
        EntityListResponse<CodeTemplate> response = new EntityListResponse<CodeTemplate>(entities);
        ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
        response.setMetadata(responseMetadata);
        return response;
      }
    }

}
