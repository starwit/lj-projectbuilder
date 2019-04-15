package de.starwit.ljprojectbuilder.api.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import de.starwit.generator.dto.GeneratorDto;
import de.starwit.generator.services.ProjectSetupService;
import de.starwit.ljprojectbuilder.exception.NotificationException;
import de.starwit.ljprojectbuilder.response.Response;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;
import de.starwit.repo.services.RepoData;
import de.starwit.repo.services.RepoServerData;
import de.starwit.repo.services.TargetRepoService;

@Path("/projectsetup")
@Consumes("application/json")
@Produces("application/json")
public class ProjectSetupRest {
	
	private static final Logger LOG = Logger.getLogger(ProjectSetupRest.class);
	
	@Inject
	protected ProjectSetupService service;
	
	@Inject
	protected TargetRepoService targetRepoService; 
	
	@Path("/downloadproject")
	@POST
	public Response<Boolean> downloadProject(GeneratorDto dto) throws Exception {
		
		try {
			service.setupAndGenerateProject(dto);
			Response<Boolean> response = new Response<>(true);
			ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.OK, "generator.success");
			response.setMetadata(responseMetadata);
			return response;
		} catch (NotificationException nex) {
			LOG.error(nex.getMessage());
			Response<Boolean> response = new Response<>(false);
			response.setMetadata(nex.getResponseMetadata());
			return response;
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			Response<Boolean> response = new Response<>(false);
			ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.ERROR, "generator.error");
			response.setMetadata(responseMetadata);
			return response;
		}
	}
	
	@Path("/currentrepos")
	@POST
	public Response<List<RepoData>> listCurrentRepos(RepoServerData data) {
		Response<List<RepoData>> response = new Response<>();
		
		if("".equals(data.getProjectName())) {
			data.setProjectName(null);
		}
        
		targetRepoService.setRepoServerData(data);
		List<RepoData> repoData = targetRepoService.listRepos();
		if (repoData == null) {
			response.setMetadata(new ResponseMetadata(ResponseCode.ERROR, "generator.target.test.error"));
		} else {
			response.setMetadata(new ResponseMetadata(ResponseCode.OK, "generator.target.test.success"));
			response.setResult(repoData);
		}
		
		return response;
	}
}