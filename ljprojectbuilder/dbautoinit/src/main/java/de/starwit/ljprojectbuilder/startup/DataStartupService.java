package de.starwit.ljprojectbuilder.startup;

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
	private static final String DB = System.getProperty("flyway.database");
	private static final String FS = System.getProperty("file.separator");

	@Resource(name = "jdbc/ljprojectbuilder")
	private DataSource dataSource;
 
	@PostConstruct
	private void onStartup() throws SQLException {
		if (dataSource == null) {
			LOG.error("no datasource found to execute the db migrations!");
			throw new EJBException(
					"no datasource found to execute the db migrations!");
		}
 
		String schema = "sql" + FS + "mysql";
		if (DB != null) {
			 schema = "sql" + FS + DB;
		}
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.setBaselineOnMigrate(true);
		flyway.setLocations("sql" + FS + "configdata", schema);
		flyway.setBaselineVersion(MigrationVersion.fromVersion("201609221422"));
		for (MigrationInfo i : flyway.info().all()) {
			LOG.info("migrate task: " + i.getVersion() + " : "
					+ i.getDescription() + " from file: " + i.getScript());
		}
		flyway.repair();
		flyway.migrate();
	}
}
