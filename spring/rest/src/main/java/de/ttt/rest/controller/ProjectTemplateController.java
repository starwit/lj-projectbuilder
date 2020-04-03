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
@RequestMapping("${rest.base-path}/projecttemplate")
public class ProjectTemplateController {

    @Autowired
    private ProjectTemplateService projectTemplateService;

    @GetMapping
    public List<ProjectTemplate> findAll() {
        return this.projectTemplateService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ProjectTemplate findById(@PathVariable("id") Long id) {
        return this.projectTemplateService.findById(id);
    }

    @PutMapping
    public EntityResponse<ProjectTemplate> save(@RequestBody ProjectTemplate projectTemplate) {
      EntityResponse response = validateCodeTemplates(projectTemplate);
      if (response == null) {
        response.setResult(projectTemplateService.saveOrUpdate(projectTemplate));
      }
      return response;
    }

    @PostMapping
    public EntityResponse<ProjectTemplate> update(@RequestBody ProjectTemplate projectTemplate) {
      EntityResponse response = validateCodeTemplates(projectTemplate);
      if (response == null) {
        response.setResult(projectTemplateService.saveOrUpdate(projectTemplate));
      }
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<ProjectTemplate> delete(@PathVariable("id") Long id) {
      ProjectTemplate toBeDeleted = this.projectTemplateService.findById(id);
      this.projectTemplateService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      response.setMetadata(responseMetadata);

      return response;
    }

    //private functions
    private EntityResponse<ProjectTemplate> validateCodeTemplates(ProjectTemplate entity) {
      if (entity.getCodeTemplates() != null) {
        for (CodeTemplateEntity codeTemplate : entity.getCodeTemplates()) {
          ResponseMetadata responseMetadata = EntityValidator.validate(codeTemplate);	
          if (responseMetadata.getResponseCode() == ResponseCode.NOT_VALID) {
            EntityResponse<ProjectTemplate> response = new EntityResponse<ProjectTemplate>();
            response.setMetadata(responseMetadata);
            return response;
          }
        }
      }
      
      return null;
    }

}
