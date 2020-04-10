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
import de.starwit.service.impl.ProjectTemplateService;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/projecttemplate")
public class ProjectTemplateController {

    @Autowired
    private ProjectTemplateService projectTemplateService;


    @GetMapping("/query/all")
    public EntityListResponse<ProjectTemplateEntity> findAll() {
      List<ProjectTemplateEntity> entities = this.projectTemplateService.findAll();
      EntityListResponse<ProjectTemplateEntity> response = new EntityListResponse<ProjectTemplateEntity>(entities);
      ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
      response.setMetadata(responseMetadata);
      return response;
    }

    @GetMapping(value = "/query/{id}")
    public EntityResponse<ProjectTemplateEntity> findById(@PathVariable("id") Long id) {
      ProjectTemplateEntity entity = this.projectTemplateService.findById(id);
      EntityResponse<ProjectTemplateEntity> rw = new EntityResponse<ProjectTemplateEntity>(entity);
      if (entity == null) {
        rw.setMetadata(new ResponseMetadata(ResponseCode.NOT_FOUND, "response.notfound"));
      }
      return rw;
    }

    @PutMapping
    public EntityResponse<ProjectTemplateEntity> save(@RequestBody ProjectTemplateEntity projectTemplate) {
      EntityResponse<ProjectTemplateEntity> response = validateCodeTemplates(projectTemplate);
      if (response != null) {
        response.setResult(projectTemplateService.saveOrUpdate(projectTemplate));
      }
      return response;
    }

    @PostMapping
    public EntityResponse<ProjectTemplateEntity> update(@RequestBody ProjectTemplateEntity projectTemplate) {
      EntityResponse<ProjectTemplateEntity> response = validateCodeTemplates(projectTemplate);
      if (response != null) {
        response.setResult(projectTemplateService.saveOrUpdate(projectTemplate));
      }
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<ProjectTemplateEntity> delete(@PathVariable("id") Long id) {
      ProjectTemplateEntity toBeDeleted = this.projectTemplateService.findById(id);
      this.projectTemplateService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      EntityResponse<ProjectTemplateEntity> response = new EntityResponse<ProjectTemplateEntity>();
      response.setMetadata(responseMetadata);

      return response;
    }

    //private functions
    private EntityResponse<ProjectTemplateEntity> validateCodeTemplates(ProjectTemplateEntity entity) {
      if (entity.getCodeTemplates() != null) {
        for (CodeTemplateEntity codeTemplate : entity.getCodeTemplates()) {
          ResponseMetadata responseMetadata = EntityValidator.validate(codeTemplate);	
          if (responseMetadata.getResponseCode() == ResponseCode.NOT_VALID) {
            EntityResponse<ProjectTemplateEntity> response = new EntityResponse<ProjectTemplateEntity>();
            response.setMetadata(responseMetadata);
            return response;
          }
        }
      }
      
      return null;
    }

}
