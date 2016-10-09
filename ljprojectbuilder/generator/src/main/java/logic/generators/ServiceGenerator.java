package logic.generators;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ServiceGenerator extends Generator {
	
	public final static Logger LOG = Logger.getLogger(ServiceGenerator.class);

	public void generate(GeneratorDto setupBean) {
		if (setupBean.getProject() == null) {
			return;
		}
		
		String packagePath = setupBean.getProject().getTargetPath() + "/" + setupBean.getProject().getTitle() + "/persistence/" + Generator.SRC_JAVA_PATH 
				+ setupBean.getProject().getPackagePrefix() + "/"
				+ setupBean.getProject().getTitle();
		String packageTestPath = setupBean.getProject().getTargetPath() + "/" + setupBean.getProject().getTitle() + "/persistence/" + Generator.TEST_JAVA_PATH 
				+ setupBean.getProject().getPackagePrefix() + "/"
				+ setupBean.getProject().getTitle();
		String packageTestResourcePath = setupBean.getProject().getTargetPath() + "/" + setupBean.getProject().getTitle() + "/persistence/" + Generator.TEST_RESOURCES_PATH;
		
        GeneratorConfig generatorConfig_I = GeneratorConfig.SERVICE_INTERFACE;
        GeneratorConfig generatorConfig_S = GeneratorConfig.SERVICE_IMPL;
        GeneratorConfig generatorConfig_T = GeneratorConfig.SERVICE_TEST;
        GeneratorConfig generatorConfig_JT = GeneratorConfig.JUNITTESTDATA;
        List<DomainEntity> domains = setupBean.getDomains();

        for (DomainEntity domain : domains) {

	        try {
	            // Build the data-model
	            Map<String, Object> data = new HashMap<String, Object>();
	            data.put("domain", domain.getName());
	            data.put("domainLower", domain.getName().toLowerCase());
	            data.put("domainUpper", domain.getName().toUpperCase());
	            data.put("appName", setupBean.getProject().getTitle());
				data.put("package", setupBean.getProject().getPackagePrefix());
	            
	            //loadTemplates and write files from template
	            Template template_I = getTemplate(setupBean, generatorConfig_I);
	    		String name = domain.getName() + generatorConfig_I.suffix;
	            writeGeneratedFile(packagePath + "/" + generatorConfig_I.targetPath + "/" + name + "/", template_I, data);
	            
	            Template template_S = getTemplate(setupBean, generatorConfig_S);
	    		name = domain.getName() + generatorConfig_S.suffix;
	            writeGeneratedFile(packagePath + "/" + generatorConfig_S.targetPath + "/" + name + "/", template_S, data);
	            
	            Template template_T = getTemplate(setupBean, generatorConfig_T);
	    		name = domain.getName() + generatorConfig_T.suffix;
	            writeGeneratedFile(packageTestPath + "/" + generatorConfig_T.targetPath + "/" + name + "/", template_T, data);
	            
	            Template template_JT = getTemplate(setupBean, generatorConfig_JT);
	    		name = domain.getName() + generatorConfig_JT.suffix;
	            writeGeneratedFile(packageTestResourcePath + "/" + generatorConfig_JT.targetPath + "/" + name + "/", template_JT, data);
	            
	            writeImportExportProterties(domain.getName(), packageTestResourcePath, generatorConfig_JT);
	            
	        } catch (IOException e) {
	        	LOG.error("Error during file writing: ", e);
	        } catch (TemplateException e) {
	        	LOG.error("Error generation Template:", e);
	        }
		
        }

	}

}
