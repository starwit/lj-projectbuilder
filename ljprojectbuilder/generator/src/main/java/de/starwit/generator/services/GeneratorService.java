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
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import de.starwit.generator.config.Constants;
import de.starwit.generator.generator.EntityImports;
import de.starwit.ljprojectbuilder.config.GeneratorConfig;
import de.starwit.ljprojectbuilder.ejb.CodeTemplateService;
import de.starwit.ljprojectbuilder.ejb.ProjectService;
import de.starwit.ljprojectbuilder.entity.CodeTemplateEntity;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
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
@Local
@Stateless(name = "GeneratorService")
public class GeneratorService {

	public final static Logger LOG = Logger.getLogger(GeneratorService.class);
	
	private final static String GENERATION ="###GENERATION###";
	
	@Inject
	private CodeTemplateService codeTempateService;
	
	@Inject
	private ProjectService projectService;

	public void generate(Long projectId) {
		ProjectEntity project = projectService.findById(projectId);
		List<CodeTemplateEntity> codeTemplates = codeTempateService.findAllCodeTemplatesByProject(projectId);
		Map<String, Object> templateData = fillTemplateGlobalParameter(project);
		Collection<DomainEntity> domains = project.getSelectedDomains();
		
		for (CodeTemplateEntity codeTemplate : codeTemplates) {
			generatePath(templateData, codeTemplate);
    		switch (codeTemplate.getType()) {
			case GLOBAL:
				generateGlobal(templateData, codeTemplate);
				break;
			case ADDITIONAL_CONTENT:
				generateAdditionalContent(templateData, codeTemplate);
				break;
			case DOMAIN: {
				for (DomainEntity domain : domains) {
					templateData.putAll(fillTemplateDomainParameter(domain));
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
		data.put("templateSingle", GeneratorConfig.MAINTAIN_UI.suffix);
		data.put("templateAll", GeneratorConfig.ALL_UI.suffix);
		return data;
	}
	
	protected void generatePath(Map<String, Object> data, CodeTemplateEntity codeTemplate) {
		try {
			@SuppressWarnings("deprecation")
			Template t = new Template("codeTemplate", new StringReader(codeTemplate.getTargetPath()),
		               new Configuration());
	        StringWriter output = new StringWriter();
	        t.process(data, output);
 		codeTemplate.setConcreteTargetPath(output.toString());
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
			throw new RuntimeException(e);
		}
	}
	
	protected void generateGlobal(Map<String, Object> data, CodeTemplateEntity codeTemplate) {
		try {
			writeGeneratedFile(codeTemplate.getConcreteTargetPath(), getTemplate(codeTemplate.getTemplatePath()), data, true);
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
		}
	}
	
	protected void generateAdditionalContent(Map<String, Object> data, CodeTemplateEntity codeTemplate) {
		try {
			addLinesToFile(codeTemplate.getConcreteTargetPath() + Constants.FILE_SEP + codeTemplate.getFileNameSuffix(), getTemplate(codeTemplate.getTemplatePath()), data);
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
		}
	}
	
	protected void generateDomain(String domainName, Map<String, Object> data, CodeTemplateEntity codeTemplate) {
		try {
			String targetFileUrl = codeTemplate.getTargetFileUrl(domainName);
			writeGeneratedFile(targetFileUrl, getTemplate(codeTemplate.getTemplatePath()), data, false);
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
		}
	}
	
	public Template getTemplate(String templatePath)
			throws IOException, TemplateNotFoundException, MalformedTemplateNameException, ParseException {
		@SuppressWarnings("deprecation")
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(FindClass.class, ".." + Constants.FILE_SEP);
		Template template = cfg.getTemplate(templatePath);
		return template;
	}
	
	protected void writeGeneratedFile(String filepath, Template template, Map<String, Object> data, boolean override)
			throws IOException, TemplateException {
		// File output
		File outputFile = new File(filepath);
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
