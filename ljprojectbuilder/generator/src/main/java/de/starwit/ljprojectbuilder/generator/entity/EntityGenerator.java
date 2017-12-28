package de.starwit.ljprojectbuilder.generator.entity;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.generator.AbstractGenerator;

public class EntityGenerator extends AbstractGenerator<EntityModule> {

	public final static Logger LOG = Logger.getLogger(EntityGenerator.class);
	
	@Override
	public Map<String, Object> fillTemplateGlobalParameter(GeneratorDto setupBean) {
		if (setupBean.getProject() == null) {
			return null;
		}
		// Build the data-model
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("appName", setupBean.getProject().getTitle().toLowerCase());
		data.put("package", setupBean.getProject().getPackagePrefix().toLowerCase());
		data.put("classes", getModule().getEntityClasses());
		return data;
	}

	@Override
	public Map<String, Object> fillTemplateDomainParameter(DomainEntity domain) {
		// Build the data-model
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("domain", domain);
		data.put("imports", EntityImports.gatherEntityImports(domain));
		return data;
	}
}
