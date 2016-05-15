package de.starwit.ljprojectbuilderp.ejb;

import java.io.InputStream;

import javax.ejb.Local;

import de.starwit.ljprojectbuilderp.entity.AbstractEntity;
import de.starwit.ljprojectbuilderp.exception.ImportException;

@Local
public interface DataImportExportService {
	
	void importAll();

	void importEntityData(Class<? extends AbstractEntity> entityClass,
			InputStream in) throws ImportException;
}
