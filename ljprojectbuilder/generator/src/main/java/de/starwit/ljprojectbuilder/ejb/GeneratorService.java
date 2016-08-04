package de.starwit.ljprojectbuilder.ejb;

import javax.ejb.Local;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;

/**
 * General class for starting the code generation. The different parts of the application have their own special implementation of the code generator which are base on Generator-class.
 * @author anett
 *
 */
@Local
public interface GeneratorService {
	
	/**
	 * Generates the target files for the application.
	 * @param setupBean - generator configuration
	 */
	public void generate(GeneratorDto dto);
}
