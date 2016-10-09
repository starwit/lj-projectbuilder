package de.starwit.ljprojectbuilder.generator;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;

public interface Generator {
	public void generate(GeneratorDto setupBean);
}
