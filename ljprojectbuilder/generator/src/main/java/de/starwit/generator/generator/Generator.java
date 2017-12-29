package de.starwit.generator.generator;

import de.starwit.generator.modules.AbstractModule;

/**
 * The generator starts generation and hides the details of generation.
 * @author anett
 */
public interface Generator<E extends AbstractModule> {

	/**
	 * Starts genereation.
	 */
	public void generate(E module);
}
