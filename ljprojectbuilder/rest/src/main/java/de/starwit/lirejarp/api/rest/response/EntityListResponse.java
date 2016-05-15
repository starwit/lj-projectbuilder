package de.starwit.ljprojectbuilderp.api.rest.response;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.ljprojectbuilderp.entity.AbstractEntity;

@XmlRootElement
public class EntityListResponse<E extends AbstractEntity> extends Response<Collection<E>>{

	private int amount = 0;
	
	public EntityListResponse() {
		//default constructor
	}
	
	public EntityListResponse(Collection<E> result) {
		super(result);
		if (result != null) {
			this.amount = result.size();
		}
	}

//	@JsonIgnoreProperties({ "shipment","container"})

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
