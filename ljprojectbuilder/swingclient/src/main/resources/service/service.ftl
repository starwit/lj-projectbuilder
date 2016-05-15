package de.starwit.${appName?lower_case}.ejb;

import java.io.Serializable;
import javax.ejb.Local;
import de.starwit.${appName?lower_case}.entity.${domain}Entity;

@Local
public interface ${domain}Service extends Serializable, AbstractService<${domain}Entity> {

}


    
