package de.starwit.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.TemplateFile;
import de.starwit.persistence.repository.CodeTemplateRepository;

@Service
public class CodeTemplateService implements ServiceInterface<TemplateFile> {

	final static Logger LOG = LoggerFactory.getLogger(CodeTemplateService.class);

	@Autowired
	private CodeTemplateRepository codeTemplateRepository;

	public List<TemplateFile> findAllCodeTemplatesByApp(Long appId) {
		return this.codeTemplateRepository.findAllCodeTemplatesByApp(appId);
	}

	public List<TemplateFile> findAllCodeTemplatesByAppTemplate(Long appTemplateId) {
		return this.codeTemplateRepository.findAllCodeTemplatesByAppTemplate(appTemplateId);
	}

	public List<Long> findAllCodeTemplateIdsByAppTemplate(Long appTemplateId) {
		return this.codeTemplateRepository.findAllCodeTemplateIdsByAppTemplate(appTemplateId);
	}

	@Override
	public TemplateFile findById(Long id) {
		return this.codeTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
	}

	@Override
	public List<TemplateFile> findAll() {
		return this.codeTemplateRepository.findAll();
	}

	@Override
	public TemplateFile saveOrUpdate(TemplateFile entity) throws ValidationException {
		return this.codeTemplateRepository.save(entity);
	}

	@Override
	public void delete(Long id) throws de.starwit.persistence.exception.EntityNotFoundException {
		this.codeTemplateRepository.deleteById(id);
		
	}
}

