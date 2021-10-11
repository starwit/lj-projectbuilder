package de.starwit.generator.services;


import java.io.Serializable;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.starwit.generator.dto.GeneratorDto;
import de.starwit.persistence.entity.Domain;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.service.impl.DomainService;
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
	private DomainService domainService;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private GeneratorService generatorService;
	
	@Autowired
	private AppCheckout appCheckout;
	
	@Autowired
	private AppRenamer appRenamer;
	
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
	public void setupAndGenerateApp(GeneratorDto dto) throws NotificationException, EntityNotFoundException {
		App app = appService.findById(dto.getApp().getId());
		//String destDirString = app.getTargetPath();
		//appCheckout.deleteTempApp(Constants.TMP_DIR + Constants.FILE_SEP + destDirString);
		String newAppFolder = appCheckout.createTempAppDirectory(app);
		app.setTargetPath(newAppFolder);
		app = appService.saveOrUpdate(app);

		GeneratorDto checkoutDto = dto;
		checkoutDto.setApp(app);
		appCheckout.checkoutAppTemplate(dto);
		appRenamer.renameAppTitle(app);
		appRenamer.renamePackage(app);
		dto.setApp(app);
		
		generatorService.generate(app.getId());
		appCheckout.findFilesAndDelete();
	}
}