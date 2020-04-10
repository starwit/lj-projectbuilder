package de.starwit.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.persistence.validation.EntityValidator;
import de.starwit.service.impl.ProjectService;

/**
 * Domain RestController Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/project")
public class ProjectController {

  final static Logger LOG = LoggerFactory.getLogger(ProjectController.class);

  @Autowired
  private ProjectService projectService;

  @GetMapping("/query/all")
  public EntityListResponse<ProjectEntity> findAll() {
    List<ProjectEntity> entities = this.projectService.findAll();
    EntityListResponse<ProjectEntity> response = new EntityListResponse<ProjectEntity>(entities);
    ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
    response.setMetadata(responseMetadata);
    return response;
  }

  @GetMapping(value = "/query/{id}")
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
