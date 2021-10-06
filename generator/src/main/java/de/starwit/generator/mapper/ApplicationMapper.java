package de.starwit.generator.mapper;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import de.starwit.generator.dto.ApplicationDto;
import de.starwit.generator.dto.EntityDto;
import de.starwit.persistence.entity.App;
import de.starwit.service.impl.DomainService;

@Component
public class ApplicationMapper implements Serializable, CustomMapper<App, ApplicationDto> {


  private static final long serialVersionUID = 1L;

  public ApplicationDto convertDb(App app) throws Exception {
    ApplicationDto applicationDto = new ApplicationDto();
    applicationDto.setBaseName(app.getTitle());
    applicationDto.setPackageName(app.getPackagePrefix());
    return applicationDto;
  }

  public App convertFrontend(ApplicationDto dto) throws Exception {
    App app = new App();
    app.setTitle(dto.getBaseName());
    app.setPackagePrefix(dto.getPackageName());

    List<EntityDto> entityDtos = dto.getEntities();

    return app;
  }

}
