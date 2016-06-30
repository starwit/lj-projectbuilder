package de.starwit.ljprojectbuilder.response;

import javax.xml.bind.annotation.XmlRootElement;
import de.starwit.ljprojectbuilder.entity.AbstractEntity;

@XmlRootElement
public class EntityResponse<E extends AbstractEntity> extends Response<E>{
	
	public EntityResponse() {
		//default constructor
	}
	
	public EntityResponse(E result) {
		super(result);
	}
		
}
