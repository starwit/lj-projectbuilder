package de.starwit.generator.services;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import de.starwit.generator.config.Constants;
import de.starwit.generator.dto.GeneratorDto;
import de.starwit.generator.generator.CommonGenerator;
import de.starwit.generator.generator.Generator;
import de.starwit.generator.modules.EntityModule;
import de.starwit.generator.modules.FrontendModule;
import de.starwit.generator.modules.RestModule;
import de.starwit.generator.modules.ServiceModule;

/**
 * General class for starting the code generation. The different parts of the application have their own special implementation of the code generator which are base on Generator-class.
 * @author anett
 *
 */
@Local
@Stateless(name = "GeneratorService")
public class GeneratorService {
	
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
