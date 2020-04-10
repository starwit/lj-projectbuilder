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

import de.starwit.persistence.entity.CodeTemplateEntity;
import de.starwit.persistence.entity.ProjectTemplateEntity;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.persistence.validation.EntityValidator;
import de.starwit.service.impl.CodeTemplateService;
import de.starwit.service.impl.ProjectTemplateService;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/codetemplate")
public class CodeTemplateController {

    @Autowired
    private CodeTemplateService codeTemplateService;

    @Autowired
    private ProjectTemplateService projectTemplateService;

    @GetMapping("/query/all")
    public EntityListResponse<CodeTemplateEntity> findAll() {
      List<CodeTemplateEntity> entities = this.codeTemplateService.findAll();
      EntityListResponse<CodeTemplateEntity> response = new EntityListResponse<CodeTemplateEntity>(entities);
      ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
      response.setMetadata(responseMetadata);
      return response;
    }

    @GetMapping(value = "/query/{id}")
    public EntityResponse<CodeTemplateEntity> findById(@PathVariable("id") Long id) {
      CodeTemplateEntity entity = this.codeTemplateService.findById(id);
      EntityResponse<CodeTemplateEntity> rw = new EntityResponse<CodeTemplateEntity>(entity);
      if (entity == null) {
        rw.setMetadata(new ResponseMetadata(ResponseCode.NOT_FOUND, "response.notfound"));
      }
      return rw;
    }

    @PutMapping
    public EntityResponse<CodeTemplateEntity> save(@RequestBody CodeTemplateEntity codeTemplate) {
      EntityResponse<CodeTemplateEntity> response = new EntityResponse<CodeTemplateEntity>();
      response.setResult(this.codeTemplateService.saveOrUpdate(codeTemplate));
      return response;
    }

    @PostMapping
    public EntityResponse<CodeTemplateEntity> update(@RequestBody CodeTemplateEntity codeTemplate) {
      EntityResponse<CodeTemplateEntity> response = new EntityResponse<CodeTemplateEntity>();
      response.setResult(this.codeTemplateService.saveOrUpdate(codeTemplate));
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<CodeTemplateEntity> delete(@PathVariable("id") Long id) {
      CodeTemplateEntity toBeDeleted = this.codeTemplateService.findById(id);
      this.codeTemplateService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      EntityResponse<CodeTemplateEntity> response = new EntityResponse<CodeTemplateEntity>();
      response.setMetadata(responseMetadata);

      return response;
    }

    @GetMapping(value = "/query/byprojecttemplate/{projecttemplateId}")
    public EntityListResponse<CodeTemplateEntity> findAllCodeTemplatesByProjectTemplate(
      @PathVariable("projecttemplateId") Long projecttemplateId) {
      ProjectTemplateEntity projectTemplate = projectTemplateService.findById(projecttemplateId);
      if (projectTemplate == null) {
        EntityListResponse<CodeTemplateEntity> response = new EntityListResponse<CodeTemplateEntity>(null);
        ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.NOT_FOUND, "responsecode.notfound");
        response.setMetadata(responseMetadata);
        return response;
      } else {
        List<CodeTemplateEntity> entities = codeTemplateService.findAllCodeTemplatesByProjectTemplate(projecttemplateId);
        EntityListResponse<CodeTemplateEntity> response = new EntityListResponse<CodeTemplateEntity>(entities);
        ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
        response.setMetadata(responseMetadata);
        return response;
      }
    }

}
