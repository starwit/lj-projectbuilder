package de.starwit.service.impl;

import de.starwit.persistence.entity.TemplateFile;
import de.starwit.persistence.repository.TemplateFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * 
 * TemplateFile Service class
 *
 */
@Service
public class TemplateFileService {

    @Autowired
    private TemplateFileRepository templatefileRepository;

    /**
     * @return
     */
    public List<TemplateFile> findAll() {
        return this.templatefileRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    public TemplateFile findById(Long id) {
        return this.templatefileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    /**
     * @param templatefile
     * @return
     */
    public TemplateFile saveOrUpdate(TemplateFile templatefile) {
        this.templatefileRepository.save(templatefile);
        return templatefile;
    }

    /**
     * @param templatefile
     */
    public void delete(TemplateFile templatefile) {
        this.templatefileRepository.delete(templatefile);
    }

}
