package de.starwit.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractEntity as basis for all entities in Lirejarp-Spring projects
 *
 * @author Dennis Hooth (Volkswagen AG)
 * @since 12.03.2019 10:30
 */
@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected PK id;

    /**
     *
     */
    public AbstractEntity() {
        super();
    }

    /**
     * @return
     */
    public PK getId() {
        return this.id;
    }

    public void setId(PK id) {
        this.id = id;
    }

}
