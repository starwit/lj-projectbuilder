package logic.generators;

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

import de.starwit.ljprojectbuilder.config.GeneratorConfig;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class RestGenerator extends Generator {

	public final static Logger LOG = Logger.getLogger(RestGenerator.class);
	
	public void generate(GeneratorDto setupBean) {
		if (setupBean.getProject() == null) {
			return;
		}
				
		String packagePath = setupBean.getProject().getTargetPath() + "/" + setupBean.getProject().getTitle() + "/rest/" + Generator.SRC_JAVA_PATH + "/" + setupBean.getProject().getTitle();
        GeneratorConfig generatorConfig = GeneratorConfig.REST;
        generate(setupBean, packagePath, generatorConfig);
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
			cfg.setDirectoryForTemplateLoading(new File(setupBean.getTemplatePath()));
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("classnames", classnames);
			data.put("appName", setupBean.getProject().getTitle());
			
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
