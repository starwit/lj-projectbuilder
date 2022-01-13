package de.starwit.mapper;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.starwit.dto.AppTemplateDto;
import de.starwit.dto.ApplicationDto;
import de.starwit.dto.EntityDto;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.AppTemplate;

@Component
public class ApplicationMapper implements Serializable, CustomMapper<App, ApplicationDto> {

  @Autowired
  EntityMapper entityMapper;
  
  private static final long serialVersionUID = 1L;
  public static final long defaultAppTemplateID = 1L;

  public ApplicationDto convertToDto(App entity) {
    ApplicationDto dto = null;
    if (entity != null) {
      dto = new ApplicationDto();
      dto.setId(entity.getId());
      dto.setBaseName(entity.getTitle());
      dto.setPackageName(entity.getPackagePrefix());
      dto.setEntities(entityMapper.convertToDtoList(entity.getDomains()));
      AppTemplateDto appTemplateDto = new AppTemplateDto();
      if (entity.getTemplate() != null) {
        appTemplateDto.setId(entity.getTemplate().getId());
        appTemplateDto.setName(entity.getTemplate().getTemplateName());
        appTemplateDto.setCredentialsRequired(entity.getTemplate().isCredentialsRequired());
        dto.setTemplate(appTemplateDto);
      }
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

      AppTemplate appTemplate = new AppTemplate();
      if (dto.getTemplate() == null) {
        appTemplate.setId(defaultAppTemplateID);
      } else {
        appTemplate.setId(dto.getTemplate().getId());
      }
      if (dto.getId() == null) {
        deleteIdsFromEntityDtos(dto.getEntities());
      }
      app.setDomains(entityMapper.convertToEntityList(dto.getEntities()));
      entityMapper.addParent(app.getDomains(), app);
    }

    return app;
  }

  private void deleteIdsFromEntityDtos(Collection<EntityDto> dtos) {
    if (dtos != null) {
        for (EntityDto dto  : dtos) {
            dto.setId(null);
        }
    }
  }

}
