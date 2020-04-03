package de.spring.rest.controller;

import de.spring.service.impl.DomainService;
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
    public List<String> findAll() {
        return this.domainService.findAll();
    }

    @GetMapping(value = "/{id}")
    public String findById(@PathVariable("id") Long id) {
        return this.domainService.findById(id);
    }

    /**
     * @param domain
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody String domain) {
        this.domainService.saveOrUpdate(domain);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody String domain) {
        this.domainService.saveOrUpdate(domain);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestBody String domain) {
        this.domainService.delete(domain);
    }

}
