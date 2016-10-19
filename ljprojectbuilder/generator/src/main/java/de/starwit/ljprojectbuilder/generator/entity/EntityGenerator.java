package de.starwit.ljprojectbuilder.generator.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.AttributeEntity;
import de.starwit.ljprojectbuilder.entity.DataType;
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
		data.put("appName", setupBean.getProject().getTitle());
		data.put("package", setupBean.getProject().getPackagePrefix());
		data.put("classes", getModule().getEntityClasses());
		return data;
	}

	@Override
	public Map<String, Object> fillTemplateDomainParameter(DomainEntity domain) {
		// Build the data-model
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("domain", domain.getName());
		data.put("domainLower", domain.getName().toLowerCase());
		data.put("domainUpper", domain.getName().toUpperCase());
		data.put("attributes", domain.getAttributes());


		Set<String> imports = new HashSet<String>();
		if (domain.getAttributes() != null) {
			for (AttributeEntity attr : domain.getAttributes()) {
				if (DataType.String.equals(attr.getDataType())) {
					if (attr.getMax() != null || attr.getMin() != null) {
						imports.add("import javax.validation.constraints.Size;");
					}
					if (!attr.isNullable()) {
						imports.add("import org.hibernate.validator.constraints.NotBlank;");
					}
				} else if (DataType.Date.equals(attr.getDataType()) || DataType.Time.equals(attr.getDataType())
						|| DataType.Timestamp.equals(attr.getDataType())) {
					imports.add("import java.util.Date;");
					imports.add("import javax.persistence.Temporal;");
					imports.add("import javax.persistence.TemporalType;");
				} else if (DataType.Enum.equals(attr.getDataType())) {
					imports.add("import javax.persistence.EnumType;");
					imports.add("import javax.persistence.Enumerated;");
				} else if (DataType.BigDecimal.equals(attr.getDataType())) {
					imports.add("import java.math.BigDecimal;");
				}
				if (attr.getPattern() != null) {
					imports.add("import javax.validation.constraints.Pattern;");
				}

				if (!DataType.String.equals(attr.getDataType())) {
					if (!attr.isNullable()) {
						imports.add("import javax.validation.constraints.NotNull;");
					}
					if (attr.getMax() != null) {
						imports.add("import javax.validation.constraints.Max;");
					}
					if (attr.getMin() != null) {
						imports.add("import javax.validation.constraints.Min;");
					}
				}
			}
		}
		data.put("imports", imports);
		return data;
	}
}
