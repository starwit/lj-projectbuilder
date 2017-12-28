package de.starwit.generator.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.starwit.generator.config.TemplateDef;
import de.starwit.generator.dto.GeneratorDto;
import de.starwit.generator.modules.AbstractModule;
import de.starwit.ljprojectbuilder.config.GeneratorConfig;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * The generator connects configuration with templates and starts generation.
 * @author anett
 *
 * @param <E> different configuration for frontend, backend and business
 */
public class CommonGenerator<E extends AbstractModule> implements Generator {

	public final static Logger LOG = Logger.getLogger(CommonGenerator.class);
	
	private final static String GENERATION ="###GENERATION###";
	
	private E module;
	
	
	public E getModule() {
		return module;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void generate(GeneratorDto setupBean) {
		try {
			module = (E) ((Class<?>) ((ParameterizedType) this
					.getClass().getGenericSuperclass())
					.getActualTypeArguments()[0]).getDeclaredConstructor( GeneratorDto.class ).newInstance(setupBean);
			
			Map<String, Object> templateData = fillTemplateGlobalParameter(setupBean.getProject());
			generateGlobal(templateData);
			generateAdditionalContent(templateData);
			
			Collection<DomainEntity> domains = setupBean.getProject().getSelectedDomains();
			for (DomainEntity domain : domains) {
					templateData.putAll(fillTemplateDomainParameter(domain));
					generateDomain(setupBean, domain.getName(), templateData);
			}
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			LOG.error("Inherit class of AbstractEntity could not be resolved.");
		}
	}
	
	/**
	 * Adds parameter for generations based on project data.
	 * @param setupBean - project data and generation configuration.
	 * @return parameter for freemarker
	 */
	public Map<String, Object> fillTemplateGlobalParameter(ProjectEntity project) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("project", project);
		return data;
	}

	/**
	 * Adds parameter for domain specific generations.
	 * @param domain - basic for entities
	 * @return
	 */
	public Map<String, Object> fillTemplateDomainParameter(DomainEntity domain) {
		// Build the data-model
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("domain", domain);
		data.put("imports", EntityImports.gatherEntityImports(domain));
		data.put("templateSingle", GeneratorConfig.MAINTAIN_UI.suffix);
		data.put("templateAll", GeneratorConfig.ALL_UI.suffix);
		return data;
	}
	
	protected void generateDomain(GeneratorDto setupBean, String domainName, Map<String, Object> data) {
		try {
			for (TemplateDef templateDef : getModule().getDomainTemplates()) {
				String targetFileUrl = templateDef.getTargetFileUrl(domainName);
				writeGeneratedFile(targetFileUrl, templateDef.getTemplate(), data, false);
			}
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
		}
	}
	
	protected void generateGlobal(Map<String, Object> data) {
		try {
			for (TemplateDef templateDef : getModule().getGlobalTemplates()) {
				writeGeneratedFile(templateDef.getTargetFileUrl(), templateDef.getTemplate(), data, true);
			}
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
		}
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

	protected void generateAdditionalContent(Map<String, Object> data) {
		addContentToFiles(data);
	}
	
	private void addContentToFiles(Map<String, Object> data) {
		try {
			for (TemplateDef templateDef : getModule().getAdditionalContentTemplates()) {
				addLinesToFile(templateDef.getTargetFileUrl(), templateDef.getTemplate(), data);
			}
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
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
