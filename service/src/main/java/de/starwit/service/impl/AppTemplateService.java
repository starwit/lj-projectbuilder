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

	static final Logger LOG = LoggerFactory.getLogger(AppTemplateService.class);

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
		if (templateFiles != null && templateFiles.isEmpty()) {
			for (TemplateFile templateFile : templateFiles) {
				templateFile.setAppTemplate(entity);
			}
		}
		return this.getRepository().save(entity);
	}

	public AppTemplate updateFromRepo(AppTemplate entity) {
		Set<TemplateFile> newTemplateFiles = entity.getTemplateFiles();
		
		if (newTemplateFiles != null && newTemplateFiles.isEmpty()) {
			for (TemplateFile templateFile : newTemplateFiles) {
				templateFile.setAppTemplate(entity);
			}
		}
		return this.getRepository().save(entity);
	}
}
