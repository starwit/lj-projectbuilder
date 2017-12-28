package de.starwit.ljprojectbuilder.generator;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;

/**
 * The generator starts generation and hides the details of generation.
 * @author anett
 */
public interface Generator {

	/**
	 * Starts genereation.
	 */
	public void generate(GeneratorDto setupBean);
}
