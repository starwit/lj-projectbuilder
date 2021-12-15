package de.starwit.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.TemplateFile;

@Service
public class TemplateFileService {

	final static Logger LOG = LoggerFactory.getLogger(TemplateFileService.class);

	public List<TemplateFile> findAllTemplateFilesByAppTemplate(Long appTemplateId) {
		//TODO: get templateFiles from git
		return null;
	}
}

