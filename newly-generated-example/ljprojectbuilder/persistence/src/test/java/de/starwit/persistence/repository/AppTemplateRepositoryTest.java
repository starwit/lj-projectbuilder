package de.starwit.persistence.repository;

import de.starwit.persistence.entity.AppTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Tests for AppTemplateRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AppTemplateRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppTemplateRepository apptemplateRepository;

    //implement tests here
    @Test
    public void someTest() {

    }
}
