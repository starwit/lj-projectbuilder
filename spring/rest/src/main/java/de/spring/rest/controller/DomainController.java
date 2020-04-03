package de.spring.rest.controller;

import de.spring.persistence.entity.Domain;
import de.spring.service.impl.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Domain RestController
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
    public List<Domain> findAll() {
        return this.domainService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Domain findById(@PathVariable("id") Long id) {
        return this.domainService.findById(id);
    }

    @PutMapping
    public EntityResponse<Domain> save(@RequestBody Domain domain) {
      if (domain.getProject() == null) {
        Project project = new Project();
        project.setId(domain.getProjectId());
        domain.setProject(project);
      }

      EntityResponse<Domain> response = validateAttibutes(domain);
      if (response == null) {
        response.setResult(domainService.saveOrUpdate(domain));
      }
      return response;
    }

    @PostMapping
    public EntityResponse<Domain> update(@RequestBody Domain domain) {
      repsone.setResult(this.domainService.saveOrUpdate(domain));

      if (domain.getProject() == null) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(entity.getProjectId());
        domain.setProject(projectEntity);
      }
      
      EntityResponse<Domain> response = validateAttibutes(domain);

      if (response == null) {
        response.setResult(domainService.saveOrUpdate(domain));
      }
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<Domain> delete(@PathVariable("id") Long id) {
      Domain toBeDeleted = this.domainService.findById(id);
      this.domainService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      response.setMetadata(responseMetadata);

      return response;
    }

    // Custom Endpoints
    @GetMapping(value "/query/domainsbyproject/{projectId}")
    public EntityListResponse<Domain> findAllDomainsByProject(@PathVariable("projectId") Long projectId) {
      Project project = projectService.findById(projectId);
      if (project == null) {
        EntityListResponse<Domain> response = new EntityListResponse<Domain>(null);
        ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.NOT_FOUND, "responsecode.notfound");
        response.setMetadata(responseMetadata);
        return response;
      } else {
        List<Domain> entities = getService().findAllDomainsByProject(projectId);
        EntityListResponse<Domain> response = new EntityListResponse<Domain>(entities);
        ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
        response.setMetadata(responseMetadata);
        return response;
      }
    }
    
    @Path("/query/types")
    @GET
    public DataType[] getTypes() {
      return DataType.values();
    }

    //helper functions
    private EntityResponse<Domain> validateAttibutes(Domain entity) {
      EntityResponse<Domain> response = new EntityResponse<Domain>();
      
      if (entity.getDomains() != null) {
        for (Domain domain : entity.getDomains()) {
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
