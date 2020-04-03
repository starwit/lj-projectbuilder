package de.spring.persistence.response;

import javax.xml.bind.annotation.XmlRootElement;

import de.spring.persistence.entity.AbstractEntity;

@XmlRootElement
public class EntityResponse<E extends AbstractEntity<Long>> extends Response<E> {

	public EntityResponse() {
		// default constructor
	}

	public EntityResponse(E result) {
		super(result);
	}

}
