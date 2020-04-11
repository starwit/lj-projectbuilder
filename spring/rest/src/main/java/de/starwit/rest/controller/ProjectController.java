package de.starwit.rest.controller;

import javax.annotation.PostConstruct;

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

import de.starwit.dto.ProjectDto;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
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

	private GenericController<ProjectDto> genericController;

	@PostConstruct
	public void init() {
		genericController = new GenericController<ProjectDto>();
		genericController.setService(projectService);
	}

	@GetMapping("/query/all")
	public EntityListResponse<ProjectDto> findAll() {
		return genericController.findAll();
	}

	@GetMapping(value = "/query/{id}")
	public EntityResponse<ProjectDto> findById(@PathVariable("id") Long id) {
		return genericController.findById(id);
	}

	@PutMapping
	public EntityResponse<ProjectDto> save(@RequestBody ProjectDto category) {
		return genericController.editGeneric(category);
	}

	@PostMapping
	public EntityResponse<ProjectDto> update(@RequestBody ProjectDto category) {
		return genericController.editGeneric(category);
	}

	@DeleteMapping(value = "/{id}")
	public EntityResponse<ProjectDto> delete(@PathVariable("id") Long id) {
		return genericController.delete(id);
	}

//	@PostMapping
//	public ListResponse<String> getBranchnames(String remoteLocation) {
//		List<String> branchnames = new ArrayList<>();
//		Collection<Ref> refs = null;
//		ListResponse<String> response = new ListResponse<String>(branchnames);
//		try {
//			refs = Git.lsRemoteRepository().setHeads(true).setTags(true).setRemote(remoteLocation).call();
//
//			for (Ref ref : refs) {
//				String[] parts = ref.getName().split("/");
//				int l = parts.length;
//				if (l > 0) {
//					branchnames.add(parts[l - 1]);
//				}
//			}
//		} catch (GitAPIException e) {
//			LOG.error("Error getting branches");
//		}
//		response = new ListResponse<String>(branchnames);
//		if (branchnames.isEmpty()) {
//			branchnames.add("master");
//		}
//		ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.OK, "getbranchnames.ok");
//		response.setMetadata(responseMetadata);
//		return response;
//	}
}
