package de.starwit.service;

<<<<<<< HEAD:spring/service/src/test/java/de/spring/service/DomainServiceTest.java
=======
import de.starwit.persistence.repository.DomainRepository;
import de.starwit.service.impl.DomainService;
import de.starwit.service.impl.ProjectService;

>>>>>>> 7e8480f44deaf6ab38266f83e4d7edd401f0908b:spring/service/src/test/java/de/starwit/service/DomainServiceTest.java
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import de.spring.persistence.repository.DomainRepository;
import de.spring.service.impl.DomainService;
import de.spring.service.impl.ProjectService;

/**
 * Tests for DomainService
 */
@RunWith(SpringRunner.class)
public class DomainServiceTest {

    /**
     * <pre>
     * To check the Service class, we need to have an instance of Service class created and available as a
     * &#64;Bean so that we can @Autowire it in our test class.
     * This configuration is achieved by using the @TestConfiguration annotation.
     * </pre>
     */
    @TestConfiguration
    static class DomainServiceTestConfiguration {

        @Bean
        public DomainService createDomainService() {
            return new DomainService();
        }
    }

    @Autowired
    private DomainService domainService;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private DomainRepository domainRepository;

    @Before
    public void setUp() {
        // TODO: setup objects for each test
    }

    // implement tests here
    @Test
    public void someTest() {

    }

}
