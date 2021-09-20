package de.starwit.service.impl;

import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.repository.AppTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * 
 * AppTemplate Service class
 *
 */
@Service
public class AppTemplateService {

    @Autowired
    private AppTemplateRepository apptemplateRepository;

    /**
     * @return
     */
    public List<AppTemplate> findAll() {
        return this.apptemplateRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    public AppTemplate findById(Long id) {
        return this.apptemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    /**
     * @param apptemplate
     * @return
     */
    public AppTemplate saveOrUpdate(AppTemplate apptemplate) {
        this.apptemplateRepository.save(apptemplate);
        return apptemplate;
    }

    /**
     * @param apptemplate
     */
    public void delete(AppTemplate apptemplate) {
        this.apptemplateRepository.delete(apptemplate);
    }

}
