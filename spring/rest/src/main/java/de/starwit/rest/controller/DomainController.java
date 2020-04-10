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

import de.starwit.persistence.entity.DataType;
import de.starwit.persistence.entity.DomainEntity;
import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.persistence.validation.EntityValidator;
import de.starwit.service.impl.DomainService;
import de.starwit.service.impl.ProjectService;

/**
 * DomainEntity RestController Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/domain")
public class DomainController {

  @Autowired
  private DomainService domainService;

  @Autowired
  private ProjectService projectService;

  @GetMapping("/query/all")
  public EntityListResponse<DomainEntity> findAll() {
    List<DomainEntity> entities = this.domainService.findAll();
    EntityListResponse<DomainEntity> response = new EntityListResponse<DomainEntity>(entities);
    ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
    response.setMetadata(responseMetadata);
    return response;
  }

  @GetMapping(value = "/query/{id}")
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
      ProjectEntity project = new ProjectEntity();
      // TODO DH: auto created.
      // project.setId(domain.getProjectId());
      domain.setProject(project);
    }

    EntityResponse<DomainEntity> response = validateAttibutes(domain);
    if (response != null) {
      response.setResult(domainService.saveOrUpdate(domain));
    }
    return response;
  }

  @PostMapping
  public EntityResponse<DomainEntity> update(@RequestBody DomainEntity domain) {

    // response.setResult(this.domainService.saveOrUpdate(domain));

    if (domain.getProject() == null) {
      ProjectEntity projectEntity = new ProjectEntity();
      // projectEntity.setId(entity.getProjectId());
      domain.setProject(projectEntity);
    }

    EntityResponse<DomainEntity> response = validateAttibutes(domain);

    if (response == null) {
      // response.setResult(domainService.saveOrUpdate(domain));
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

    EntityResponse<DomainEntity> response = new EntityResponse<DomainEntity>();
    response.setMetadata(responseMetadata);

    return response;
  }

  // Custom Endpoints
  @GetMapping(value = "/query/domainsbyproject/{projectId}")
  public EntityListResponse<DomainEntity> findAllDomainsByProject(@PathVariable("projectId") Long projectId)
      throws NotificationException {
    ProjectEntity project = projectService.findProjectByIdOrThrowExeption(projectId);
    if (project == null) {
      EntityListResponse<DomainEntity> response = new EntityListResponse<DomainEntity>(null);
      ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.NOT_FOUND, "responsecode.notfound");
      response.setMetadata(responseMetadata);
      return response;
    } else {
      List<DomainEntity> entities = this.domainService.findAllDomainsByProject(projectId);
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

  // helper functions
  private EntityResponse<DomainEntity> validateAttibutes(DomainEntity entity) {
    EntityResponse<DomainEntity> response = new EntityResponse<DomainEntity>();

    // if (entity.getDomains() != null) {
    //   for (DomainEntity domain : entity.getDomains()) {
    //     ResponseMetadata responseMetadata = EntityValidator.validate(domain);
    //     if (responseMetadata.getResponseCode() == ResponseCode.NOT_VALID) {
    //       response.setMetadata(responseMetadata);
    //       return response;
    //     }
    //   }
    // }

    return null;
  }
}
