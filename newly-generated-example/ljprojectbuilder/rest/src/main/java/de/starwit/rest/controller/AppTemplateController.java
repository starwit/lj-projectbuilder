package de.starwit.rest.controller;

import de.starwit.persistence.entity.AppTemplate;
import de.starwit.service.impl.AppTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AppTemplate RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/apptemplate")
public class AppTemplateController {

    @Autowired
    private AppTemplateService apptemplateService;

    @GetMapping
    public List<AppTemplate> findAll() {
        return this.apptemplateService.findAll();
    }

    @GetMapping(value = "/{id}")
    public AppTemplate findById(@PathVariable("id") Long id) {
        return this.apptemplateService.findById(id);
    }

    /**
     * @param apptemplate
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody AppTemplate apptemplate) {
        this.apptemplateService.saveOrUpdate(apptemplate);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody AppTemplate apptemplate) {
        this.apptemplateService.saveOrUpdate(apptemplate);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestBody AppTemplate apptemplate) {
        this.apptemplateService.delete(apptemplate);
    }

}
