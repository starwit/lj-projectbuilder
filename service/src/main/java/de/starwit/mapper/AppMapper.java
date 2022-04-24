package de.starwit.mapper;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.starwit.dto.AppTemplateDto;
import de.starwit.dto.AppDto;
import de.starwit.dto.EntityDto;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.repository.AppTemplateRepository;

@Component
public class AppMapper implements Serializable, CustomMapper<App, AppDto> {

    @Autowired
    EntityMapper entityMapper;

    private static final long serialVersionUID = 1L;
    public static final long defaultAppTemplateID = 1L;

    public AppDto convertToDto(App entity) {
        AppDto dto = null;
        if (entity != null) {
            dto = new AppDto();
            dto.setId(entity.getId());
            dto.setBaseName(entity.getTitle());
            dto.setPackageName(entity.getPackagePrefix());
            dto.setTargetPath(entity.getTargetPath());
            dto.setEntities(entityMapper.convertToDtoList(entity.getDomains()));
            dto.setGroupsToAssign(entity.getGroups());
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

    public App convertToEntity(AppDto dto) {
        App app = null;
        if (dto != null) {
            app = new App();
            app.setId(dto.getId());
            app.setTitle(dto.getBaseName());
            app.setPackagePrefix(dto.getPackageName());
            app.setTargetPath(dto.getTargetPath());

            if (dto.getTemplate() != null) {
                AppTemplate appTemplate = new AppTemplate();
                appTemplate.setId(dto.getTemplate().getId());
                app.setTemplate(appTemplate);
            }
            if (dto.getId() == null) {
                deleteIdsFromEntityDtos(dto.getEntities());
            }
            app.setDomains(entityMapper.convertToEntityList(dto.getEntities()));
            app.setGroups(dto.getGroupsToAssign());
            entityMapper.addParent(app.getDomains(), app);
        }

        return app;
    }

    private void deleteIdsFromEntityDtos(Collection<EntityDto> dtos) {
        if (dtos != null) {
            for (EntityDto dto : dtos) {
                dto.setId(null);
            }
        }
    }

}
