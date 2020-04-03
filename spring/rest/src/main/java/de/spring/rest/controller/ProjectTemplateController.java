package de.spring.rest.controller;

import de.spring.service.impl.ProjectTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/projecttemplate")
public class ProjectTemplateController {

    @Autowired
    private ProjectTemplateService projectTemplateService;


    @GetMapping
    public EntityListResponse<ProjectTemplateEntity> findAll() {
      List<ProjectTemplateEntity> entities = this.projectTemplateService.findAll();
      EntityListResponse<ProjectTemplateEntity> response = new EntityListResponse<ProjectTemplateEntity>(entities);
      ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
      response.setMetadata(responseMetadata);
      return response;
    }

    @GetMapping(value = "/{id}")
    public ProjectTemplateEntity findById(@PathVariable("id") Long id) {
      ProjectTemplateEntity entity = this.projectTemplateService.findById(id);
      EntityResponse<ProjectTemplateEntity> rw = new EntityResponse<ProjectTemplateEntity>(entity);
      if (entity == null) {
        rw.setMetadata(new ResponseMetadata(ResponseCode.NOT_FOUND, "response.notfound"));
      }
      return rw;
    }

    @PutMapping
    public EntityResponse<ProjectTemplateEntity> save(@RequestBody ProjectTemplateEntity projectTemplate) {
      EntityResponse response = validateCodeTemplates(projectTemplate);
      if (response == null) {
        response.setResult(projectTemplateService.saveOrUpdate(projectTemplate));
      }
      return response;
    }

    @PostMapping
    public EntityResponse<ProjectTemplateEntity> update(@RequestBody ProjectTemplateEntity projectTemplate) {
      EntityResponse response = validateCodeTemplates(projectTemplate);
      if (response == null) {
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
