package de.ttt.rest.controller;

import de.ttt.persistence.entity.Domain;
import de.ttt.service.impl.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/domain")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @GetMapping
    public List<Domain> findAll() {
        return this.domainService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Domain findById(@PathVariable("id") Long id) {
        return this.domainService.findById(id);
    }

    /**
     * @param domain
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Domain domain) {
        this.domainService.saveOrUpdate(domain);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Domain domain) {
        this.domainService.saveOrUpdate(domain);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestBody Domain domain) {
        this.domainService.delete(domain);
    }

}
