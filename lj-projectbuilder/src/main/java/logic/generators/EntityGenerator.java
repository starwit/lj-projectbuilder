package logic.generators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import frontend.beans.DataType;
import frontend.beans.DomainAttributeBean;
import frontend.beans.GeneratorSetupBean;
import logic.GeneratorConfig;

public class EntityGenerator extends Generator {

	public final static Logger LOG = Logger.getLogger(EntityGenerator.class);

	@Override
	public void generate(GeneratorSetupBean setupBean) {
		String packagePath = setupBean.getProjectPath() + "/" + setupBean.getProjectName() + "/persistence/" + Generator.SRC_JAVA_PATH
				+ setupBean.getProjectName();
		GeneratorConfig generatorConfig = GeneratorConfig.ENTITY;
		
		String domain = setupBean.getDomainName();
		
		// Build the data-model
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("domain", domain);
		data.put("domainLower", domain.toLowerCase());
		data.put("domainUpper", domain.toUpperCase());
		data.put("attributes", (List<DomainAttributeBean>)setupBean.getDomainAttributes());
		data.put("appName", setupBean.getProjectName());
		
		Set<String> imports = new HashSet<String>();
		if (setupBean.getDomainAttributes() != null) {
			for (DomainAttributeBean attr : setupBean.getDomainAttributes()) {
				if(DataType.String.equals(attr.getDataType())) {
					if(attr.getMax() != null || attr.getMin() != null) {
						imports.add("import javax.validation.constraints.Size;");
					}
					if (!attr.isNullable()) {
						imports.add("import org.hibernate.validator.constraints.NotBlank;");
					}
				} else if(DataType.Date.equals(attr.getDataType()) || DataType.Time.equals(attr.getDataType()) || DataType.Timestamp.equals(attr.getDataType())) {
					imports.add("import java.util.Date;");
					imports.add("import javax.persistence.Temporal;");
					imports.add("import javax.persistence.TemporalType;");
				} else if(DataType.Enum.equals(attr.getDataType())) {
					imports.add("import javax.persistence.EnumType;");
					imports.add("import javax.persistence.Enumerated;");
				} else if (DataType.BigDecimal.equals(attr.getDataType())) {
					imports.add("import java.math.BigDecimal;");
				}
				if (attr.getPattern() != null) {
					imports.add("import javax.validation.constraints.Pattern;");
				}

				if (!DataType.String.equals(attr.getDataType())) {
					if(!attr.isNullable()) {
						imports.add("import javax.validation.constraints.NotNull;");
					}
					if(attr.getMax() != null) {
						imports.add("import javax.validation.constraints.Max;");
					}
					if( attr.getMin() != null) {
						imports.add("import javax.validation.constraints.Min;");
					}
				}
			}
		}
		data.put("imports", imports);
		generate(setupBean, packagePath, data, generatorConfig);
	}

}
