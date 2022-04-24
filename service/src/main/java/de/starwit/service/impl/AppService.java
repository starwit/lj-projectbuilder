package de.starwit.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.mapper.AppMapper;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.repository.AppRepository;
import de.starwit.persistence.repository.AppTemplateRepository;

@Service
public class AppService implements ServiceInterface<App, AppRepository> {

    public final static String[] EXT = new String[] { "java", "js", "html", "sql", "xml" };
    final static Logger LOG = LoggerFactory.getLogger(AppService.class);

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AppTemplateRepository appTemplateRepository;

    @Override
    public AppRepository getRepository() {
        return appRepository;
    }

    public List<App> findByGroups(List<String> groups) {

        return this.getRepository().findByGroupString("," + String.join(",|", groups) + ",");
    }

    @Override
    public App saveOrUpdate(App entity) {
        if (entity != null) {
            AppTemplate appTempate;
            if (entity.getTemplate() != null && entity.getTemplate().getId() != null) {
                appTempate = this.appTemplateRepository.getById(entity.getTemplate().getId());
            } else {
                List<AppTemplate> templates = this.appTemplateRepository
                        .findFirstByGroupString("," + String.join(",|", entity.getGroups()) + ",");
                if (templates != null && !templates.isEmpty()) {
                    appTempate = templates.get(0);
                } else {
                    appTempate = this.appTemplateRepository.getById(AppMapper.defaultAppTemplateID);
                }
            }
            entity.setTemplate(appTempate);
            entity = this.getRepository().save(entity);
        }
        return entity;
    }
}