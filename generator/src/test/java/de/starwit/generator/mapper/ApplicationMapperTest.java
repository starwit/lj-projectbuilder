package de.starwit.generator.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import de.starwit.generator.dto.ApplicationDto;
import de.starwit.persistence.entity.App;

@RunWith(SpringRunner.class)
public class ApplicationMapperTest {

    final static Logger LOG = LoggerFactory.getLogger(ApplicationMapperTest.class);

        /**
     * <pre>
     * To check the Service class, we need to have an instance of Service class created and available as a
     * &#64;Bean so that we can @Autowire it in our test class.
     * This configuration is achieved by using the @TestConfiguration annotation.
     * </pre>
     */
    @TestConfiguration
    static class ApplicationMapperTestConfiguration {

        @Bean
        public ApplicationMapper createApplicationMapper() {
            ApplicationMapper appMapper = new ApplicationMapper();
            return appMapper;
        }
    }

    @Autowired
    private ApplicationMapper applicationMapper;

    @Test
   public void mapAppTest() throws Exception {
        App app = new App();
        app.setTitle("testAppTitle");
        app.setPackagePrefix("testpackage");

        ApplicationDto dto = applicationMapper.convertDb(app);
        assertEquals( "testAppTitle", dto.getBaseName());
        assertEquals("testpackage", dto.getPackageName());
      }
    
    @Test
    public void mapApplicationDtoTest() throws Exception {
        ApplicationDto dto = new ApplicationDto();
        dto.setBaseName("testAppTitle");
        dto.setPackageName("testpackage");
        App app = applicationMapper.convertFrontend(dto);
        assertEquals( "testAppTitle", app.getTitle());
        assertEquals("testpackage", app.getPackagePrefix());
    }
    
    
}
