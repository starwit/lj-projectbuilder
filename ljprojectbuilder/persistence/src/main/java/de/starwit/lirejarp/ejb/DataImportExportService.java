package de.starwit.lirejarp.ejb;

import java.io.InputStream;

import javax.ejb.Local;

import de.starwit.lirejarp.entity.AbstractEntity;
import de.starwit.lirejarp.exception.ImportException;

@Local
public interface DataImportExportService {
	
	void importAll();

	void importEntityData(Class<? extends AbstractEntity> entityClass,
			InputStream in) throws ImportException;
}
