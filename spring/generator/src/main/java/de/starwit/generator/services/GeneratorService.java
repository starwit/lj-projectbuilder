package de.starwit.generator.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.spring.persistence.entity.CodeTemplateEntity;
import de.spring.persistence.entity.DomainEntity;
import de.spring.persistence.entity.ProjectEntity;
import de.spring.persistence.exception.NotificationException;
import de.spring.persistence.response.ResponseCode;
import de.spring.persistence.response.ResponseMetadata;
import de.spring.persistence.validation.ValidationError;
import de.spring.service.impl.ProjectService;
import de.starwit.generator.config.Constants;
import de.starwit.generator.generator.EntityImports;
import find.FindClass;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * The generator connects configuration with templates and starts generation.
 * @author anett
 *
 * @param <E> different configuration for frontend, backend and business
 */
@Service
public class GeneratorService {

  final static Logger LOG = LoggerFactory.getLogger(GeneratorService.class);
	
	private final static String GENERATION ="###GENERATION###";
	
	@Autowired
	private ProjectService projectService;

	public void generate(Long projectId) throws de.spring.persistence.exception.NotificationException {
		ProjectEntity project = projectService.findById(projectId);
		Set<CodeTemplateEntity> codeTemplates = project.getTemplate().getCodeTemplates();
		Collection<DomainEntity> domains = project.getSelectedDomains();
		Map<String, Object> templateData = fillTemplateGlobalParameter(project);
		
		for (CodeTemplateEntity codeTemplate : codeTemplates) {
    		switch (codeTemplate.getType()) {
			case GLOBAL:
				generatePath(templateData, codeTemplate);
				generateGlobal(templateData, codeTemplate);
				break;
			case ADDITIONAL_CONTENT:
				generatePath(templateData, codeTemplate);
				generateAdditionalContent(templateData, codeTemplate);
				break;
			case DOMAIN: {
				for (DomainEntity domain : domains) {
					templateData.putAll(fillTemplateDomainParameter(domain));
					generatePath(templateData, codeTemplate);
					generateDomain(domain.getName(), templateData, codeTemplate);
				}
				break;
			}
			default:
				break;
			}
		}
	}
	
	/**
	 * Adds parameter for generations based on project data.
	 * @param setupBean - project data and generation configuration.
	 * @return parameter for freemarker
	 */
	private Map<String, Object> fillTemplateGlobalParameter(ProjectEntity project) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("project", project);
		data.put("projecthome", Constants.TMP_DIR);
		return data;
	}

	/**
	 * Adds parameter for domain specific generations.
	 * @param domain - basic for entities
	 * @return
	 */
	private Map<String, Object> fillTemplateDomainParameter(DomainEntity domain) {
		// Build the data-model
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("domain", domain);
		data.put("imports", EntityImports.gatherEntityImports(domain));
		return data;
	}
	
	protected void generatePath(Map<String, Object> data, CodeTemplateEntity codeTemplate) throws NotificationException {
		try {
			@SuppressWarnings("deprecation")
			Template codeTemplateTargetPath = new Template("codeTemplateTargetPath", new StringReader(codeTemplate.getTargetPath()),
		               new Configuration());
	        StringWriter output = new StringWriter();
	        codeTemplateTargetPath.process(data, output);
	        codeTemplate.setConcreteTargetPath(output.toString());
	        
			@SuppressWarnings("deprecation")
			Template codeTemplateTemplatePath = new Template("codeTemplateTemplatePath", new StringReader(codeTemplate.getTemplatePath()),
		               new Configuration());
	        output = new StringWriter();
	        codeTemplateTemplatePath.process(data, output);
	        codeTemplate.setConcreteTemplatePath(output.toString());
		} catch (IOException | TemplateException e) {
			LOG.error("Error during file writing: ", e);
			ResponseMetadata errorResponse = new ResponseMetadata(ResponseCode.ERROR, "error.generation.generatepath");
			throw new NotificationException(errorResponse);
		}
	}
	
	protected void generateGlobal(Map<String, Object> data, CodeTemplateEntity codeTemplate) throws NotificationException {
		try {
			String targetFileUrl = codeTemplate.getTargetFileUrl("");
			writeGeneratedFile(targetFileUrl, getTemplate(codeTemplate.getConcreteTemplatePath()), data, true);
		} catch (IOException | TemplateException e) {
			LOG.error("Error during file writing: ", e);
			ResponseMetadata errorResponse = new ResponseMetadata(ResponseCode.ERROR, "error.generation.generateglobal");
			throw new NotificationException(errorResponse);
		}
	}
	
	protected void generateAdditionalContent(Map<String, Object> data, CodeTemplateEntity codeTemplate) throws NotificationException {
		try {
			addLinesToFile(codeTemplate.getConcreteTargetPath() + Constants.FILE_SEP + codeTemplate.getFileNameSuffix(), getTemplate(codeTemplate.getConcreteTemplatePath()), data);
		} catch (IOException | TemplateException e) {
			LOG.error("Error during file writing: ", e);
			ResponseMetadata errorResponse = new ResponseMetadata(ResponseCode.ERROR, "error.generation.generateadditionalcontent");
			throw new NotificationException(errorResponse);
		}
	}
	
	protected void generateDomain(String domainName, Map<String, Object> data, CodeTemplateEntity codeTemplate) throws NotificationException {
		try {
			File targetPath = new File(codeTemplate.getConcreteTemplatePath());
			if (targetPath.exists()) {
				String targetFileUrl = codeTemplate.getTargetFileUrl(domainName);
				writeGeneratedFile(targetFileUrl, getTemplate(codeTemplate.getConcreteTemplatePath()), data, false);
			} else {
				List<ValidationError> validationErrors = new ArrayList<ValidationError>();
				ValidationError ve = new ValidationError(codeTemplate.getTargetPath(), "error.generation.codetemplate");
				validationErrors.add(ve);
				ResponseMetadata errorResponse = new ResponseMetadata(ResponseCode.NOT_VALID, "error.generation.templatemissing", validationErrors);
				throw new NotificationException(errorResponse);
			}

		} catch (IOException | TemplateException e) {
			LOG.error("Error during file writing: ", e.fillInStackTrace());
			ResponseMetadata errorResponse = new ResponseMetadata(ResponseCode.ERROR, e.getMessage());
			throw new NotificationException(errorResponse);
		}
	}
	
	public Template getTemplate(String templatePath)
			throws IOException, TemplateNotFoundException, MalformedTemplateNameException, ParseException {
		@SuppressWarnings("deprecation")
		Configuration cfg = new Configuration();
		if (templatePath.startsWith("classpath:")) {
			cfg.setClassForTemplateLoading(FindClass.class, ".." + Constants.FILE_SEP);
			templatePath = templatePath.replace("classpath:", "");
		} else {
			File templateFile = new File(templatePath);
			File cfgDir = templateFile.getParentFile();
			cfg.setDirectoryForTemplateLoading(cfgDir);	
			templatePath = templateFile.getName();
		}
		Template template = cfg.getTemplate(templatePath);
		return template;
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
