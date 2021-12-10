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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.generator.config.Constants;
import de.starwit.generator.generator.EntityImports;
import de.starwit.persistence.entity.TemplateFile;
import de.starwit.persistence.entity.Domain;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.AppRepository;
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
	private AppRepository AppRepository;

	public void generate(Long appId) throws de.starwit.persistence.exception.NotificationException {
		App app = AppRepository.findById(appId).orElseThrow();
		Set<TemplateFile> templateFiles = app.getTemplate().getTemplateFiles();
		Collection<Domain> domains = app.getDomains();
		Map<String, Object> templateData = fillTemplateGlobalParameter(app);
		
		for (TemplateFile templateFile : templateFiles) {
    		switch (templateFile.getType()) {
			case GLOBAL:
				generatePath(templateData, templateFile);
				generateGlobal(templateData, templateFile);
				break;
			case ADDITIONAL_CONTENT:
				generatePath(templateData, templateFile);
				generateAdditionalContent(templateData, templateFile);
				break;
			case DOMAIN: {
				for (Domain domain : domains) {
					templateData.putAll(fillTemplateDomainParameter(domain));
					generatePath(templateData, templateFile);
					generateDomain(domain.getName(), templateData, templateFile);
				}
				break;
			}
			default:
				break;
			}
		}
	}
	
	/**
	 * Adds parameter for generations based on app data.
	 * @param setupBean - app data and generation configuration.
	 * @return parameter for freemarker
	 */
	private Map<String, Object> fillTemplateGlobalParameter(App app) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("app", app);
		data.put("apphome", Constants.TMP_DIR);
		return data;
	}

	/**
	 * Adds parameter for domain specific generations.
	 * @param domain - basic for entities
	 * @return
	 */
	private Map<String, Object> fillTemplateDomainParameter(Domain domain) {
		// Build the data-model
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("domain", domain);
		data.put("imports", EntityImports.gatherEntityImports(domain));
		return data;
	}
	
	protected void generatePath(Map<String, Object> data, TemplateFile templateFile) throws NotificationException {
		try {
			@SuppressWarnings("deprecation")
			Template templateFileTargetPath = new Template("templateFileTargetPath", new StringReader(templateFile.getTargetPath()),
		               new Configuration());
	        StringWriter output = new StringWriter();
	        templateFileTargetPath.process(data, output);
	        templateFile.setConcreteTargetPath(output.toString());
	        
			@SuppressWarnings("deprecation")
			Template templateFileTemplatePath = new Template("templateFileTemplatePath", new StringReader(templateFile.getTemplatePath()),
		               new Configuration());
	        output = new StringWriter();
	        templateFileTemplatePath.process(data, output);
	        templateFile.setConcreteTemplatePath(output.toString());
		} catch (IOException | TemplateException e) {
			LOG.error("Error during file writing: ", e);
			throw new NotificationException("error.generation.generatepath", "Error during file writing.");
		}
	}
	
	protected void generateGlobal(Map<String, Object> data, TemplateFile templateFile) throws NotificationException {
		try {
			String targetFileUrl = templateFile.getTargetFileUrl("");
			writeGeneratedFile(targetFileUrl, getTemplate(templateFile.getConcreteTemplatePath()), data, true);
		} catch (IOException | TemplateException e) {
			LOG.error("Error during file writing: ", e);
			throw new NotificationException("error.generation.generateglobal", "Error during file writing");
		}
	}
	
	protected void generateAdditionalContent(Map<String, Object> data, TemplateFile templateFile) throws NotificationException {
		try {
			addLinesToFile(templateFile.getConcreteTargetPath() + Constants.FILE_SEP + templateFile.getFileNameSuffix(), getTemplate(templateFile.getConcreteTemplatePath()), data);
		} catch (IOException | TemplateException e) {
			LOG.error("Error during file writing: ", e);
			throw new NotificationException("error.generation.generateadditionalcontent", "Error during file writing");
		}
	}
	
	protected void generateDomain(String domainName, Map<String, Object> data, TemplateFile templateFile) throws NotificationException {
		try {
			File targetPath = new File(templateFile.getConcreteTemplatePath());
			if (targetPath.exists()) {
				String targetFileUrl = templateFile.getTargetFileUrl(domainName);
				writeGeneratedFile(targetFileUrl, getTemplate(templateFile.getConcreteTemplatePath()), data, false);
			} else {
				throw new NotificationException("error.generation.templatemissing", "CodeTemplate missing.");
			}

		} catch (IOException e) {
			LOG.error("Error during file writing: ", e.fillInStackTrace());
			throw new NotificationException("error.generation.file", "Error during file writing.");
		} catch (TemplateException e) {
			LOG.error("Error during file writing: ", e.fillInStackTrace());
			throw new NotificationException("error.generation.template", "Error during file writing.");
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
