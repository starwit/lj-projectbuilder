package de.starwit.service.impl;

import java.util.List;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.mapper.Mapper;
import de.starwit.persistence.entity.Domain;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.AppRepository;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;

@Service
public class AppService implements ServiceInterface<App> {

	public final static String[] EXT = new String[] { "java", "js", "html", "sql", "xml" };
	final static Logger LOG = LoggerFactory.getLogger(AppService.class);

	@Autowired
	private AppRepository AppRepository;
	
	@Autowired
	private AppTemplateService appTemplateService;
	
	@Autowired
	private DomainService domainService;


	public App findAppByIdOrThrowExeption(final Long appid) throws NotificationException {
		final App entity = this.AppRepository.findById(appid).orElse(null);
		if (entity == null) {
			LOG.error("Error setup app for generation. App with id " + appid + " could not be found.");
			final ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR,
					"error.appservice.findappbyid.appnotfound");
			throw new NotificationException(data);
		}
		App dto = Mapper.convert(entity, App.class, "domains", "selectedDomains", "template");
		dto.setTemplate(appTemplateService.map(entity.getTemplate()));
		return dto;
	}

	@Override
	public App saveOrUpdate(App dto) throws ValidationException {
		if (dto != null && dto.getId() != null) {
			final List<Domain> domains = this.AppRepository.findById(dto.getId()).orElse(null)
					.getDomains();
			if (domains != null) {
				dto.setDomains(domains);
			}
		}
		
		dto = this.AppRepository.saveAndFlush(dto);
		dto = Mapper.convert(dto, App.class, "domains", "selectedDomains");
		dto.setTemplate(appTemplateService.map(dto.getTemplate()));
		return dto;
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		App entity = AppRepository.findById(id).get();
		if (entity == null || entity.getDomains() == null) {
			throw new EntityNotFoundException(id, "App");
		}
		
		List<Domain> domains = entity.getDomains();
		for (Domain domain : domains) {
			domainService.delete(domain.getId());
		}
		AppRepository.flush();
		this.AppRepository.deleteById(id);
	}

	@Override
	public App findById(Long id) {
		App entity = this.AppRepository.findById(id).orElse(null);
		App dto = Mapper.convert(entity, App.class, "domains", "selectedDomains", "template");
	   	dto.setTemplate(appTemplateService.map(entity.getTemplate()));
		return dto;
	}

	@Override
	public List<App> findAll() {
		List<App> dtos = Mapper.convertList(this.AppRepository.findAll(), App.class, "domains", "selectedDomains");
		for (App dto : dtos) {
			dto.setTemplate(appTemplateService.map(dto.getTemplate()));
		}
		return dtos;
	}
}