package de.spring.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.spring.persistence.entity.CodeTemplateEntity;
import de.spring.persistence.repository.CodeTemplateRepository;

@Service
public class CodeTemplateService {

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

	public CodeTemplateEntity findById(Long id) {
		return this.codeTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
	}

	public List<CodeTemplateEntity> findAll() {
		return this.codeTemplateRepository.findAll();
	}

	public CodeTemplateEntity saveOrUpdate(CodeTemplateEntity entity) {
		return this.codeTemplateRepository.save(entity);
	}

	public void delete(CodeTemplateEntity entity) {
		this.codeTemplateRepository.delete(entity);
	}
}

