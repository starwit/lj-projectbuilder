package de.starwit.ljprojectbuilder.api.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.ejb.ProjectSetupService;
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

}