package de.starwit.ljprojectbuilder.ejb;

import java.io.Serializable;
import javax.ejb.Local;
import de.starwit.ljprojectbuilder.entity.DomainEntity;

@Local
public interface DomainService extends Serializable, AbstractService<DomainEntity> {

}


    
