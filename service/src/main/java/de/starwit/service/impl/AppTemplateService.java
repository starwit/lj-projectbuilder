package de.starwit.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.entity.TemplateFile;
import de.starwit.persistence.repository.AppTemplateRepository;
import de.starwit.persistence.repository.TemplateFileRepository;

@Service
public class AppTemplateService implements ServiceInterface<AppTemplate, AppTemplateRepository> {

	final static Logger LOG = LoggerFactory.getLogger(AppTemplateService.class);

	@Autowired
	private AppTemplateRepository appTemplateRepository;

	@Autowired
	private TemplateFileRepository templateFileRepository;


	@Override
	public AppTemplateRepository getRepository() {
		return appTemplateRepository;
	}


	@Override
	public AppTemplate saveOrUpdate(AppTemplate entity) {
		Set<TemplateFile> templateFiles = templateFileRepository.findAllByAppTemplate(entity.getId());
		
		if (templateFiles != null && templateFiles.size() > 0) {
			for (TemplateFile templateFile : templateFiles) {
				templateFileRepository.delete(templateFile);
			}
		}

		templateFiles = entity.getTemplateFiles();
		if (templateFiles != null && templateFiles.size() > 0) {
			for (TemplateFile templateFile : templateFiles) {
				templateFile.setAppTemplate(entity);
				templateFileRepository.save(templateFile);
			}
		}
		return ServiceInterface.super.saveOrUpdate(entity);
	}

}
