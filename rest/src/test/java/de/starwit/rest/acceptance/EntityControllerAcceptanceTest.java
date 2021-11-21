package de.starwit.rest.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import de.starwit.TestdataConstants;
import de.starwit.dto.ApplicationDto;
import de.starwit.dto.EntityDto;

public class EntityControllerAcceptanceTest extends AbstractControllerAcceptanceTest<EntityDto> {

    private static final String data = TestdataConstants.TESTDATA_ENTITY_DIR;

    private static final String restpath = "/api/entities/";

    private static final String appdata = TestdataConstants.TESTDATA_APP_DIR;

    private static final String apprestpath = "/api/apps/";

    private JacksonTester<ApplicationDto> jsonAppTester;

    private JacksonTester<EntityDto> jsonTester;

    @Override
    public Class<EntityDto> getDtoClass() {
        return EntityDto.class;
    }

    @Override
    public String getRestPath() {
        return restpath;
    }

    @Override
    public JacksonTester<EntityDto> getJsonTester() {
        return jsonTester;
    }

    @Override
    @Test
    public void canCreate() throws Exception {
        // given
        ApplicationDto appDto = readAppFromFile(appdata + "app.json");
        EntityDto dto = readFromFile(data + "entity.json");
        String requestObject = jsonAppTester.write(appDto).getJson();
        MockHttpServletResponse response = create(apprestpath, requestObject);
        Long appId = mapper.readValue(response.getContentAsString(), ApplicationDto.class).getId();

        // when
        requestObject = jsonTester.write(dto).getJson();
        response = create(restpath + "byApp/" + appId, requestObject);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        dto.setId(mapper.readValue(response.getContentAsString(), EntityDto.class).getId());
        assertThat(response.getContentAsString()).isEqualTo(jsonTester.write(dto).getJson());
    }

    @Override
    @Test
    public void canRetrieveById() throws Exception {

        // given
        ApplicationDto appDto = readAppFromFile(appdata + "app.json");
        EntityDto dto = readFromFile(data + "entity.json");
        appDto.getEntities().add(dto);
        String applicationString = jsonAppTester.write(appDto).getJson();
        MockHttpServletResponse response = create(apprestpath, applicationString);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ApplicationDto appDto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        dto.setId(appDto2.getEntities().get(0).getId());

        // when
        response = retrieveById(dto.getId());

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonTester.write(dto).getJson());
    }

    private MockHttpServletResponse create(String path, String applicationString) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(applicationString);

        MockHttpServletResponse response = mvc.perform(builder).andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }

    private MockHttpServletResponse update(String path, String requestObjectString) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(requestObjectString);

        MockHttpServletResponse response = mvc.perform(builder).andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }

    private ApplicationDto readAppFromFile(String path) throws Exception {
        try {
            URL res = getClass().getClassLoader().getResource(path);
            File file = new File(res.getFile());
            ApplicationDto dto = mapper.readValue(file, ApplicationDto.class);
            return dto;
        } catch (IOException e) {
            LOG.error("JSON mapper failed", e);
            throw new Exception("JSON mapper failed");
        }
    }

    @Override
    @Test
    public void canUpdate() throws Exception {
        // given
        ApplicationDto appDto = readAppFromFile(appdata + "app.json");
        EntityDto dto = readFromFile(data + "entity.json");
        appDto.getEntities().add(dto);
        String applicationString = jsonAppTester.write(appDto).getJson();
        MockHttpServletResponse response = create(apprestpath, applicationString);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ApplicationDto appDto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        dto.setId(appDto2.getEntities().get(0).getId());
        dto.setName("changed");

        String requestObject = jsonTester.write(dto).getJson();

        // when
        response = update(restpath + "byApp/" + appDto2.getId(), requestObject);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        dto.setId(mapper.readValue(response.getContentAsString(), EntityDto.class).getId());
        assertThat(response.getContentAsString()).isEqualTo(jsonTester.write(dto).getJson());
        assertThat(mapper.readValue(response.getContentAsString(), EntityDto.class).getName()).isEqualTo("changed");
    }

    @Override
    @Test
    public void canDelete() throws Exception {
        // given
        ApplicationDto appDto = readAppFromFile(appdata + "app.json");
        EntityDto dto = readFromFile(data + "entity.json");
        appDto.getEntities().add(dto);
        String applicationString = jsonAppTester.write(appDto).getJson();
        MockHttpServletResponse response = create(apprestpath, applicationString);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ApplicationDto appDto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        Long entityId = appDto2.getEntities().get(0).getId();
        response = retrieveById(entityId);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // when
        delete(entityId);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        response = retrieveById(entityId);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}