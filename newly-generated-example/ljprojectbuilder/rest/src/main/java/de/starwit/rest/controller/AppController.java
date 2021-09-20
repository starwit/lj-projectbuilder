package de.starwit.rest.controller;

import de.starwit.persistence.entity.App;
import de.starwit.service.impl.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * App RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/app")
public class AppController {

    @Autowired
    private AppService appService;

    @GetMapping
    public List<App> findAll() {
        return this.appService.findAll();
    }

    @GetMapping(value = "/{id}")
    public App findById(@PathVariable("id") Long id) {
        return this.appService.findById(id);
    }

    /**
     * @param app
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody App app) {
        this.appService.saveOrUpdate(app);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody App app) {
        this.appService.saveOrUpdate(app);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestBody App app) {
        this.appService.delete(app);
    }

}
