package de.starwit.ljprojectbuilder.ejb.impl;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.ejb.GeneratorService;
import de.starwit.ljprojectbuilder.generator.CommonGenerator;
import de.starwit.ljprojectbuilder.generator.Generator;
import de.starwit.ljprojectbuilder.generator.entity.EntityModule;
import de.starwit.ljprojectbuilder.generator.frontend.FrontendModule;
import de.starwit.ljprojectbuilder.generator.rest.RestModule;
import de.starwit.ljprojectbuilder.generator.service.ServiceModule;

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
			Generator entityGenerator = new CommonGenerator<EntityModule>();
			entityGenerator.generate(setupBean);
		}
		
		if (setupBean.isGenerateService()) {
			Generator serviceGenerator = new CommonGenerator<ServiceModule>();
			serviceGenerator.generate(setupBean);
		}
		
		if (setupBean.isGenerateRest()) {
			Generator restGenerator = new CommonGenerator<RestModule>();
			restGenerator.generate(setupBean);
		}
		
		if (setupBean.isGenerateFrontend()) {
			Generator frontendGenerator = new CommonGenerator<FrontendModule>();
			frontendGenerator.generate(setupBean);
		}
		
	}
}
