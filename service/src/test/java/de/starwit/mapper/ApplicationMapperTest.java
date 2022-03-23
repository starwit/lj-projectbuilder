package de.starwit.mapper;

import de.starwit.dto.ApplicationDto;
import de.starwit.dto.EntityDto;
import de.starwit.dto.FieldDto;
import de.starwit.dto.FieldType;
import de.starwit.persistence.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class ApplicationMapperTest {

    final static Logger LOG = LoggerFactory.getLogger(ApplicationMapperTest.class);
    private App app;
    private ApplicationDto dto;
    @Autowired
    private ApplicationMapper applicationMapper;

    @Before
    public void createApp() {
        app = new App();
        app.setTitle("testAppTitle");
        app.setPackagePrefix("testpackage");

        Domain domain = new Domain();
        domain.setName("testdomain");
        Attribute attr = new Attribute();
        attr.setName("testattribute");
        attr.setDataType(DataType.String);
        List<Attribute> attributes = new ArrayList<Attribute>();
        attributes.add(attr);
        domain.setAttributes(attributes);
        app.setDomains(new ArrayList<Domain>());
        app.getDomains().add(domain);
        AppTemplate template = new AppTemplate();
        template.setId(ApplicationMapper.defaultAppTemplateID);
        template.setBranch("master");
        template.setTemplateName("testtemplate");
        app.setTemplate(template);
    }

    @Before
    public void createApplicationDto() {
        dto = new ApplicationDto();
        dto.setBaseName("testAppTitle");
        dto.setPackageName("testpackage");
        EntityDto entity = new EntityDto();
        entity.setName("testentity");
        FieldDto field = new FieldDto();
        field.setFieldName("testfield");
        entity.setFields(new ArrayList<>());
        entity.getFields().add(field);
        field.setFieldType(FieldType.String);
        dto.setEntities(new ArrayList<>());
        dto.getEntities().add(entity);
    }

    @Test
    public void convertToAppTest() throws Exception {
        ApplicationDto dto = applicationMapper.convertToDto(app);
        assertEquals("testAppTitle", dto.getBaseName());
        assertEquals("testpackage", dto.getPackageName());
        assertEquals("testdomain", dto.getEntities().get(0).getName());
        assertEquals("testattribute", dto.getEntities().get(0).getFields().get(0).getFieldName());
    }

    @Test
    public void convertToDtoTest() throws Exception {
        App app = applicationMapper.convertToEntity(dto);
        assertEquals("testAppTitle", app.getTitle());
        assertEquals("testpackage", app.getPackagePrefix());
        assertEquals("testentity", app.getDomains().get(0).getName());
        assertEquals("testfield", ((Attribute) app.getDomains().get(0).getAttributes().get(0)).getName());
    }

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
            ApplicationMapper applicationMapper = new ApplicationMapper();
            return applicationMapper;
        }

        @Bean
        public EntityMapper createEntityMapper() {
            EntityMapper entityMapper = new EntityMapper();
            return entityMapper;
        }

        @Bean
        public FieldMapper createFieldMapper() {
            FieldMapper fieldMapper = new FieldMapper();
            return fieldMapper;
        }
    }


}
