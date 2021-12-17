package de.starwit.mapper;

import org.springframework.stereotype.Component;

import de.starwit.dto.SaveAppTemplateDto;
import de.starwit.persistence.entity.AppTemplate;

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
        dto.setPackagePlaceholder(entity.getPackagePlaceholder());
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
        entity.setPackagePlaceholder(dto.getPackagePlaceholder());
        return entity;
    }
   
}
