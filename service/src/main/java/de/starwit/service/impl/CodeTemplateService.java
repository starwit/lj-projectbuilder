package de.starwit.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.CodeTemplateEntity;
import de.starwit.persistence.repository.CodeTemplateRepository;

@Service
public class CodeTemplateService implements ServiceInterface<CodeTemplateEntity> {

	final static Logger LOG = LoggerFactory.getLogger(CodeTemplateService.class);

	@Autowired
	private CodeTemplateRepository codeTemplateRepository;

	public List<CodeTemplateEntity> findAllCodeTemplatesByProject(Long projectId) {
		return this.codeTemplateRepository.findAllCodeTemplatesByProject(projectId);
	}

	public List<CodeTemplateEntity> findAllCodeTemplatesByProjectTemplate(Long projectTemplateId) {
		return this.codeTemplateRepository.findAllCodeTemplatesByProjectTemplate(projectTemplateId);
	}

	public List<Long> findAllCodeTemplateIdsByProjectTemplate(Long projectTemplateId) {
		return this.codeTemplateRepository.findAllCodeTemplateIdsByProjectTemplate(projectTemplateId);
	}

	@Override
	public CodeTemplateEntity findById(Long id) {
		return this.codeTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
	}

	@Override
	public List<CodeTemplateEntity> findAll() {
		return this.codeTemplateRepository.findAll();
	}

	@Override
	public CodeTemplateEntity saveOrUpdate(CodeTemplateEntity entity) throws ValidationException {
		return this.codeTemplateRepository.save(entity);
	}

	@Override
	public void delete(Long id) throws de.starwit.persistence.exception.EntityNotFoundException {
		this.codeTemplateRepository.deleteById(id);
		
	}
}

