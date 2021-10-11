package de.starwit.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.App;
import de.starwit.persistence.repository.AppRepository;

@Service
public class AppService implements ServiceInterface<App, AppRepository> {

	public final static String[] EXT = new String[] { "java", "js", "html", "sql", "xml" };
	final static Logger LOG = LoggerFactory.getLogger(AppService.class);

	@Autowired
	private AppRepository AppRepository;
	
	@Override
	public AppRepository getRepository() {
		return AppRepository;
	}

}