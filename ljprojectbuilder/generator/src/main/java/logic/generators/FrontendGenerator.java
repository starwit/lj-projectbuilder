package logic.generators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Template;

import de.starwit.ljprojectbuilder.config.GeneratorConfig;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class FrontendGenerator extends Generator {
	
	private final static String BEGIN_GENERATION ="###BEGIN###";
	private final static String END_GENERATION ="###END###";
			

	@Override
	public void generate(GeneratorDto setupBean) {
		if (setupBean.getProject() == null) {
			return;
		}
		
		String packagePath = setupBean.getProject().getTargetPath() + "/" + setupBean.getProject().getTitle() + "/webclient/" + Generator.SRC_FRONTEND_PATH;
		
		List<DomainEntity> domains = setupBean.getDomains();
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("appName", setupBean.getProject().getTitle());
		data.put("templateSingle", GeneratorConfig.MAINTAIN_UI.suffix);
		data.put("templateAll", GeneratorConfig.ALL_UI.suffix);
		
		for (DomainEntity domain : domains) {
			data.put("domain", domain.getName());
			data.put("attributes", domain.getAttributes());
			generateInDomainFolder(setupBean, domain.getName(), packagePath, data, GeneratorConfig.ALL_UI);
			generateInDomainFolder(setupBean, domain.getName(), packagePath, data, GeneratorConfig.MAINTAIN_UI);
			
			generateInDomainFolder(setupBean, domain.getName(), packagePath, data, GeneratorConfig.CONFIG_UI);
			generateInDomainFolder(setupBean, domain.getName(), packagePath, data, GeneratorConfig.CONTROLLER_UI);
			
			generateWithLowercaseFilename(setupBean, domain.getName(), packagePath, data, GeneratorConfig.CONNECTOR_UI);
			generateGeneralFile(setupBean, packagePath, data, GeneratorConfig.VIEWS_UI);
			generateGeneralFile(setupBean, packagePath, data, GeneratorConfig.SCRIPT_BINDING);
			generateGeneralFile(setupBean, packagePath, data, GeneratorConfig.MENU_UI);
			
			generateGeneralFile(setupBean, packagePath, data, GeneratorConfig.TRANSLATION_UI);
		}
	}
	
	private void generateWithLowercaseFilename(GeneratorDto setupBean, String domainName, String packagePath, Map<String, Object> data, GeneratorConfig config) {
		try {
			
			Template template = getTemplate(setupBean, config);
			String domain = domainName.toLowerCase();
			String name = domain + config.suffix;
			writeGeneratedFile(packagePath + "/" + config.targetPath + "/" + name, template, data);
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
		}
	}
	
	private void generateGeneralFile(GeneratorDto setupBean, String packagePath, Map<String, Object> data, GeneratorConfig config) {
		
		String restPath = packagePath + "/" + GeneratorConfig.CONFIG_UI.getTargetPath();
		File folder = new File(restPath);
		File[] listOfFiles = folder.listFiles();
		int l = listOfFiles.length;
		
		List<String> domainnames = new ArrayList<String>(l);
		for (File file : listOfFiles) {
			if (file.isDirectory()) {
				domainnames.add(file.getName());
			}
		}

		try {
			// Freemarker configuration object
			@SuppressWarnings("deprecation")
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(setupBean.getTemplatePath()));
			data.put("domainnames", domainnames);
			
			Template template = getTemplate(setupBean, config);
			String inputFilename = packagePath + "/" + config.targetPath + "/" + config.getSuffix();
			String tempFilename = packagePath + "/" + config.targetPath + "/" + "temp_" + config.getSuffix();
			addLinesToFile(inputFilename, tempFilename, template, data);
		} catch (IOException e) {
			LOG.error("Error during file writing: ", e);
		} catch (TemplateException e) {
			LOG.error("Error generation Template:", e);
		}
	}
	
	private void addLinesToFile(String inputFilename, String tempFilename, Template template, Map<String, Object> data)
			throws IOException, TemplateException {

		File inputFile = new File(inputFilename);
		File tempFile = new File(tempFilename);
		if (inputFile.exists() && inputFile.isFile()) {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			Writer writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			boolean copyContentFromFile = true;

			while ((currentLine = reader.readLine()) != null) {
				if (null != currentLine) {
					if (currentLine.contains(END_GENERATION)) {
						copyContentFromFile = true;
					}
					if (copyContentFromFile) {
						writer.write(currentLine + System.getProperty("line.separator"));
					}
					if (currentLine.contains(BEGIN_GENERATION)) {
						copyContentFromFile = false;
						template.process(data, writer);
						writer.write(System.getProperty("line.separator"));
					}
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
