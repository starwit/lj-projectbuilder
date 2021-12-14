package de.starwit.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@XmlRootElement
@Entity
@Table(name = "DIAGRAM_POSITION")
public class Position extends AbstractEntity<Long> {

    @NotNull
    @Column(name = "POSITION_X", nullable = false)
    private int positionX = 0;

    @NotNull
    @Column(name = "POSITION_Y", nullable = false)
    private int positionY = 0;

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

 
}
