package de.starwit.rest.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

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

import de.starwit.TestdataConstants;
import de.starwit.dto.AppDto;
import de.starwit.dto.EntityDto;
import de.starwit.dto.SaveAppTemplateDto;
import de.starwit.mapper.AppTemplateMapper;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.service.impl.AppTemplateService;

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc(addFilters = false)
public class AppControllerAcceptanceTest extends AbstractControllerAcceptanceTest<AppDto> {

    final static Logger LOG = LoggerFactory.getLogger(AppControllerAcceptanceTest.class);

    private static final String data = TestdataConstants.TESTDATA_APP_DIR;

    private static final String restpath = "/api/apps/";

    @Autowired
    public AppTemplateService appTemplateService;

    @Autowired
    public AppTemplateMapper appTemplateMapper;

    private AppTemplate template;

    private List<String> groups;

    private JacksonTester<AppDto> jsonTester;

    @Override
    public Class<AppDto> getDtoClass() {
        return AppDto.class;
    }

    @Override
    public String getRestPath() {
        return restpath;
    }

    @Override
    public JacksonTester<AppDto> getJsonTester() {
        return jsonTester;
    }

    @BeforeEach
    private void createAppTemplate() throws Exception {
        if (template == null) {
            SaveAppTemplateDto appTemplateDto = (SaveAppTemplateDto) readFromFile(
                    TestdataConstants.TESTDATA_APPTEMPLATE_DIR + "apptemplate.json",
                    SaveAppTemplateDto.class);
            groups = new ArrayList<>();
            groups.add("public");
            appTemplateDto.setGroups(groups);
            template = appTemplateService.saveOrUpdate(appTemplateMapper.convertToEntity(appTemplateDto));
        }
    }

    @Test
    public void canCreate() throws Exception {
        // given
        AppDto dto = readFromFile(data + "app.json");
        dto.setTemplate(appTemplateMapper.convertToDto(template));

        // when
        MockHttpServletResponse response = create(dto);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        AppDto responseDto = jsonTester.parseObject(response.getContentAsString());
        dto.setId(responseDto.getId());
        dto.setGroupsToAssign(groups);
        String applicationString = jsonTester.write(dto).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseDto.getTemplate().getId()).isEqualTo(template.getId());
        responseDto.setTemplate(appTemplateMapper.convertToDto(template));
        String responseString = jsonTester.write(responseDto).getJson();
        assertThat(responseString).isEqualTo(applicationString);
    }

    @Test
    public void canCreateTest() throws Exception {
        // given
        String jsonString = readJsonStringFromFile(data + "app-test.json");
        // when
        MockHttpServletResponse response = createFromString(jsonString);
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void canCreateTest2() throws Exception {
        // given
        String jsonString = readJsonStringFromFile(data + "app-test2.json");
        // when
        MockHttpServletResponse response = createFromString(jsonString);
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void isValidated() throws Exception {
        // given
        AppDto dto = readFromFile(data + "app-wrong-format.json");
        dto.setTemplate(appTemplateMapper.convertToDto(template));

        // when
        MockHttpServletResponse response = create(dto);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(
                "{\"messageKey\":\"error.methodArgumentNotValid\",\"message\":\"{entities[0].name=must not be blank}\"}");
    }

    @Test
    public void isValidatedInputParam() throws Exception {
        // when
        MockHttpServletResponse response = mvc
                .perform(get(getRestPath() + "/wrongvalue").contentType(MediaType.APPLICATION_JSON)).andReturn()
                .getResponse();

        // then
        LOG.info(response.getContentAsString());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(
                "{\"messageKey\":\"error.wrongInputValue\",\"message\":\"Wrong input value wrongvalue. Failed to convert value of type String to required type Long.\"}");
    }

    @Test
    public void canNotFindById() throws Exception {
        // when
        MockHttpServletResponse response = mvc
                .perform(get(getRestPath() + "/4242").contentType(MediaType.APPLICATION_JSON)).andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"messageKey\":\"error.app.notfound\",\"message\":\"App not found.\"}");
    }

    @Test
    public void canRetrieveById() throws Exception {
        // given
        AppDto dto = readFromFile(data + "app.json");
        MockHttpServletResponse response = create(dto);
        AppDto dto2 = mapper.readValue(response.getContentAsString(), AppDto.class);

        // when
        response = retrieveById(dto2.getId());

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonTester.write(dto2).getJson());
    }

    @Test
    public void canRetrieveByIdWithFields() throws Exception {
        // given
        AppDto dto = readFromFile(data + "app-with-fields.json");
        MockHttpServletResponse response = create(dto);
        AppDto dto2 = mapper.readValue(response.getContentAsString(), AppDto.class);

        // when
        response = retrieveById(dto2.getId());

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        AppDto result = mapper.readValue(response.getContentAsString(), AppDto.class);
        assertThat(jsonTester.write(result).getJson()).isEqualTo(jsonTester.write(dto2).getJson());
    }

    @Test
    public void canUpdate() throws Exception {

        // given
        AppDto dto = readFromFile(data + "app-with-fields.json");
        MockHttpServletResponse response = create(dto);
        AppDto dto2 = mapper.readValue(response.getContentAsString(), AppDto.class);
        dto2.setBaseName("secondName");
        EntityDto entityDto = dto2.getEntities().get(0);
        entityDto.setName("SecondEntityName");
        int fieldcount = entityDto.getFields().size();
        entityDto.getFields().get(0).setFieldName("secondFieldName");
        entityDto.getFields().remove(1);

        // when
        response = update(dto2);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        AppDto result = mapper.readValue(response.getContentAsString(), AppDto.class);
        assertThat(result.getId()).isEqualTo(dto2.getId());
        assertThat(result.getBaseName()).isEqualTo("secondName");
        assertThat(result.getEntities().get(0).getFields().size()).isEqualTo(fieldcount - 1);
        assertThat(result.getEntities().get(0).getId()).isEqualTo(entityDto.getId());
        assertThat(result.getEntities().get(0).getName()).isEqualTo("SecondEntityName");
        assertThat(result.getEntities().get(0).getFields().get(0).getId())
                .isEqualTo(entityDto.getFields().get(0).getId());
        assertThat(result.getEntities().get(0).getFields().get(0).getFieldName()).isEqualTo("secondFieldName");

        assertThat(response.getContentAsString()).isEqualTo(jsonTester.write(dto2).getJson());
    }

    @Override
    @Test
    public void canDelete() throws Exception {
        // given
        AppDto dto = readFromFile(data + "app.json");
        MockHttpServletResponse response = create(dto);
        AppDto dto2 = mapper.readValue(response.getContentAsString(), AppDto.class);
        response = retrieveById(dto2.getId());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // when
        delete(dto2.getId());

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        response = retrieveById(dto2.getId());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
