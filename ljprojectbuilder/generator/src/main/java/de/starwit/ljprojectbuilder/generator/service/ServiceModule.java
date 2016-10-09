package de.starwit.ljprojectbuilder.generator.service;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.generator.AbstractModule;
import de.starwit.ljprojectbuilder.generator.TemplateDef;

public class ServiceModule extends AbstractModule {
	private final static String FS = Constants.FILE_SEP;

	public ServiceModule(GeneratorDto setupBean) {
		super(setupBean);
		this.setModuleName("persistence");
		
		TemplateDef serviceInterfaceT = new TemplateDef(getSrcDir() + "ejb" + FS, "Service.java", "service" + FS + "service.ftl");
		serviceInterfaceT.setUpperCaseFirst(true);
		TemplateDef serviceImplT = new TemplateDef(getSrcDir() + "ejb" + FS + "impl" + FS, "ServiceImpl.java", "service" + FS + "serviceImpl.ftl");
		serviceImplT.setUpperCaseFirst(true);
		TemplateDef serviceTestT = new TemplateDef(getTestDir() + "ejb" + FS + "impl" + FS, "ServiceTest.java", "service" + FS + "serviceTest.ftl");
		serviceTestT.setUpperCaseFirst(true);

		getDomainTemplates().add(serviceInterfaceT);
		getDomainTemplates().add(serviceImplT);
		getDomainTemplates().add(serviceTestT);
	}
	
	@Override
	public String getSrcDir() {
		return this.getModuleDir()
				+ FS + getPaths().getSource()
				+ getSetupBean().getProject().getPackagePrefix()
				+ FS + getSetupBean().getProject().getTitle() + FS;
	}
	
	@Override
	public String getTestDir() {
		return this.getModuleDir()
				+ FS + getPaths().getTest()
				+ getSetupBean().getProject().getPackagePrefix()
				+ FS + getSetupBean().getProject().getTitle() + FS;
	}

}
