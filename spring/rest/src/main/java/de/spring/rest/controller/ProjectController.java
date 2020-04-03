package de.spring.rest.controller;

import de.spring.persistence.entity.ProjectEntity;
import de.spring.persistence.response.EntityListResponse;
import de.spring.persistence.response.EntityResponse;
import de.spring.persistence.response.ListResponse;
import de.spring.persistence.response.ResponseCode;
import de.spring.persistence.response.ResponseMetadata;
import de.spring.persistence.validation.EntityValidator;
import de.spring.service.impl.ProjectService;
import de.starwit.generator.services.Git;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Domain RestController Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/project")
public class ProjectController {

  final static Logger LOG = LoggerFactory.getLogger(ProjectController.class);

  @Autowired
  private ProjectService projectService;

  @GetMapping
  public EntityListResponse<ProjectEntity> findAll() {
    List<ProjectEntity> entities = this.projectService.findAll();
    EntityListResponse<ProjectEntity> response = new EntityListResponse<ProjectEntity>(entities);
    ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
    response.setMetadata(responseMetadata);
    return response;
  }

  @GetMapping(value = "/{id}")
  public EntityResponse<ProjectEntity> findById(@PathVariable("id") Long id) {
    ProjectEntity entity = this.projectService.findById(id);
    EntityResponse<ProjectEntity> rw = new EntityResponse<ProjectEntity>(entity);
    if (entity == null) {
      rw.setMetadata(new ResponseMetadata(ResponseCode.NOT_FOUND, "response.notfound"));
    }
    return rw;
  }

  @PutMapping
  public EntityResponse<ProjectEntity> save(@RequestBody ProjectEntity project) {
    EntityResponse<ProjectEntity> response = new EntityResponse<ProjectEntity>();
    response.setResult(this.projectService.saveOrUpdate(project));
    return response;
  }

  @PostMapping
  public EntityResponse<ProjectEntity> update(@RequestBody ProjectEntity project) {
    EntityResponse<ProjectEntity> response = new EntityResponse<ProjectEntity>();
    response.setResult(this.projectService.saveOrUpdate(project));
    return response;
  }

  @DeleteMapping(value = "/{id}")
  public EntityResponse<ProjectEntity> delete(@PathVariable("id") Long id) {
    ProjectEntity toBeDeleted = this.projectService.findById(id);
    this.projectService.delete(toBeDeleted);

    ResponseMetadata responseMetadata = new ResponseMetadata();
    responseMetadata.setResponseCode(ResponseCode.OK);
    responseMetadata.setMessage("Der Eintrag wurde gelöscht.");

    EntityResponse<ProjectEntity> response = new EntityResponse<ProjectEntity>();
    response.setMetadata(responseMetadata);

    return response;
  }

  // Custom endpoints
  // @PostMapping(value = "/branchnames")
  // public ListResponse<String> getBranchnames(String remoteLocation) {
  //   List<String> branchnames = new ArrayList<>();
  //   Collection<Ref> refs = null;
  //   ListResponse<String> response = new ListResponse<String>(branchnames);
  //   try {
  //     refs = Git.lsRemoteRepository().setHeads(true).setTags(true).setRemote(remoteLocation).call();

  //     for (Ref ref : refs) {
  //       String[] parts = ref.getName().split("/");
  //       int l = parts.length;
  //       if (l > 0) {
  //         branchnames.add(parts[l - 1]);
  //       }
  //     }
  //   } catch (GitAPIException e) {
  //     LOG.error("Error getting branches");
  //   }
  //   response = new ListResponse<String>(branchnames);
  //   if (branchnames.isEmpty()) {
  //     branchnames.add("master");
  //   }
  //   ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.OK, "getbranchnames.ok");
  //   response.setMetadata(responseMetadata);
  //   return response;
  // }

}
