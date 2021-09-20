package de.starwit.persistence.repository;

import de.starwit.persistence.entity.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Tests for AppRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AppRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppRepository appRepository;

    //implement tests here
    @Test
    public void someTest() {

    }
}
