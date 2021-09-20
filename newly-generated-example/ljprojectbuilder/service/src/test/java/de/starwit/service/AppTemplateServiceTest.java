package de.starwit.service;

import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.repository.AppTemplateRepository;
import de.starwit.service.impl.AppTemplateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for AppTemplateService
 */
@RunWith(SpringRunner.class)
public class AppTemplateServiceTest {

    /**
     * <pre>
     * To check the Service class, we need to have an instance of Service class created and available as a
     * @Bean so that we can @Autowire it in our test class.
     * This configuration is achieved by using the @TestConfiguration annotation.
     * </pre>
     */
    @TestConfiguration
    static class AppTemplateServiceTestConfiguration {

        @Bean
        public AppTemplateService createAppTemplateService() {
            return new AppTemplateService();
        }
    }

    @Autowired
    private AppTemplateService apptemplateService;

    /**
     * Create a mock.
     */
    @MockBean
    private AppTemplateRepository apptemplateRepository;

    @Before
    public void setUp() {
        //TODO: setup objects for each test
    }

    //implement tests here
    @Test
    public void someTest() {
        
    }

}
