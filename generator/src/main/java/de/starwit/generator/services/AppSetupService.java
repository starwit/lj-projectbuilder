package de.starwit.generator.services;


import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.starwit.dto.GeneratorDto;
import de.starwit.dto.LoadAppTemplateDto;
import de.starwit.mapper.ApplicationMapper;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.service.impl.AppService;
/**
 * Class for processing the whole app setup. A newly configured app is created and can be used.
 * @author Anett Huebner
 *
 */
@Service
public class AppSetupService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	@Autowired
	private AppService appService;
	
	@Autowired
	private GeneratorService generatorService;
	
	@Autowired
	private AppCheckout appCheckout;
	
	@Autowired
	private AppRenamer appRenamer;

	@Autowired
	private ApplicationMapper applicationMapper;
	
  final static Logger LOG = LoggerFactory.getLogger(AppSetupService.class);
	
  	/**
	 * Executes all functions needed to setup the new app. These are:
	 *  - checkout template-app from git-repository
	 *  - rename the app as configured in generatorDto.app
	 *  - rename the "starwit" package as configured in generatorDto.app
	 *  - generate CRUD-Operations for the several layers (Database access, REST API and frontend)
	 * @param dto - configuration for app setup
	 * @return
	 * @throws NotificationException
	 */
	@Transactional(propagation = Propagation.NEVER)
	public void setupAndGenerateApp(GeneratorDto dto) throws NotificationException {
		App app = appService.findById(dto.getAppId());
		dto.setAppTemplateId(app.getTemplate().getId());
		String newAppFolder = appCheckout.createTempAppDirectory(app.getTitle());
		app.setTargetPath(newAppFolder);
		AppTemplate appTemplate = appCheckout.checkoutAndUpdateAppTemplate(dto, newAppFolder);
		app.setTemplate(appTemplate);
		app = appService.saveOrUpdate(app);
		appRenamer.renameAppTitle(app);
		appRenamer.renamePackage(app);
		
		generatorService.generate(app.getId());
		appCheckout.findFilesAndDelete();
	}

	/**
	 * Executes all functions needed to get template description from git repository and updates template definitions in database.
	 * @param dto - configuration for app setup
	 * @return
	 * @throws NotificationException
	 */
	@Transactional(propagation = Propagation.NEVER)
	public void updateTemplates(LoadAppTemplateDto dto) throws NotificationException {
		String newAppTemplateFolder = appCheckout.createTempAppDirectory("defaultAppTemplatePath");
		appCheckout.checkoutAndUpdateAppTemplate(dto, newAppTemplateFolder);
		appCheckout.findFilesAndDelete();
	}
}