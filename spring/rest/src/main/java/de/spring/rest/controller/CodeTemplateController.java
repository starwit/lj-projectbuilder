package de.spring.rest.controller;

import de.spring.persistence.entity.CodeTemplateEntity;
import de.spring.persistence.entity.ProjectTemplateEntity;
import de.spring.persistence.response.EntityListResponse;
import de.spring.persistence.response.EntityResponse;
import de.spring.persistence.response.ResponseCode;
import de.spring.persistence.response.ResponseMetadata;
import de.spring.persistence.validation.EntityValidator;
import de.spring.service.impl.CodeTemplateService;
import de.spring.service.impl.ProjectTemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.websocket.server.PathParam;

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

    @GetMapping
    public EntityListResponse<CodeTemplateEntity> findAll() {
      List<CodeTemplateEntity> entities = this.codeTemplateService.findAll();
      EntityListResponse<CodeTemplateEntity> response = new EntityListResponse<CodeTemplateEntity>(entities);
      ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
      response.setMetadata(responseMetadata);
      return response;
    }

    @GetMapping(value = "/{id}")
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
