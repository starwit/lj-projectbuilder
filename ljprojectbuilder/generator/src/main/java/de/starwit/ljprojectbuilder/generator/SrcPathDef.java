package de.starwit.ljprojectbuilder.generator;

import de.starwit.ljprojectbuilder.config.Constants;

public class SrcPathDef {
	
	private String source = Constants.SOURCE_PATH;
	private String resources = Constants.RESOURCES_PATH;
	private String test = Constants.TEST_PATH;
	private String testResources = Constants.TEST_RESOURCES_PATH;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getResources() {
		return resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getTestResources() {
		return testResources;
	}
	public void setTestResources(String testResources) {
		this.testResources = testResources;
	}

}
