package de.starwit.ljprojectbuilder.generator.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.starwit.ljprojectbuilder.config.GeneratorConfig;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.generator.AbstractGenerator;

public class FrontendGenerator extends AbstractGenerator<FrontendModule> {
	
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
		List<DomainEntity> domains = setupBean.getDomains();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("appName", setupBean.getProject().getTitle());
		data.put("templateSingle", GeneratorConfig.MAINTAIN_UI.suffix);
		data.put("templateAll", GeneratorConfig.ALL_UI.suffix);
		data.put("package", setupBean.getProject().getPackagePrefix());
		data.put("domainnames", getModule().getDomainnames() );
		data.put("domains", domains);
		return data;
	}
}
