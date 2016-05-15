package logic.generators;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import frontend.beans.GeneratorSetupBean;
import logic.GeneratorConfig;

public class ServiceGenerator extends Generator {
	
	public final static Logger LOG = Logger.getLogger(ServiceGenerator.class);

	public void generate(GeneratorSetupBean setupBean) {
		String packagePath = setupBean.getProjectPath() + "/" + setupBean.getProjectName() + "/persistence/" + Generator.SRC_JAVA_PATH + setupBean.getProjectName();
		String packageTestPath = setupBean.getProjectPath() + "/" + setupBean.getProjectName() + "/persistence/" + Generator.TEST_JAVA_PATH + setupBean.getProjectName();
		String packageTestResourcePath = setupBean.getProjectPath() + "/" + setupBean.getProjectName() + "/persistence/" + Generator.TEST_RESOURCES_PATH;
		
        GeneratorConfig generatorConfig_I = GeneratorConfig.SERVICE_INTERFACE;
        GeneratorConfig generatorConfig_S = GeneratorConfig.SERVICE_IMPL;
        GeneratorConfig generatorConfig_T = GeneratorConfig.SERVICE_TEST;
        GeneratorConfig generatorConfig_JT = GeneratorConfig.JUNITTESTDATA;
        String domain = setupBean.getDomainName();

        try {
            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("domain", domain);
            data.put("domainLower", domain.toLowerCase());
            data.put("domainUpper", domain.toUpperCase());
            data.put("appName", setupBean.getProjectName());
            
            //loadTemplates and write files from template
            Template template_I = getTemplate(setupBean, generatorConfig_I);
    		String name = domain + generatorConfig_I.suffix;
            writeGeneratedFile(packagePath + "/" + generatorConfig_I.targetPath + "/" + name + "/", template_I, data);
            
            Template template_S = getTemplate(setupBean, generatorConfig_S);
    		name = domain + generatorConfig_S.suffix;
            writeGeneratedFile(packagePath + "/" + generatorConfig_S.targetPath + "/" + name + "/", template_S, data);
            
            Template template_T = getTemplate(setupBean, generatorConfig_T);
    		name = domain + generatorConfig_T.suffix;
            writeGeneratedFile(packageTestPath + "/" + generatorConfig_T.targetPath + "/" + name + "/", template_T, data);
            
            Template template_JT = getTemplate(setupBean, generatorConfig_JT);
    		name = domain + generatorConfig_JT.suffix;
            writeGeneratedFile(packageTestResourcePath + "/" + generatorConfig_JT.targetPath + "/" + name + "/", template_JT, data);
            
            writeImportExportProterties(domain, packageTestResourcePath, generatorConfig_JT);
            
        } catch (IOException e) {
        	LOG.error("Error during file writing: ", e);
        } catch (TemplateException e) {
        	LOG.error("Error generation Template:", e);
        }

	}

}
