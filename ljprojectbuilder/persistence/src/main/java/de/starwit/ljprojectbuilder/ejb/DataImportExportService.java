package de.starwit.ljprojectbuilder.ejb;

import java.io.InputStream;

import javax.ejb.Local;

import de.starwit.ljprojectbuilder.entity.AbstractEntity;
import de.starwit.ljprojectbuilder.exception.ImportException;

@Local
public interface DataImportExportService {
	
	void importAll();

	void importEntityData(Class<? extends AbstractEntity> entityClass,
			InputStream in) throws ImportException;
}
