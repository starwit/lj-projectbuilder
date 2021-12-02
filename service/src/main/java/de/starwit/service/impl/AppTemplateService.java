package de.starwit.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.repository.AppTemplateRepository;

@Service
public class AppTemplateService implements ServiceInterface<AppTemplate, AppTemplateRepository> {

	final static Logger LOG = LoggerFactory.getLogger(AppTemplateService.class);

	@Autowired
	private AppTemplateRepository appTemplateRepository;


	@Override
	public AppTemplateRepository getRepository() {
		return appTemplateRepository;
	}

}
