package de.starwit.ljprojectbuilder.api.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.ejb.GeneratorService;
import de.starwit.ljprojectbuilder.response.Response;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;

@Path("/generator")
@Consumes("application/json")
@Produces("application/json")
public class GeneratorRest {
	
	@Inject
	private GeneratorService generatorService;
	
	@Path("/generate")
	@POST
	public Response<Boolean> generate(GeneratorDto dto) {
		try {
			generatorService.generate(dto);
			Response<Boolean> response = new Response<>(true);
			ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.OK, "generator.success");
			response.setMetadata(responseMetadata);
			return response;
		//TODO: handle exception
		} catch (Exception ex) {
			Response<Boolean> response = new Response<>(false);
			ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.ERROR, "generator.error");
			response.setMetadata(responseMetadata);
			return response;
		}
		
	}

}
