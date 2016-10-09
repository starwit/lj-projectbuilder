package de.starwit.ljprojectbuilder.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class AbstractGenerator<E extends AbstractModule> implements Generator {

	public final static Logger LOG = Logger.getLogger(AbstractGenerator.class);
	
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
			
			Map<String, Object> data = fillTemplateGlobalParameter(setupBean);
			generateGlobal(setupBean, data);
			generateAdditionals(data);
			
			List<DomainEntity> domains = setupBean.getDomains();
			for (DomainEntity domain : domains) {
				if (domain.isSelected()) {
					data.putAll(fillTemplateDomainParameter(domain));
					generateDomain(setupBean, domain.getName(), data);
				}
			}
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			LOG.error("Inherit class of AbstractEntity could not be resolved.");
		}
	}

	protected void generateAdditionals(Map<String, Object> data) {
	}
	
	public abstract Map<String, Object> fillTemplateDomainParameter(DomainEntity domain);
	public abstract Map<String, Object> fillTemplateGlobalParameter(GeneratorDto setupBean);

	
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
	
	protected void generateGlobal(GeneratorDto setupBean, Map<String, Object> data) {
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
}
