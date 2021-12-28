package de.starwit.rest.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.dto.GeneratorDto;
import de.starwit.dto.DownloadAppTemplateDto;
import de.starwit.generator.services.AppSetupService;
import de.starwit.persistence.exception.NotificationException;
import io.swagger.v3.oas.annotations.Operation;

/**
 * AppSetup RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}")
public class AppSetupController {

  final static Logger LOG = LoggerFactory.getLogger(AppSetupController.class);

  @Autowired
  private AppSetupService appSetupService;

  @Operation(summary = "Creates new App from templates.")
  @PostMapping(value = "/setup-app")
  public void generateApp(@Valid @RequestBody GeneratorDto dto) throws NotificationException {
    appSetupService.setupAndGenerateApp(dto);
  }

  @Operation(summary = "Gets template description from git repository and updates template definitions in database.")
  @PostMapping(value = "/update-templates")
	public void updateCodeTemplates(@Valid @RequestBody DownloadAppTemplateDto dto) throws NotificationException {
	  appSetupService.updateTemplates(dto);
	}
}
