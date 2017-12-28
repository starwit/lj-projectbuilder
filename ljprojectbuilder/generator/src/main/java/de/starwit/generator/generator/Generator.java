package de.starwit.generator.generator;

import de.starwit.generator.dto.GeneratorDto;

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
