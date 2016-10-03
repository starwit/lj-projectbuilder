package de.starwit.ljprojectbuilder.ejb;

import java.io.Serializable;

import javax.ejb.Local;

import de.starwit.ljprojectbuilder.entity.AttributeEntity;

@Local
public interface AttributeService extends Serializable, AbstractService<AttributeEntity> {
}


    
