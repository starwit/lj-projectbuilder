package de.starwit.ljprojectbuilder.api.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

import de.starwit.ljprojectbuilder.ejb.ProjectService;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.response.EntityResponse;
import de.starwit.ljprojectbuilder.response.ListResponse;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;
import de.starwit.ljprojectbuilder.validation.EntityValidator;

@Path("/project")
@Consumes("application/json")
@Produces("application/json")
public class ProjectRest extends AbstractRest<ProjectEntity> {
	
	private static final Logger LOG = Logger.getLogger(ProjectRest.class);
	
	@Inject
	protected ProjectService service;
	
	@Override
	protected ProjectService getService() {
		return service;
	}
	
	//Create
	@Path("/")
	@PUT
	public EntityResponse<ProjectEntity> create(ProjectEntity entity) {
		return super.createGeneric(entity);
	}

	//Update
	@Path("/")
	@POST
	public EntityResponse<ProjectEntity> update(ProjectEntity entity) {
		return super.updateGeneric(entity);
	}
	
	@Path("/branchnames")
	@POST
	public  ListResponse<String> getBranchnames(String remoteLocation) {
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