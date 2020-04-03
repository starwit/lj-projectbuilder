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
@RequestMapping("${rest.base-path}/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> findAll() {
        return this.projectService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Project findById(@PathVariable("id") Long id) {
        return this.projectService.findById(id);
    }

    @PutMapping
    public EntityResponse<Project> save(@RequestBody Project project) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.projectService.saveOrUpdate(project));
      return response;
    }

    @PostMapping
    public EntityResponse<Project> update(@RequestBody Project project) {
      EntityResponse response = new EntityResponse();
      repsone.setResult(this.projectService.saveOrUpdate(project));
      return response;
    }

    @DeleteMapping(value = "/{id}")
    public EntityResponse<Project> delete(@PathVariable("id") Long id) {
      Project toBeDeleted = this.projectService.findById(id);
      this.projectService.delete(toBeDeleted);

      ResponseMetadata responseMetadata = new ResponseMetadata();
      responseMetadata.setResponseCode(ResponseCode.OK);
      responseMetadata.setMessage("Der Eintrag wurde gelöscht.");
      
      response.setMetadata(responseMetadata);

      return response;
    }

    //Custom endpoints
    @Path("/branchnames")
    @POST
    public ListResponse<String> getBranchnames(String remoteLocation) {
      List<String> branchnames = new ArrayList<>();
      Collection<Ref> refs = null;
      ListResponse<String> response =  new ListResponse<String>(branchnames);
      try {
        refs = Git.lsRemoteRepository()
                .setHeads(true)
                .setTags(true)
                .setRemote(remoteLocation).call();
        
        for (Ref ref : refs) {
          String[] parts = ref.getName().split("/");
          int l = parts.length;
          if (l > 0) {
            branchnames.add(parts[l-1]);
          }
        }
      } catch (GitAPIException e) {
        LOG.error("Error getting branches");
      }
      response = new ListResponse<String>(branchnames);
      if (branchnames.isEmpty()) {
        branchnames.add("master");
      }
      ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.OK, "getbranchnames.ok");
      response.setMetadata(responseMetadata);
      return response;
    }

}
