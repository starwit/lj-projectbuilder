package de.starwit.generator.services;

import de.starwit.dto.GitAuthDto;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.service.impl.AppService;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    static final Logger LOG = LoggerFactory.getLogger(AppSetupService.class);

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
    public void setupAndGenerateApp(Long appId, GitAuthDto dto) throws NotificationException {
        App app = appService.findById(appId);
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
    public void updateTemplates(Long appTemplateId, GitAuthDto dto) throws NotificationException {
        dto.setAppTemplateId(appTemplateId);
        String newAppTemplateFolder = appCheckout.createTempAppDirectory("defaultAppTemplatePath");
        appCheckout.checkoutAndUpdateAppTemplate(dto, newAppTemplateFolder);
        appCheckout.findFilesAndDelete();
    }
}
