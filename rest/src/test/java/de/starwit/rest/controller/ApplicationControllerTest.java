package de.starwit.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    
    public App createApp() {
        App app = new App();
        app.setId(2L);
        app.setTitle("testAppTitle");
        app.setPackagePrefix("testpackage");
        return app;
    }

    public ApplicationDto createApplicationDto() {
        ApplicationDto dto = new ApplicationDto();
        dto.setId(2L);
        dto.setBaseName("testAppTitle");
        dto.setPackageName("testpackage");
        return dto;
    }

    @WithMockUser(username = "admin", roles = { "ADMIN", "PBUSER" })
    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());

        // given
       when(appService.findById(2L)).thenReturn(createApp());


        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/application/2")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        LOG.info(response.getContentAsString());
        assertThat(response.getContentAsString()).isEqualTo(
        jsonApplicationDto.write(createApplicationDto()).getJson()
        );
    }
}
