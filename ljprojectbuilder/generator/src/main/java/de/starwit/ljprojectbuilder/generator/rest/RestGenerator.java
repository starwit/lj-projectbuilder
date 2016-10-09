package de.starwit.ljprojectbuilder.generator.rest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import freemarker.template.Template;
import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import logic.generators.Generator;
import logic.generators.GeneratorConfig;

public class RestGenerator extends Generator<RestModule> {

	public final static Logger LOG = Logger.getLogger(RestGenerator.class);
	
	@Override
	public Map<String, Object> fillTemplateParameter(GeneratorDto setupBean, DomainEntity domain) {
		if (setupBean.getProject() == null) {
			return null;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("appName", setupBean.getProject().getTitle());
		data.put("package", setupBean.getProject().getPackagePrefix());
		data.put("domain", domain.getName());
		return data;
	}
	
	@Override
	public void generateAdditionals(GeneratorDto setupBean, String domainName, Map<String, Object> data) {
		if (setupBean.getProject() == null) {
			return;
		}
				
		String packagePath = getModule().getModuleDir();
        generateRestfulApplications(setupBean, packagePath);
		
	}
	
	public void generateRestfulApplications(GeneratorDto setupBean, String packagePath) {

		File folder = new File(packagePath + "/" + GeneratorConfig.REST.targetPath);
		File[] listOfFiles = folder.listFiles();
		int l = listOfFiles.length;

		List<String> classnames = new ArrayList<String>(l);
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String value = file.getName().replace(".java", "");
				classnames.add(value);
			}
		}

		try {
			// Freemarker configuration object
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(Constants.TEMPLATE_PATH));
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("classnames", classnames);
			data.put("appName", setupBean.getProject().getTitle());
			data.put("package", setupBean.getProject().getPackagePrefix());
			
			// Load template from source folder
			Template template = cfg.getTemplate(GeneratorConfig.REST_APP.templateFile);
			File restapp = new File(packagePath + "/" + GeneratorConfig.REST_APP.targetPath + "/"
					+ GeneratorConfig.REST_APP.suffix);
		    Writer filewriter = new FileWriter (restapp);
		    template.process(data, filewriter);
		    filewriter.flush();
		    filewriter.close();
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
		}
	}

}
