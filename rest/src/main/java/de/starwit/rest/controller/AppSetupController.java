package de.starwit.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.dto.GeneratorDto;
import de.starwit.generator.services.AppSetupService;
import de.starwit.persistence.exception.NotificationException;

/**
 * AppSetup RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/appsetup")
public class AppSetupController {

  final static Logger LOG = LoggerFactory.getLogger(AppSetupController.class);

  @Autowired
  private AppSetupService appSetupService;

  @PostMapping
  public void generateApp(@RequestBody GeneratorDto dto) throws NotificationException {
    appSetupService.setupAndGenerateApp(dto);
  }
}
