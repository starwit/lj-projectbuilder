package de.starwit.persistence.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for DomainRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DomainRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    //implement tests here
    @Test
    public void someTest() {

    }
}
