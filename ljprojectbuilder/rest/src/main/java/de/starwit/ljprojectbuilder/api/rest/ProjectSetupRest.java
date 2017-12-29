package de.starwit.ljprojectbuilder.api.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import de.starwit.generator.dto.GeneratorDto;
import de.starwit.generator.services.ProjectSetupService;
import de.starwit.ljprojectbuilder.response.Response;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;

@Path("/projectsetup")
@Consumes("application/json")
@Produces("application/json")
public class ProjectSetupRest {
	
	private static final Logger LOG = Logger.getLogger(ProjectSetupRest.class);
	
	@Inject
	protected ProjectSetupService service;
	
	@Path("/downloadproject")
	@POST
	public Response<Boolean> downloadProject(GeneratorDto dto) throws Exception {
		
		try {
			service.setupAndGenerateProject(dto);
			Response<Boolean> response = new Response<>(true);
			ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.OK, "generator.success");
			response.setMetadata(responseMetadata);
			return response;
		//TODO: handle exception
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			Response<Boolean> response = new Response<>(false);
			ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.ERROR, "generator.error");
			response.setMetadata(responseMetadata);
			return response;
		}
	}

	// temp functions to develop ci/cd stuff

	@Path("/ci/createrepo")
	@PUT
	public Response<Boolean> createRepository() {
		LOG.info("creating remote repo");
		Response<Boolean> response = new Response<>(true);
		return response;
	}

	@Path("/ci/checkincode")
	@PUT
	public Response<Boolean> checkInCode() {
		LOG.info("check in code to remote repo");
		Response<Boolean> response = new Response<>(true);
		return response;
	}

	@Path("/ci/createbuildjob")
	@PUT
	public Response<Boolean> createBuildJob() {
		LOG.info("creating build job");
		Response<Boolean> response = new Response<>(true);
		return response;
	}

	@Path("/ci/createruntimeenv")
	@PUT
	public Response<Boolean> createRuntimeEnvironment() {
		LOG.info("creating runtime environment");
		Response<Boolean> response = new Response<>(true);
		return response;
	}

	@Path("/ci/deployapp")
	@PUT
	public Response<Boolean> deployApplication() {
		LOG.info("calling build job");
		Response<Boolean> response = new Response<>(true);
		return response;
	}
}