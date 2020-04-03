package de.spring.rest.controller;

import de.spring.persistence.entity.DataType;
import de.spring.persistence.entity.DomainEntity;
import de.spring.persistence.response.EntityListResponse;
import de.spring.persistence.response.EntityResponse;
import de.spring.service.impl.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DomainEntity RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/domain")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public EntityListResponse<DomainEntity> findAll() {
      List<DomainEntity> entities = this.domainService.findAll();
      EntityListResponse<DomainEntity> response = new EntityListResponse<DomainEntity>(entities);
      ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
      response.setMetadata(responseMetadata);
      return response;
    }

    @GetMapping(value = "/{id}")
    public EntityResponse<DomainEntity> findById(@PathVariable("id") Long id) {
      DomainEntity entity = this.domainService.findById(id);
      EntityResponse<DomainEntity> rw = new EntityResponse<DomainEntity>(entity);
      if (entity == null) {
        rw.setMetadata(new ResponseMetadata(ResponseCode.NOT_FOUND, "response.notfound"));
      }
      return rw;
    }

    @PutMapping
    public EntityResponse<DomainEntity> save(@RequestBody DomainEntity domain) {
      if (domain.getProject() == null) {
        Project project = new Project();
        project.setId(domain.getProjectId());
        domain.setProject(project);
      }

      EntityResponse<DomainEntity> response = validateAttibutes(domain);
      if (response == null) {
        response.setResult(domainService.saveOrUpdate(domain));
      }
      return response;
    }

    @PostMapping
    public EntityResponse<DomainEntity> update(@RequestBody DomainEntity domain) {
      repsone.setResult(this.domainService.saveOrUpdate(domain));

      if (domain.getProject() == null) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(entity.getProjectId());
        domain.setProject(projectEntity);
      }
      
      EntityResponse<DomainEntity> response = validateAttibutes(domain);

      if (response == null) {
        response.setResult(domainService.saveOrUpdate(domain));
      }
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<DomainEntity> delete(@PathVariable("id") Long id) {
      DomainEntity toBeDeleted = this.domainService.findById(id);
      this.domainService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      response.setMetadata(responseMetadata);

      return response;
    }

    // Custom Endpoints
    @GetMapping(value = "/query/domainsbyproject/{projectId}")
    public EntityListResponse<DomainEntity> findAllDomainsByProject(@PathVariable("projectId") Long projectId) {
      Project project = projectService.findById(projectId);
      if (project == null) {
        EntityListResponse<DomainEntity> response = new EntityListResponse<DomainEntity>(null);
        ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.NOT_FOUND, "responsecode.notfound");
        response.setMetadata(responseMetadata);
        return response;
      } else {
        List<DomainEntity> entities = getService().findAllDomainsByProject(projectId);
        EntityListResponse<DomainEntity> response = new EntityListResponse<DomainEntity>(entities);
        ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
        response.setMetadata(responseMetadata);
        return response;
      }
    }
    
    @GetMapping(value = "/query/types")
    public DataType[] getTypes() {
      return DataType.values();
    }

    //helper functions
    private EntityResponse<DomainEntity> validateAttibutes(DomainEntity entity) {
      EntityResponse<DomainEntity> response = new EntityResponse<DomainEntity>();
      
      if (entity.getDomains() != null) {
        for (DomainEntity domain : entity.getDomains()) {
          ResponseMetadata responseMetadata = EntityValidator.validate(domain);	
          if (responseMetadata.getResponseCode() == ResponseCode.NOT_VALID) {
            response.setMetadata(responseMetadata);
            return response;
          }
        }
      }
      
      return null;
    }
}
