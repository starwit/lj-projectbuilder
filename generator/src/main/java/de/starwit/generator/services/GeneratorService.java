package de.starwit.generator.services;

import de.starwit.dto.AppDto;
import de.starwit.generator.config.Constants;
import de.starwit.generator.generator.EntityImports;
import de.starwit.mapper.AppMapper;
import de.starwit.mapper.EntityMapper;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.Domain;
import de.starwit.persistence.entity.EnumDef;
import de.starwit.persistence.entity.TemplateFile;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.AppRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The generator connects configuration with templates and starts generation.
 *
 * @param <E> different configuration for frontend, backend and business
 * @author anett
 */
@Service
public class GeneratorService {

    static final Logger LOG = LoggerFactory.getLogger(GeneratorService.class);
    static final String FILE_WRITING_ERROR = "Error during File writing: ";

    private static final String GENERATION = "###GENERATION###";

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private AppRepository appRepository;

    public void generate(Long appId) throws NotificationException {
        LOG.debug("Gathering data for generation");

        App app = appRepository.findById(appId).orElseThrow();
        Set<TemplateFile> templateFiles = app.getTemplate().getTemplateFiles();
        Collection<Domain> domains = app.getDomains();
        Collection<EnumDef> enums = app.getEnumDefs();
        Map<String, Object> templateData = fillTemplateGlobalParameter(app);

        LOG.debug("Beginning to iterate through {} template files now...", templateFiles.size());

        for (TemplateFile templateFile : templateFiles) {
            LOG.debug("Starting with template {}", templateFile.getFileName());
            if (templateFile.isAppend()) {
                LOG.debug("{} is appending", templateFile.getFileName());
                generatePath(templateData, templateFile);
                generateAdditionalContent(templateData, templateFile);
            } else if (templateFile.getFileName().contains("${domain")
                    || templateFile.getFileName().contains("${entity")) {
                LOG.debug("Filename contains domain or entity.");

                for (Domain domain : domains) {
                    LOG.debug("Iterating through Domain {}", domain.getName());

                    templateData.putAll(fillTemplateDomainParameter(domain));
                    generatePath(templateData, templateFile);
                    generateFileWithOverride(templateData, templateFile);
                }
            } else if (templateFile.getFileName().contains("${enumDef")) {
                LOG.debug("Filename contains enumDef");

                for (EnumDef enumDef : enums) {
                    LOG.debug("Iterating through Enum {}", enumDef);

                    templateData.putAll(fillTemplateEnumParameter(enumDef));
                    generatePath(templateData, templateFile);
                    generateFileWithOverride(templateData, templateFile);
                }
            } else {
                LOG.debug("Does not contain anything");

                generatePath(templateData, templateFile);
                generateFileWithOverride(templateData, templateFile);
            }
        }
    }

    /**
     * Adds parameter for generations based on app data.
     *
     * @param setupBean - app data and generation configuration.
     * @return parameter for freemarker
     */
    private Map<String, Object> fillTemplateGlobalParameter(App appEntity) {
        Map<String, Object> data = new HashMap<>();
        // support old format
        data.put("project", appEntity);
        data.put("projecthome", Constants.TMP_DIR);
        AppDto appDto = appMapper.convertToDto(appEntity);

        // support new format
        data.put("app", appDto);
        data.put("apphome", Constants.TMP_DIR);
        return data;
    }

    /**
     * Adds parameter for domain specific generations.
     *
     * @param domain - basic for entities
     * @return
     */
    private Map<String, Object> fillTemplateDomainParameter(Domain domain) {
        // Build the data-model
        Map<String, Object> data = new HashMap<>();
        data.put("domain", domain);
        data.put("entity", entityMapper.convertToDto(domain));
        data.put("imports", EntityImports.gatherEntityImports(domain));
        return data;
    }

    /**
     * Adds parameter for enum specific generations.
     *
     * @param enum - basic for entities
     * @return
     */
    private Map<String, Object> fillTemplateEnumParameter(EnumDef enumDef) {
        // Build the data-model
        Map<String, Object> data = new HashMap<>();
        data.put("enumDef", enumDef);
        return data;
    }

