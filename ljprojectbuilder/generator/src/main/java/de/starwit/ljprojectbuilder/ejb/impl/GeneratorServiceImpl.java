package de.starwit.ljprojectbuilder.ejb.impl;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.ejb.GeneratorService;
import de.starwit.ljprojectbuilder.generator.entity.EntityGenerator;
import de.starwit.ljprojectbuilder.generator.frontend.FrontendGenerator;
import de.starwit.ljprojectbuilder.generator.rest.RestGenerator;
import logic.generators.ServiceGenerator;

@Stateless(name = "GeneratorService")
public class GeneratorServiceImpl implements GeneratorService {
	
	public final static Logger LOG = Logger.getLogger(GeneratorService.class);
	
	/**
	 * Generates the target files for the application.
	 * @param setupBean - generator configuration
	 */
	public void generate(GeneratorDto setupBean) {
		
		//TODO: NOT NULL CHECK

		LOG.info("Setup Bean templatePath " + Constants.TEMPLATE_PATH);
		LOG.info("Setup Bean generate Entity:  " + setupBean.isGenerateEntity());
		LOG.info("Setup Bean generate Service: " + setupBean.isGenerateService());
		LOG.info("Setup Bean generate Rest: " + setupBean.isGenerateRest());
		LOG.info("Setup Bean generate Frontend: " + setupBean.isGenerateFrontend());
		
		
		if (setupBean.isGenerateEntity()) {
			EntityGenerator entityGenerator = new EntityGenerator();
			entityGenerator.generate(setupBean);
		}
		
		if (setupBean.isGenerateService()) {
			ServiceGenerator serviceGenerator = new ServiceGenerator();
			serviceGenerator.generate(setupBean);
		}
		
		if (setupBean.isGenerateRest()) {
			RestGenerator restGenerator = new RestGenerator();
			restGenerator.generate(setupBean);
		}
		
		if (setupBean.isGenerateFrontend()) {
			FrontendGenerator frontendGenerator = new FrontendGenerator();
			frontendGenerator.generate(setupBean);
		}
		
	}
}
