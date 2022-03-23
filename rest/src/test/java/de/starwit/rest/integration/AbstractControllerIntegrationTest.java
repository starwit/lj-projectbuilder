package de.starwit.rest.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.starwit.persistence.entity.AbstractEntity;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockUser(username = "admin", roles = {"ADMIN", "PBUSER"})
@WebMvcTest()
@Import({})
public abstract class AbstractControllerIntegrationTest<DTO extends AbstractEntity<Long>> {

    final static Logger LOG = LoggerFactory.getLogger(AbstractControllerIntegrationTest.class);

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;


    @BeforeEach
    public void setup() {
        // create Object Mapper
        mapper = new ObjectMapper();
        JacksonTester.initFields(this, new ObjectMapper());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public abstract Class<DTO> getDtoClass();

    public abstract String getRestPath();

    public abstract void canRetrieveById() throws Exception;

    protected DTO readFromFile(String path) throws Exception {
        try {
            URL res = getClass().getClassLoader().getResource(path);
            File file = new File(res.getFile());
            DTO dto = mapper.readValue(file, getDtoClass());
            return dto;
        } catch (IOException e) {
            LOG.error("JSON mapper failed", e);
            throw new Exception("JSON mapper failed");
        }
    }

    protected MockHttpServletResponse retrieveById(Long id) throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                        get(getRestPath() + id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }

}