    /**
     * Template Path can contain variables needed to be interpreted by freemarker.
     *
     * @param data         input parameters
     * @param templateFile templateFile with freemarker
     * @throws NotificationException
     */
    protected void generatePath(Map<String, Object> data, TemplateFile templateFile) throws NotificationException {
        String targetPath = templateFile.getTargetPath();
        if (!targetPath.startsWith(Constants.APP_HOME) || targetPath.startsWith(Constants.PROJECT_HOME)) {
            targetPath = Constants.TARGET_PATH_PREFIX + targetPath;
        }
        String concreteTargetPath = generatePathWithFreemarker(data, targetPath);
        templateFile.setConcreteTargetPath(concreteTargetPath);

        String templatePath = templateFile.getTemplatePath();
        if (!templatePath.startsWith(Constants.APP_HOME) || templatePath.startsWith(Constants.PROJECT_HOME)) {
            templatePath = Constants.TEMPLATE_PATH_PREFIX + templatePath;
        }
        String contreteTemplatePath = generatePathWithFreemarker(data, templatePath);
        templateFile.setConcreteTemplatePath(contreteTemplatePath);
    }

    protected String generatePathWithFreemarker(Map<String, Object> data, String path)
            throws NotificationException {
        try {
            @SuppressWarnings("deprecation")
            Template templateFileTargetPath = new Template("templatePath", new StringReader(path),
                    new Configuration());
            StringWriter output = new StringWriter();
            templateFileTargetPath.process(data, output);
            return output.toString();
        } catch (IOException e) {
            throw new NotificationException("error.generation.ioexception", e.getMessage());
        } catch (TemplateException e) {
            throw new NotificationException("error.generation.generatepath", e.getMessage());
        }
    }

    protected void generateFileWithOverride(Map<String, Object> data, TemplateFile templateFile)
            throws NotificationException {
        try {
            String targetFileUrl = templateFile.getConcreteTargetPath()
                    + generatePathWithFreemarker(data, templateFile.getFileName());
            writeGeneratedFile(targetFileUrl, getTemplate(templateFile.getConcreteTemplatePath()), data, true);
        } catch (TemplateNotFoundException e) {
            String templateUrl = generatePathWithFreemarker(data, templateFile.getFileName());
            throw new NotificationException("error.generation.templatenotfound",
                    "Template with path " + templateUrl + " not found", templateUrl);
        } catch (FileNotFoundException e) {
            throw new NotificationException("error.generation.filenotfound", e.getMessage(),
                    templateFile.getTargetPath(), templateFile.getTemplatePath());
        } catch (TemplateException e) {
            throw new NotificationException("error.generation.template", e.getMessage());
        } catch (IOException e) {
            throw new NotificationException("error.generation.generateglobal", e.getMessage());
        }
    }

    protected void generateAdditionalContent(Map<String, Object> data, TemplateFile templateFile)
            throws NotificationException {
        try {
            addLinesToFile(templateFile.getConcreteTargetPath() + Constants.FILE_SEP + templateFile.getFileName(),
                    getTemplate(templateFile.getConcreteTemplatePath()), data);
        } catch (TemplateException e) {
            throw new NotificationException("error.generation.template", e.getMessage());
        } catch (IOException e) {
            throw new NotificationException("error.generation.generateglobal", e.getMessage());
        }
    }

    public Template getTemplate(String templatePath)
            throws IOException {
        @SuppressWarnings("deprecation")
        Configuration cfg = new Configuration();
        File templateFile = new File(templatePath);
        File cfgDir = templateFile.getParentFile();
        cfg.setDirectoryForTemplateLoading(cfgDir);
        templatePath = templateFile.getName();
        return cfg.getTemplate(templatePath);
    }

    protected void writeGeneratedFile(String filepath, Template template, Map<String, Object> data, boolean override)
            throws IOException, TemplateException {
        // File output
        File outputFile = new File(filepath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        if (!outputFile.exists() || override) {
            Writer filewriter = new FileWriter(outputFile);
            template.process(data, filewriter);
            filewriter.flush();
            filewriter.close();
        }
    }

    private void addLinesToFile(String inputFilename, Template template, Map<String, Object> data)
            throws IOException, TemplateException {

        File inputFile = new File(inputFilename);
        File tempFile = new File(inputFilename + "_tmp");
        if (inputFile.exists() && inputFile.isFile()) {

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            Writer writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (null != currentLine) {
                    if (currentLine.contains(GENERATION)) {
                        template.process(data, writer);
                        writer.write(System.getProperty("line.separator"));
                    }
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
            writer.flush();
            writer.close();
            reader.close();
            inputFile.delete();
            Files.move(tempFile.toPath(), inputFile.toPath());
        }
    }
}
