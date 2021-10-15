package de.starwit.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import de.starwit.dto.AppTemplateDto;
import de.starwit.dto.ApplicationDto;
import de.starwit.dto.EntityDto;
import de.starwit.mapper.ApplicationMapper;

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc(addFilters = false)
public class ApplicationControllerAcceptanceTest {

    final static Logger LOG = LoggerFactory.getLogger(ApplicationControllerAcceptanceTest.class);

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ApplicationMapper applicationMapper;

    private JacksonTester<ApplicationDto> jsonApplicationDto;

    @BeforeEach
    public void init() {
        // create Object Mapper
        mapper = new ObjectMapper();
        JacksonTester.initFields(this, new ObjectMapper());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void canCreate() throws Exception {
        //given
        ApplicationDto dto = readFromFile("app.json");
        AppTemplateDto templateDto = new AppTemplateDto();
        templateDto.setId(1L);
        templateDto.setName("lirejarp");
        dto.setTemplate(templateDto);

        //when
        MockHttpServletResponse response = create(dto);

        //then
        JsonNode jsonNode = mapper.readTree(response.getContentAsString());
        Long id = jsonNode.get("id").asLong();
        dto.setId(id);
        String applicationString = jsonApplicationDto.write(dto).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(applicationString);
    }
 
    @Test
    public void canRetrieveById() throws Exception {
        //given
        ApplicationDto dto = readFromFile("app.json");
        MockHttpServletResponse response = create(dto);
        ApplicationDto dto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);

        //when
        response = retrieveById(dto, dto2.getId());

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
            .isEqualTo(jsonApplicationDto.write(dto2).getJson());
    }

    @Test
    public void canRetrieveByIdWithFields() throws Exception {
        //given
        ApplicationDto dto = readFromFile("app-with-fields.json");
        MockHttpServletResponse response = create(dto);
        ApplicationDto dto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);

        //when
        response = retrieveById(dto, dto2.getId());

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ApplicationDto result = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        assertThat(jsonApplicationDto.write(result).getJson())
            .isEqualTo(jsonApplicationDto.write(dto2).getJson());
    }

    @Test
    public void canRetrieveByIdWithRelations() throws Exception {
        //given
        ApplicationDto dto = readFromFile("app-with-relations.json");
        MockHttpServletResponse response = create(dto);
        ApplicationDto dto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);

        //when
        response = retrieveById(dto, dto2.getId());

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ApplicationDto result = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        assertThat(jsonApplicationDto.write(result).getJson())
            .isEqualTo(jsonApplicationDto.write(dto2).getJson());
    }

    @Test
    public void canUpdate() throws Exception {

        //given
        ApplicationDto dto = readFromFile("app-with-fields.json");
        MockHttpServletResponse response = create(dto);
        ApplicationDto dto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        dto2.setBaseName("secondName");
        EntityDto entityDto = dto2.getEntities().get(0);
        entityDto.setName("secondEntityName");
        int fieldcount = entityDto.getFields().size();
        entityDto.getFields().get(0).setFieldName("secondFieldName");
        entityDto.getFields().remove(1);
       
        //when
        response = update(dto2);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ApplicationDto result = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        assertThat(result.getId()).isEqualTo(dto2.getId());
        assertThat(result.getBaseName()).isEqualTo("secondName");
        assertThat(result.getEntities().get(0).getFields().size()).isEqualTo(fieldcount-1);
        assertThat(result.getEntities().get(0).getId()).isEqualTo(entityDto.getId());
        assertThat(result.getEntities().get(0).getName()).isEqualTo("secondEntityName");
        assertThat(result.getEntities().get(0).getFields().get(0).getId()).isEqualTo(entityDto.getFields().get(0).getId());
        assertThat(result.getEntities().get(0).getFields().get(0).getFieldName()).isEqualTo("secondFieldName");

        assertThat(response.getContentAsString())
            .isEqualTo(jsonApplicationDto.write(dto2).getJson());
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

    private MockHttpServletResponse retrieveById(ApplicationDto dto, Long id) throws Exception {
        MockHttpServletResponse response = mvc.perform(
        get("/api/application/" + id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }

    private MockHttpServletResponse create(ApplicationDto dto) throws Exception {
        String applicationString = jsonApplicationDto.write(dto).getJson();
        MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.put("/api/application/")
                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                              .accept(MediaType.APPLICATION_JSON)
                              .characterEncoding("UTF-8")
                              .content(applicationString);

        MockHttpServletResponse response = mvc.perform(builder)
            .andExpect(status().isOk())
            .andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }

    private MockHttpServletResponse update(ApplicationDto dto) throws Exception {
        String applicationString = jsonApplicationDto.write(dto).getJson();
        MockHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.post("/api/application/")
                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                              .accept(MediaType.APPLICATION_JSON)
                              .characterEncoding("UTF-8")
                              .content(applicationString);

        MockHttpServletResponse response = mvc.perform(builder)
            .andExpect(status().isOk())
            .andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }

}
