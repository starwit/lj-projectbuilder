package de.${package}.${appName?lower_case}.ejb;

import java.io.Serializable;
import javax.ejb.Local;
import de.${package}.${appName?lower_case}.entity.${domain.name}Entity;

@Local
public interface ${domain.name}Service extends Serializable, AbstractService<${domain.name}Entity> {

}


    
