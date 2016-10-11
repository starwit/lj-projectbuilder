package de.starwit.ljprojectbuilder.response;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListResponse<E> extends Response<Collection<E>>{

	private int amount = 0;
	
	public ListResponse() {
		//default constructor
	}
	
	public ListResponse(Collection<E> result) {
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
