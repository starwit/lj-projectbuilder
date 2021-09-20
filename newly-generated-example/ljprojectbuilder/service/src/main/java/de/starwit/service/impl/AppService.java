package de.starwit.service.impl;

import de.starwit.persistence.entity.App;
import de.starwit.persistence.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * 
 * App Service class
 *
 */
@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    /**
     * @return
     */
    public List<App> findAll() {
        return this.appRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    public App findById(Long id) {
        return this.appRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    /**
     * @param app
     * @return
     */
    public App saveOrUpdate(App app) {
        this.appRepository.save(app);
        return app;
    }

    /**
     * @param app
     */
    public void delete(App app) {
        this.appRepository.delete(app);
    }

}
