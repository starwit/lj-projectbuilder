package de.starwit.ljprojectbuilder.ejb.impl;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationVersion;

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class DataStartupService {
	
	private final static Logger LOG =  Logger.getLogger("DataStartupService");

	@Resource(name = "jdbc/ljprojectbuilder")
	private DataSource dataSource;
 
	@PostConstruct
	private void onStartup() throws SQLException {
		if (dataSource == null) {
			LOG.error("no datasource found to execute the db migrations!");
			throw new EJBException(
					"no datasource found to execute the db migrations!");
		}
 
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.setBaselineOnMigrate(true);
		flyway.setBaselineVersion(MigrationVersion.fromVersion("201609221422"));
		for (MigrationInfo i : flyway.info().all()) {
			LOG.info("migrate task: " + i.getVersion() + " : "
					+ i.getDescription() + " from file: " + i.getScript());
		}
		flyway.migrate();
	}
}
