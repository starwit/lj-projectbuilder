package de.ttt.persistence.repository;

import de.ttt.persistence.entity.Domain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Tests for DomainRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DomainRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DomainRepository domainRepository;

    //implement tests here
    @Test
    public void someTest() {

    }
}
