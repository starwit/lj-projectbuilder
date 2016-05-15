package frontend.beans;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GeneratorSetupBean {
	
	private String projectName;
	private String projectPath;
	private String templatePath;
	private String domainName;
	private boolean generateEntity;
	private boolean generateService;
	private boolean generateRest;
	private boolean generateFrontend;
	private boolean generateTests;
	
	private List<DomainAttributeBean> domainAttributes = new ArrayList<DomainAttributeBean>();
	
	public GeneratorSetupBean() {
		this.projectName = "lirejarp";
		this.projectPath = Paths.get("").toAbsolutePath().getParent().toString();
		this.templatePath = Paths.get("").toAbsolutePath().getParent().toString() + "/lj-projectbuilder/src/main/resources";
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectPath() {
		return projectPath;
	}
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public List<DomainAttributeBean> getDomainAttributes() {
		return domainAttributes;
	}
	public void setDomainAttributes(List<DomainAttributeBean> domainAttributes) {
		this.domainAttributes = domainAttributes;
	}
	
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public boolean getGenerateEntity() {
		return generateEntity;
	}

	public void setGenerateEntity(boolean generateEntity) {
		this.generateEntity = generateEntity;
	}

	public boolean getGenerateService() {
		return generateService;
	}

	public void setGenerateService(boolean generateService) {
		this.generateService = generateService;
	}

	public boolean getGenerateRest() {
		return generateRest;
	}

	public void setGenerateRest(boolean generateRest) {
		this.generateRest = generateRest;
	}

	public boolean getGenerateFrontend() {
		return generateFrontend;
	}

	public void setGenerateFrontend(boolean generateFrontend) {
		this.generateFrontend = generateFrontend;
	}

	public boolean getGenerateTests() {
		return generateTests;
	}

	public void setGenerateTests(boolean generateTests) {
		this.generateTests = generateTests;
	}

}
