package de.starwit.generator.mapper;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.starwit.generator.dto.ApplicationDto;
import de.starwit.persistence.entity.App;

@Component
public class ApplicationMapper implements Serializable, CustomMapper<App, ApplicationDto> {

  @Autowired
  EntityMapper entityMapper;
  
  private static final long serialVersionUID = 1L;

  public ApplicationDto convertToDto(App entity) {
    ApplicationDto dto = null;
    if (entity != null) {
      dto = new ApplicationDto();
      dto.setId(entity.getId());
      dto.setBaseName(entity.getTitle());
      dto.setPackageName(entity.getPackagePrefix());
      dto.setEntities(entityMapper.convertToDtoList(entity.getDomains()));
    }

    return dto;
  }

  public App convertToEntity(ApplicationDto dto) {
    App app = null;
    if (dto != null) {
      app = new App();
      app.setId(dto.getId());
      app.setTitle(dto.getBaseName());
      app.setPackagePrefix(dto.getPackageName());

      app.setDomains(entityMapper.convertToEntityList(dto.getEntities()));
    }

    return app;
  }

}
