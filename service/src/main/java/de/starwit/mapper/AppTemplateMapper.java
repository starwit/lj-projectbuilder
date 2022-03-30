package de.starwit.mapper;

import de.starwit.dto.SaveAppTemplateDto;
import de.starwit.persistence.entity.AppTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppTemplateMapper implements CustomMapper<AppTemplate, SaveAppTemplateDto> {

    @Override
    public SaveAppTemplateDto convertToDto(AppTemplate entity) {
        SaveAppTemplateDto dto = new SaveAppTemplateDto();
        dto.setId(entity.getId());
        dto.setName(entity.getTemplateName());
        dto.setLocation(entity.getLocation());
        dto.setBranch(entity.getBranch());
        dto.setDescription(entity.getDescription());
        dto.setCredentialsRequired(entity.isCredentialsRequired());
        dto.setPackagePlaceholder(entity.getPackagePlaceholder());
        dto.setGroups(entity.getGroups());
        return dto;
    }

    @Override
    public AppTemplate convertToEntity(SaveAppTemplateDto dto) {
        AppTemplate entity = new AppTemplate();
        entity.setId(dto.getId());
        entity.setTemplateName(dto.getName());
        entity.setLocation(dto.getLocation());
        entity.setBranch(dto.getBranch());
        entity.setDescription(dto.getDescription());
        entity.setCredentialsRequired(dto.isCredentialsRequired());
        entity.setPackagePlaceholder(dto.getPackagePlaceholder());
        entity.setGroups(dto.getGroups());
        return entity;
    }
}
