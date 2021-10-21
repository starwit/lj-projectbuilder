package de.starwit.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.TemplateFile;
import de.starwit.persistence.repository.TemplateFileRepository;

@Service
public class TemplateFileService implements ServiceInterface<TemplateFile, TemplateFileRepository> {

	final static Logger LOG = LoggerFactory.getLogger(TemplateFileService.class);

	@Autowired
	private TemplateFileRepository templateFileRepository;

	public List<TemplateFile> findAllTemplateFilesByApp(Long appId) {
		return this.templateFileRepository.findAllTemplateFilesByApp(appId);
	}

	public List<TemplateFile> findAllTemplateFilesByAppTemplate(Long appTemplateId) {
		return this.templateFileRepository.findAllTemplateFilesByAppTemplate(appTemplateId);
	}

	public List<Long> findAllTemplateFileIdsByAppTemplate(Long appTemplateId) {
		return this.templateFileRepository.findAllTemplateFileIdsByAppTemplate(appTemplateId);
	}

	@Override
	public TemplateFileRepository getRepository() {
		return templateFileRepository;
	}
}

