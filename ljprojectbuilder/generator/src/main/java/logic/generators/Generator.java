package logic.generators;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.config.GeneratorConfig;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public abstract class Generator {

	public final static Logger LOG = Logger.getLogger(Generator.class);

	public final static String SRC_FRONTEND_PATH = "src/main/webapp/";
	public final static String SRC_JAVA_PATH = "src/main/java/de/";
	public final static String TEST_JAVA_PATH = "src/test/java/de/";
	public final static String TEST_RESOURCES_PATH = "src/test/resources/";

	public abstract void generate(GeneratorDto setupBean);

	protected void writeGeneratedFile(String filepath, Template template, Map<String, Object> data)
			throws IOException, TemplateException {

		// File output
		File serviceInterface = new File(filepath);
		if (!serviceInterface.exists()) {
			Writer filewriter = new FileWriter(serviceInterface);
			template.process(data, filewriter);
			filewriter.flush();
			filewriter.close();
		}
	}

	protected void generate(GeneratorDto setupBean, String packagePath, GeneratorConfig generatorConfig) {
		List<DomainEntity> domains = setupBean.getDomains();

		for (DomainEntity domain : domains) {
			// Build the data-model
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("domain", domain.getName());
			data.put("domainLower", domain.getName().toLowerCase());
			data.put("domainUpper", domain.getName().toUpperCase());
			data.put("appName", setupBean.getProject().getTitle());

			generate(setupBean, domain.getName(), packagePath, data, generatorConfig);
		}

	}

	protected void generate(GeneratorDto setupBean, String domainName, String packagePath, Map<String, Object> data,
			GeneratorConfig generatorConfig) {
		try {
			Template template = getTemplate(setupBean, generatorConfig);
			String domain = domainName;
			String name = domain + generatorConfig.suffix;
			writeGeneratedFile(packagePath + "/" + generatorConfig.targetPath + "/" + name, template, data);
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
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
		cfg.setDirectoryForTemplateLoading(new File(setupBean.getTemplatePath()));
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
