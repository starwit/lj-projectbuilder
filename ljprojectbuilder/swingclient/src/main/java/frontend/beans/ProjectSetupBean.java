package frontend.beans;

import java.nio.file.Paths;

public class ProjectSetupBean {
	
	private String newProjectName;
	private String currentProjectName;
	private String projectPath;
	private String targetPath;
	
	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public ProjectSetupBean() {
		this.currentProjectName = "ljprojectbuilder";
		this.projectPath = Paths.get("").toAbsolutePath().getParent().toString();
		this.targetPath = Paths.get("").toAbsolutePath().getParent().toString();
		this.newProjectName = "demoapp";
	}
	
	public String getNewProjectName() {
		return newProjectName;
	}

	public void setNewProjectName(String projectName) {
		this.newProjectName = projectName;
	}

	public String getCurrentProjectName() {
		return currentProjectName;
	}

	public void setCurrentProjectName(String packageName) {
		this.currentProjectName = packageName;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

}
