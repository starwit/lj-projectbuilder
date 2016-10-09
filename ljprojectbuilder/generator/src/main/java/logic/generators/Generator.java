package logic.generators;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.generator.AbstractModule;
import de.starwit.ljprojectbuilder.generator.TemplateDef;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public abstract class Generator<E extends AbstractModule> {

	public final static Logger LOG = Logger.getLogger(Generator.class);
	
	private E module;
	
	
	@SuppressWarnings("unchecked")
	public E getModule() {
		return module;
	}


	@SuppressWarnings("unchecked")
	public void generate(GeneratorDto setupBean) {
		try {
			module = (E) ((Class<?>) ((ParameterizedType) this
					.getClass().getGenericSuperclass())
					.getActualTypeArguments()[0]).getDeclaredConstructor( GeneratorDto.class ).newInstance(setupBean);
			
			List<DomainEntity> domains = setupBean.getDomains();
			for (DomainEntity domain : domains) {
				Map<String, Object> data = fillTemplateParameter(setupBean, domain);
				generate(setupBean, domain.getName(), data);
				generateAdditionals(setupBean, domain.getName(), data);
			}
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			LOG.error("Inherit class of AbstractEntity could not be resolved.");
		}
	}

	protected void generateAdditionals(GeneratorDto setupBean, String domainName, Map<String, Object> data) {
	}
	
	public abstract Map<String, Object> fillTemplateParameter(GeneratorDto setupBean, DomainEntity domain);

	protected void generate(GeneratorDto setupBean, String domainName, Map<String, Object> data) {
		try {
			for (TemplateDef templateDef : getModule().getTemplates()) {
				writeGeneratedFile(templateDef.getTargetFileUrl(domainName), templateDef.getTemplate(), data);
			}
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
		}
	}
	
	protected void writeGeneratedFile(String filepath, Template template, Map<String, Object> data)
			throws IOException, TemplateException {
		// File output
		File outputFile = new File(filepath);
		if (!outputFile.exists()) {
			Writer filewriter = new FileWriter(outputFile);
			template.process(data, filewriter);
			filewriter.flush();
			filewriter.close();
		}
	}
	
	protected void generateInDomainFolder(GeneratorDto setupBean, String domainName, String packagePath, Map<String, Object> data,
			GeneratorConfig generatorConfig) {
		try {
			Template template = getTemplate(setupBean, generatorConfig);

			String domain = domainName;
			String viewPath = checkOrCreateViewPath(domain, packagePath, generatorConfig);
			if (viewPath != null) {
				String name = domain.toLowerCase() + generatorConfig.suffix;
				writeGeneratedFile(viewPath + "/" + name, template, data);
			}
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
		}
	}
	
	private String checkOrCreateViewPath(String domain, String packagePath, GeneratorConfig generatorConfig) {
		String viewPath = packagePath + "/" + generatorConfig.targetPath + "/" + domain.toLowerCase();
		File directory = new File(viewPath);
		boolean success = true;
		if (!directory.exists()) {
			success = (new File(viewPath).mkdirs());
		}
		if (success) {
			return viewPath;
		}
		return null;
	}

	protected Template getTemplate(GeneratorDto setupBean, GeneratorConfig generatorConfig)
			throws IOException, TemplateNotFoundException, MalformedTemplateNameException, ParseException {
		@SuppressWarnings("deprecation")
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(Constants.TEMPLATE_PATH));
		Template template = cfg.getTemplate(generatorConfig.templateFile);
		return template;
	}

	protected void writeImportExportProterties(String domain, String packagePath, GeneratorConfig generatorConfig) {
		Properties prop = new Properties();
		final String configFileUrl = packagePath + "/" + generatorConfig.targetPath + "/" + "importExport.properties";
		try {
			File f = new File(configFileUrl);
			FileInputStream is = new FileInputStream(f);
			prop.load(is);
			String loadedEntries = prop.getProperty("loadedEntities");
			if (loadedEntries == null || !loadedEntries.contains("," + domain + "Entity")) {
				loadedEntries = loadedEntries + "," + domain + "Entity";
			}
			prop.setProperty("loadedEntities", loadedEntries);
			OutputStream out = new FileOutputStream(configFileUrl);
			prop.store(out, "This is an optional header comment string");
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		}
	}

}
