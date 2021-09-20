package de.starwit.persistence.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * AbstractEntity as basis for all entities in Lirejarp-Spring projects
 * @author denjoo@oss.volkswagen.com
 * @since 12.03.2019 10:30
 */
@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private PK id;

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

}
