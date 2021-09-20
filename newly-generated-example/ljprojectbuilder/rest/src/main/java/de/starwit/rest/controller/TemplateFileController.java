package de.starwit.rest.controller;

import de.starwit.persistence.entity.TemplateFile;
import de.starwit.service.impl.TemplateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TemplateFile RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/templatefile")
public class TemplateFileController {

    @Autowired
    private TemplateFileService templatefileService;

    @GetMapping
    public List<TemplateFile> findAll() {
        return this.templatefileService.findAll();
    }

    @GetMapping(value = "/{id}")
    public TemplateFile findById(@PathVariable("id") Long id) {
        return this.templatefileService.findById(id);
    }

    /**
     * @param templatefile
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody TemplateFile templatefile) {
        this.templatefileService.saveOrUpdate(templatefile);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody TemplateFile templatefile) {
        this.templatefileService.saveOrUpdate(templatefile);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestBody TemplateFile templatefile) {
        this.templatefileService.delete(templatefile);
    }

}
