package de.starwit.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.TemplateFile;
import de.starwit.persistence.repository.TemplateFileRepository;

@Service
public class TemplateFileService implements ServiceInterface<TemplateFile> {

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
	public TemplateFile findById(Long id) {
		return this.templateFileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
	}

	@Override
	public List<TemplateFile> findAll() {
		return this.templateFileRepository.findAll();
	}

	@Override
	public TemplateFile saveOrUpdate(TemplateFile entity) throws ValidationException {
		return this.templateFileRepository.save(entity);
	}

	@Override
	public void delete(Long id) throws de.starwit.persistence.exception.EntityNotFoundException {
		this.templateFileRepository.deleteById(id);
		
	}
}

