package de.starwit.persistence.response;

import java.util.Collection;

import de.starwit.persistence.entity.AbstractEntity;


/**
 * This wrapper is used to get an entity which has a oneToMany relation. 
 * The resultList of that collection is saved as children.
 * 
 * @author Anett
 *
 * @param <E> parent entity
 * @param <T> children
 */
public class EntityWithChildrenResponse<E extends AbstractEntity<Long>, T extends AbstractEntity<Long>> extends Response<E>{

	private Collection<T> children;
	
	private ResponseMetadata resultState;
	
	private int amountOfChildren = 0;
	
	public EntityWithChildrenResponse() {
		//default constructor
	}
	
	public EntityWithChildrenResponse(E result, Collection<T> children) {
		super(result);
		this.children = children;
	}

	public Collection<T> getChildren() {
		return children;
	}

	public void setChildren(Collection<T> children) {
		this.children = children;
	}

	public ResponseMetadata getResultState() {
		return resultState;
	}

	public void setResultState(ResponseMetadata resultState) {
		this.resultState = resultState;
	}

	public int getAmountOfChildren() {
		return amountOfChildren;
	}

	public void setAmountOfChildren(int amountOfChildren) {
		this.amountOfChildren = amountOfChildren;
	}

}
