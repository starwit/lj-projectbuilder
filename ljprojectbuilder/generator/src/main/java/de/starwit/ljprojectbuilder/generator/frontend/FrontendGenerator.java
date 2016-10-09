package de.starwit.ljprojectbuilder.generator.frontend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import de.starwit.ljprojectbuilder.config.GeneratorConfig;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.generator.AbstractGenerator;
import de.starwit.ljprojectbuilder.generator.TemplateDef;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FrontendGenerator extends AbstractGenerator<FrontendModule> {
	
	private final static String BEGIN_GENERATION ="###BEGIN###";
	private final static String END_GENERATION ="###END###";
	
	@Override
	public Map<String, Object> fillTemplateDomainParameter(DomainEntity domain) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("domain", domain.getName());
		data.put("attributes", domain.getAttributes());
		return data;
	}
	
	@Override
	public Map<String, Object> fillTemplateGlobalParameter(GeneratorDto setupBean) {
		if (setupBean.getProject() == null) {
			return null;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("appName", setupBean.getProject().getTitle());
		data.put("templateSingle", GeneratorConfig.MAINTAIN_UI.suffix);
		data.put("templateAll", GeneratorConfig.ALL_UI.suffix);
		data.put("package", setupBean.getProject().getPackagePrefix());
		data.put("domainnames", getModule().getDomainnames() );
		return data;
	}

	@Override
	protected void generateAdditionals(Map<String, Object> data) {
		addContentToFiles(data);
	}
	
	private void addContentToFiles(Map<String, Object> data) {
		try {
			for (TemplateDef templateDef : getModule().getGlobalTemplates()) {
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
