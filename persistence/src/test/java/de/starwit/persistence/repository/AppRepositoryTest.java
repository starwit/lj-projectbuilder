package de.starwit.persistence.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.AppTemplate;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    AppRepository repository;

    @Autowired
    AppTemplateRepository appTemplateRepository;

    @Test
    public void testFindAll() {
        List<App> apps = repository.findAll();
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testCountAppUsingTemplate() {
        AppTemplate template = new AppTemplate();
        template.setLocation("https://test.git");
        List<String> groups = new ArrayList<>();
        groups.add("public");
        template.setGroups(groups);
        template = appTemplateRepository.save(template);
        int count = repository.countAppUsingTemplate(template.getId());
        assertEquals(0, count);
        App app = new App();
        app.setTemplate(template);
        app.setTitle("test");
        app.setPackagePrefix("packagePrefix");
        app.setGroups(groups);
        repository.save(app);
        count = repository.countAppUsingTemplate(template.getId());
        assertEquals(1, count);
    }
}
