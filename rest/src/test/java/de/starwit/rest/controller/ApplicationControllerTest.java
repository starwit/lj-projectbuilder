package de.starwit.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import de.starwit.generator.dto.ApplicationDto;
import de.starwit.generator.mapper.ApplicationMapper;
import de.starwit.generator.mapper.EntityMapper;
import de.starwit.generator.mapper.FieldMapper;
import de.starwit.persistence.entity.App;
import de.starwit.service.impl.AppService;

@WithMockUser(username = "admin", roles = { "ADMIN", "PBUSER" })
@WebMvcTest(controllers = ApplicationController.class)
@Import({ApplicationMapper.class, EntityMapper.class, FieldMapper.class})
public class ApplicationControllerTest {

    final static Logger LOG = LoggerFactory.getLogger(ApplicationControllerTest.class);

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private AppService appService;

    private JacksonTester<ApplicationDto> jsonApplicationDto;

    @Autowired
    private ApplicationMapper applicationMapper;

    @BeforeEach
    public void setup() {
        // create Object Mapper
        ObjectMapper mapper = new ObjectMapper();
        JacksonTester.initFields(this, new ObjectMapper());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @WithMockUser(username = "admin", roles = { "ADMIN", "PBUSER" })
    @Test
    public void canRetrieveById() throws Exception {

        ApplicationDto dto = readFromFile("app.json");
        MockHttpServletResponse response = retrieveById(dto);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
            .isEqualTo(jsonApplicationDto.write(dto).getJson());
    }

    @WithMockUser(username = "admin", roles = { "ADMIN", "PBUSER" })
    @Test
    public void canRetrieveByIdWithFields() throws Exception {

        ApplicationDto dto = readFromFile("app-with-fields.json");
        MockHttpServletResponse response = retrieveById(dto);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
            .isEqualTo(jsonApplicationDto.write(dto).getJson());
    }

    //@WithMockUser(username = "admin", roles = { "ADMIN", "PBUSER" })
    //@Test
    public void canRetrieveByIdWithRelations() throws Exception {

        ApplicationDto dto = readFromFile("app-with-relations.json");
        MockHttpServletResponse response = retrieveById(dto);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
            .isEqualTo(jsonApplicationDto.write(dto).getJson());
    }


    private ApplicationDto readFromFile(String path) throws Exception {
        try {
            URL res = getClass().getClassLoader().getResource(path);
            File file = new File(res.getFile());
            ApplicationDto dto = mapper.readValue(file, ApplicationDto.class);
            return dto;
        } catch (IOException e) {
            LOG.error("JSON mapper failed", e);
            throw new Exception("SON mapper failed");
        }
    }
    
    private MockHttpServletResponse retrieveById(ApplicationDto dto) throws Exception {
        // given
        App entity = applicationMapper.convertToEntity(dto);
        when(appService.findById(0L)).thenReturn(entity);

        // when
        MockHttpServletResponse response = mvc.perform(
        get("/api/application/0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }

}
