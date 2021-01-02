package de.starwit.persistence.response;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.persistence.entity.AbstractEntity;

@XmlRootElement
public class EntityListResponse<E extends AbstractEntity<Long>> extends Response<Collection<E>>{

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
